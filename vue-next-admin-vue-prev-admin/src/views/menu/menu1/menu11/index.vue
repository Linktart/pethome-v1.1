<template>
  <div>
    <el-row>
      <el-col :span="16">
        <el-form :inline="true" :model="userSearchForm" class="demo-form-inline">
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-select clearable
                       v-model="userSearchForm.username"
                       filterable
                       remote
                       reserve-keyword
                       placeholder="请输入关键词搜索"
                       :remote-method="remoteMethod"
                       :loading="loading">
              <el-option
                  v-for="item in usernameOptions"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select clearable v-model="userSearchForm.state" placeholder="请选择">
              <el-option
                  v-for="item in userOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话" :label-width="formLabelWidth">
            <el-select clearable
                       v-model="userSearchForm.phone"
                       filterable
                       remote
                       reserve-keyword
                       placeholder="请输入关键词搜索"
                       :remote-method="remoteMethod"
                       :loading="loading">
              <el-option
                  v-for="item in phoneOptions"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="toQuery1">查询</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="1">
        <el-button type="success" @click="dialogFormVisible = true">新增</el-button>
      </el-col>
      <el-col :span="6">

        <!--  批量删除  -->
        <el-popover
            placement="top"
            width="160"
            v-model="visible">
          <p>你确定要批量删除吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false">取消</el-button>
            <el-button type="primary" size="mini" @click="batchDelete">确定</el-button>
          </div>
          <el-button slot="reference" type="danger">批量删除</el-button>
        </el-popover>
      </el-col>
    </el-row>


    <!-- Form -->

    <el-dialog title="操作" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item>
          <el-input v-model="form.id" autocomplete="off" type="hidden"></el-input>
        </el-form-item>
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="email" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.email" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.password" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.age" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-radio v-model="form.state" label="1">正常</el-radio>
          <el-radio v-model="form.state" label="0">禁用</el-radio>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>


    <el-row>
      <el-col :span="24">
        <div class="grid-content bg-purple-light">
          <el-table
              :row-key="getRowKey"
              :data="tableData"
              style="width: 100%"
              @selection-change="handleSelectionChange">

            <el-table-column
                :reserve-selection="true"
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column
                prop="id"
                label="编号"
                width="180">
            </el-table-column>
            <el-table-column
                prop="username"
                label="用户名"
                width="180">
            </el-table-column>
            <el-table-column
                prop="email"
                label="电子邮箱"
                width="180">
            </el-table-column>
            <el-table-column
                prop="phone"
                label="电话"
                width="180">
            </el-table-column>
            <el-table-column
                prop="password"
                label="密码">
            </el-table-column>
            <el-table-column
                prop="age"
                label="年龄">
            </el-table-column>
            <el-table-column
                label="状态"
                width="180">
              <template slot-scope="scope">
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.state == 1 ? '正常' : '禁用' }}</el-tag>
                </div>
              </template>
            </el-table-column>

            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">编辑
                </el-button>
                <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除
                </el-button>

              </template>
            </el-table-column>


          </el-table>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="6">
        <el-pagination style="margin-top:auto; margin-left: auto"
                       background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="userSearchForm.currentPage"
                       :page-sizes="[1, 2, 3, 5]"
                       :page-size="userSearchForm.pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
      </el-col>

    </el-row>


  </div>
</template>

<script>
// import mockuser from '../js/user'

export default {

  data() {

    return {
      list: [],
      loading: false,
      usernameOptions: [],
      phoneOptions: [],
      userOptions: [{
        value: 1,
        label: '启用'
      }, {
        value: 0,
        label: '禁用'
      }],
      options: [],
      userIds: [],
      visible: false,
      formLabelWidth: '140px',
      dialogFormVisible: false,
      form: {
        state: "0"
      },
      total: 0,
      value: [],
      userSearchForm: {},
      tableData: [],
      input: '',

      value1: '',
      radio: '1',
      currentPage: 1
    }
  }, methods: {
    toQuery1() {
      this.userSearchForm.currentPage = 1;
      this.toQuery();
    },
    //查询所有employee的name  id
    toAllEmpNameAndId() {
      this.$http.get("/employee").then(res => {
        this.states = res.data;
        this.phoneList = this.states.map(item => {
          return {value: `${item.phone}`, label: `${item.phone}`};
        });
        this.usernameList = this.states.map(item => {
          return {value: `${item.username}`, label: `${item.username}`};
        });
      })
    },
    // 电话搜索栏
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.phoneOptions = this.phoneList.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
          this.usernameOptions = this.usernameList.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.phoneOptions = [];
        this.usernameOptions = [];
      }
    },
    handleChange(value) {
      this.form.parentId = value;
    },
    //批量删除
    batchDelete() {
      this.visible = false;
      console.log(this.userIds);

      this.$http.delete("employee", {data: {userIds: this.userIds}}).then(res => {
        alert(res.data.msg)
        this.toQuery();

      })
    },
    // 保存选中的数据id,row-key就是要指定一个key标识这一行的数据
    getRowKey(row) {
      return row.id
    },
    handleSelectionChange(val) {
      //把对象数组转成 id数组
      this.userIds = val.map(function (obj) {
        return obj.id;
      });
      console.log(this.userIds);
    },
    saveOrUpdate() {
      this.dialogFormVisible = false;
      setTimeout(() => {
        this.$http.put("employee", this.form).then(res => {
          alert(res.data.msg);
          this.toQuery();
          this.form = {};
        })
      }, 200)
    },
    handleEdit(index, row) {
      this.form = row;
      this.form.state = row.state.toString();
      this.dialogFormVisible = true;
    },
    handleDelete(index, row) {

      // let param = {id:row.id}
      this.$http.delete("employee", {data: {id: row.id}}).then(res => {
        alert(res.data.msg)
        this.toQuery();

      })
    },
    toQuery() {

      this.$http.post("employee/list", this.userSearchForm).then(res => {
        this.tableData = res.data.list;
        this.total = res.data.total;

      })
      this.toAllEmpNameAndId();
    },
    // findAll() {
    //   this.$http.get("employee", this.userSearchForm).then(res => {
    //     this.tableData = res.data.list;
    //   })
    // },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.userSearchForm.pageSize = val;
      this.toQuery();
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.userSearchForm.currentPage = val;
      this.toQuery();
    }
  },
  mounted() {
    this.userSearchForm.pageSize = 5;
    this.userSearchForm.currentPage = 1;
    this.toQuery();
  }
}
</script>
