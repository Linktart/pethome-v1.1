<template>
  <div>


    <el-form :inline="true" :model="userSearchForm" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="userSearchForm.name" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="userSearchForm.state" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="toQuery">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- Form -->
    <el-button type="primary" @click="dialogFormVisible = true">新增</el-button>

    <el-dialog title="操作" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item>
          <el-input v-model="form.id" autocomplete="off" type="hidden"></el-input>
        </el-form-item>
        <el-form-item label="sn编号" :label-width="formLabelWidth">
          <el-input v-model="form.sn" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="managerId" :label-width="formLabelWidth">
          <el-input v-model="form.managerId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="parentId" :label-width="formLabelWidth">
          <el-input v-model="form.parentId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="form.state" placeholder="请选择状态">
            <el-option label="1" value="1"></el-option>
            <el-option label="0" value="0"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

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
              prop="sn"
              label="sn编号"
              width="180">
            </el-table-column>
            <el-table-column
              prop="name"
              label="姓名"
              width="180">
            </el-table-column>
            <el-table-column
              prop="state"
              label="状态"
              width="180">
            </el-table-column>
            <el-table-column
              prop="managerId"
              label="managerId">
            </el-table-column>
            <el-table-column
              prop="parentId"
              label="parentId">
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


    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="userSearchForm.currentPage"
      :page-sizes="[1, 2, 3, 5]"
      :page-size="userSearchForm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

  </div>
</template>

<script>
// import mockuser from '../js/user'

export default {

  data() {

    return {
      userIds: [],
      visible: false,
      formLabelWidth: '120px',
      dialogFormVisible: false,
      form: {},
      total: 0,
      options: [{
        value: 1,
        label: '启用'
      }, {
        value: 0,
        label: '禁用'
      }
      ],
      value: '',
      userSearchForm: {},
      tableData: [],
      input: '',

      value1: '',
      radio: '1',
      currentPage: 1
    }
  }, methods: {
    batchDelete(){
      this.visible = false;
      console.log(this.userIds);

      this.$http.delete("dept",{data:{userIds:this.userIds}}).then(res =>{
        alert(res.data.msg)
        this.toQuery();
      })
    },
    // 保存选中的数据id,row-key就是要指定一个key标识这一行的数据
    getRowKey (row) {
      return row.id
    },
    handleSelectionChange(val) {
      //把对象数组转成 id数组
       this.userIds = val.map(function (obj,index,arr) {
        return obj.id;
      });
      console.log(this.userIds);
    },
    saveOrUpdate() {
      this.dialogFormVisible = false;
      this.$http.put("dept", this.form).then(res => {
        alert(res.data.msg);
        this.toQuery();
        this.form = {};
      })
    },
    handleEdit(index, row) {
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(index, row) {

      // let param = {id:row.id}
      this.$http.delete("dept",{data:{id:row.id}}).then(res => {
        alert(res.data.msg)
        this.toQuery();
      })
    },
    toQuery() {

      this.$http.post("dept/list", this.userSearchForm).then(res => {
        this.tableData = res.data.list;
        this.total = res.data.total;
      })
    },
    // findAll() {
    //   this.$http.get("dept", this.userSearchForm).then(res => {
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
    this.userSearchForm.pageSize = 3;
    this.userSearchForm.currentPage = 1;
    this.toQuery();
  }
}
</script>
<style>

.el-row {
  margin-top: 20px;
  margin-left: 100px;
}

</style>
