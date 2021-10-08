<template>
  <div>
    <el-row>
      <el-col :span="16">
        <el-form :inline="true" :model="userSearchForm" class="demo-form-inline">
          <el-form-item label="菜单名称" :label-width="formLabelWidth">
            <el-select clearable
                       v-model="userSearchForm.name"
                       filterable
                       remote
                       reserve-keyword
                       placeholder="请输入关键词搜索"
                       :remote-method="remoteMethod"
                       :loading="loading">
              <el-option
                  v-for="item in nameOptions"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="上级菜单">
            <div class="block">
              <span class="demonstration"></span>
              <el-cascader clearable
                           v-model="userSearchForm.parentId"
                           :options="parentOptions"
                           :props="{expandTrigger:'hover', checkStrictly: true ,emitPath : false,label:'name',value:'id'}"
                           @change="handleChange"></el-cascader>
            </div>
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

        <el-form-item label="菜单名称" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="url" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.url" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="icon" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.icon" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="index" :label-width="formLabelWidth">
          <el-col :span="6">
            <el-input v-model="form.index" autocomplete="off"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="上级菜单" :label-width="formLabelWidth">
          <div class="block">
            <span class="demonstration"></span>
            <el-cascader clearable
                         v-model="form.parentId"
                         :options="parentOptions"
                         :props="{expandTrigger:'hover', checkStrictly: true ,emitPath : false,label:'name',value:'id'}"
                         @change="handleChange"></el-cascader>
          </div>
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
                prop="name"
                label="菜单名称"
                width="180">
            </el-table-column>
            '
            <el-table-column
                prop="url"
                label="路径"
                width="180">
            </el-table-column>
            <el-table-column
                prop="icon"
                label="图标"
                width="180">
            </el-table-column>
            <el-table-column
                prop="index"
                label="索引"
                width="180">
            </el-table-column>
            <el-table-column
                prop="parentName"
                label="上级菜单">
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

      states: [],
      loading: false,
      list: [],
      nameOptions: [],
      parentOptions: [],
      options: [],
      stateOptions: [{
        value: 1,
        label: '启用'
      }, {
        value: 0,
        label: '禁用'
      }],
      userIds: [],
      visible: false,
      formLabelWidth: '140px',
      dialogFormVisible: false,
      form: {
        state: '0'
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
    //查询所有菜单的name
    toAllMenuName() {
      this.$http.get("/menu").then(res => {
        this.states = res.data;
        this.nameList = this.states.map(item => {
          return {value: `${item.name}`, label: `${item.name}`};
        });
      })
    },
    // 菜单搜索栏
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.nameOptions = this.nameList.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.nameOptions = [];
      }
    },
    //上级部门级联选择器
    toTreeMenus() {
      this.$http.get("menu/treemenu").then(res => {

        this.parentOptions = res.data.data;
      })
    },
    handleChange(value) {
      console.log(value);
      this.form.parentId = value;
    },
    //批量删除
    batchDelete() {
      this.visible = false;
      console.log(this.userIds);

      this.$http.delete("menu", {data: {userIds: this.userIds}}).then(res => {
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
        this.$http.put("menu", this.form).then(res => {
          alert(res.data.msg);
          this.toQuery();
          this.form = {};
        })
      }, 200)

    },
    handleEdit(index, row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(index, row) {
      // let param = {id:row.id}
      this.$http.delete("menu", {data: {id: row.id}}).then(res => {
        alert(res.data.msg)
        this.toQuery();
      })
    },
    toQuery() {
      this.$http.post("menu/list", this.userSearchForm).then(res => {
        this.tableData = res.data.list;
        this.total = res.data.total;
        this.toTreeMenus();
      })
      this.toAllMenuName();
    },
    // findAll() {
    //   this.$http.get("menu", this.userSearchForm).then(res => {
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
    // console.log(JSON.stringify(this.list));
    this.userSearchForm.pageSize = 3;
    this.userSearchForm.currentPage = 1;
    this.toQuery();

  }
}
</script>
