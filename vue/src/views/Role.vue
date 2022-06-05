<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="Please enter name" suffix-icon="el-icon-search"
                v-model="username"></el-input>
      <el-input style="width: 200px" placeholder="Please enter email" suffix-icon="el-icon-message"
                v-model="email" class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="Please enter address" suffix-icon="el-icon-position"
                v-model="address" class="ml-5"></el-input>
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


      <el-upload
          action="http://localhost:9090/user/import" style="display: inline-block" :show-file-list="false"
          :accept="xlsx" :on-success="handleExcelImportSuccess"
      >
        <el-button type="primary" class="ml-5">Import<i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">Export<i class="el-icon-top"></i></el-button>
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
      <el-table-column label="Operate">
        <template slot-scope="scope">
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

  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      totalData: [],
      total: 0,
      pageNum: 0,
      pageSize: 10,
      username: "",
      email: "",
      address: "",
      nickname: "",
      form: {},
      tableData: [],
      dialogFormVisible: false,
      multipleSection: [],
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
          username: this.username,
          email: this.email,
          address: this.address
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
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
      this.email = "";
      this.username = "";
      this.address = "";
      this.load();
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    save() {
      this.request.post("/role", this.form).then(res => {

        if (res.code==='200') {
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
        if (res.code==='200') {
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
    exp() {
      window.open("http://localhost:9090/role/export")
    },
    handleExcelImportSuccess(){
      this.$message.success("Successfully import!")
      this.load()
    }
  }
}
</script>

<style scoped>

</style>