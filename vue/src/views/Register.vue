<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 350px;height:360px;padding:20px;border-radius:10px">
      <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>Register</b></div>
      <el-form :rules="rules" :model="user" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="Please enter your username." size="medium" style="margin:10px 0" prefix-icon="el-icon-user"
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="Please enter your password." size="medium" style="margin:10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="Please confirm your password." size="medium" style="margin:10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <div style="margin:10px 0;text-align:right">
          <el-button type="primary" size="small" v-model=this.user.username autocomplete="off" @click="login">Register
          </el-button>
          <el-button type="warning" size="small" v-model=this.user.password autocomplete="off" @click="$router.push('/login')">Back to login</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          {required: true, message: 'Please enter username', trigger: 'blur'},
          {min: 3, max: 20, message: '3 to 20 characters in length', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'},
          {min: 3, max: 20, message: '3 to 20 characters in length', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: 'Please confirm your password', trigger: 'blur'},
          {min: 3, max: 20, message: '3 to 20 characters in length', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          if(this.user.password !==this.user.confirmPassword){
            this.$message.error("Entered passwords do not match!");
            return false;
          }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === '200') {
              this.$message.success("Successfully register!")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #36495f, #42b984);
  overflow: hidden;
}
</style>