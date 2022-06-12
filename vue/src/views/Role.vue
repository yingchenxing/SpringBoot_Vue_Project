<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="Please enter name" suffix-icon="el-icon-search"
                v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">search</el-button>
      <el-button class="ml-5" type="primary" @click="reset">reset</el-button>
    </div>

    <div style="padding: 10px 0">
      <el-button type="primary" @click="handleAdd">Add<i class="el-icon-circle-plus-outline"></i></el-button>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='Yes'
          cancel-button-text='No'
          icon="el-icon-info"
          icon-color="red"
          title="Are you sure to remove the users selected?"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">Delete<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>


      <!--      <el-upload-->
      <!--          action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false"-->
      <!--          accept="xlsx" :on-success="handleExcelImportSuccess"-->
      <!--      >-->
      <!--        <el-button type="primary" class="ml-5">Import<i class="el-icon-bottom"></i></el-button>-->
      <!--      </el-upload>-->
      <!--      <el-button type="primary" @click="exp" class="ml-5">Export<i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table :data="tableData" border stripe header-cell-class-name="'headerBg'"
              @selection-change="handleSelectChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="140">
      </el-table-column>
      <el-table-column prop="name" label="Name" width="120">
      </el-table-column>
      <el-table-column prop="description" label="Description">
      </el-table-column>
      <el-table-column label="Operate" align="center">
        <template slot-scope="scope">

          <el-button type="info" @click="selectMenu(scope.row)">Assign Menu<i
              class="el-icon-menu"></i></el-button>
          <el-button type="success" @click="handleEdit(scope.row)">Edit<i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='Yes'
              cancel-button-text='No'
              icon="el-icon-info"
              icon-color="red"
              title="Are you sure to remove this user?"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">Remove<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2,5,10,15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="User's information" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="90px">
        <el-form-item label="Name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="Description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Save</el-button>
      </div>
    </el-dialog>

    <el-dialog title="Menu Right" :visible.sync="menuDialogVisible" width="30%">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          node-key="id"
          ref="tree"
          :default-expanded-keys="expends"
          :default-checked-keys="checks"
      >
         <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"></i> {{ data.name }}</span>
         </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="saveRoleMenu">Save</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      totalData: [],
      props: {
        label: "name",
      },
      total: 0,
      pageNum: 0,
      pageSize: 10,
      name: "",
      description: "",
      form: {},
      tableData: [],
      dialogFormVisible: false,
      menuDialogVisible: false,
      multipleSection: [],
      menuData: [],
      expends: [],
      checks: [],
      roleId: 0,
      roleFlag: '',
      ids: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/role/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })

      this.request.get("/menu/ids").then(r => {
        this.ids = r.data
      })
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum;
      this.load();
    },
    reset() {
      this.name = "";
      this.description = "";
      this.load();
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    save() {
      this.request.post("/role", this.form).then(res => {

        if (res.code === '200') {
          this.$message.success("Save successfully!")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("Fail to add!")
        }
      })
    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },
    del(id) {
      this.request.delete("/role/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("Delete successfully!")
          this.load()
        } else {
          this.$message.error("Fail to delete!")
        }
      })
    },
    delBatch() {
      let ids = this.multipleSection.map(v => v.id)
      console.log(ids)
      this.request.post("/role/del/batch", ids).then(res => {
        if (res) {
          this.$message.success("Delete successfully!")
          this.load()
        } else {
          this.$message.error("Fail to delete!")
        }
      })
    },
    handleSelectChange(val) {
      this.multipleSection = val
    },
    async selectMenu(role) {
      this.roleId = role.id;
      this.roleFlag = role.flag;
      this.request.get("/menu").then(res => {
        this.menuData = res.data
        //把后台返回的惨淡数据处理成id数组
        this.expends = this.menuData.map(v => v.id);
      })

      this.request.get("/role/roleMenu/" + this.roleId).then(res => {
        this.checks = res.data

        console.log(this.checks)
        this.ids.forEach(id => {
          if (!this.checks.includes(id)) {
            // 可能会报错：Uncaught (in promise) TypeError: Cannot read properties of undefined (reading 'setChecked')
            this.$nextTick(() => {
              this.$refs.tree.setChecked(id, false,false)
              console.log(id)
            })
          }
          this.menuDialogVisible = true
        })
      })

    },
    saveRoleMenu() {
      this.request.post("role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$message.success("Bind successfully!")
          this.menuDialogVisible = false
          if (this.roleFlag === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }

        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>