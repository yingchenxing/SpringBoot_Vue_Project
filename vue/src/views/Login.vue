<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff;width: 350px;height:300px;padding:20px;border-radius:10px">
      <div style="margin: 20px 0; text-align: center;font-size: 24px"><b>Login</b></div>
      <el-form :rules="rules" :model="user" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin:10px 0" prefix-icon="el-icon-user"
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin:10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <div style="margin:10px 0;text-align:right">
          <el-button type="primary" size="small" v-model=this.user.username autocomplete="off" @click="login">Login
          </el-button>
          <el-button type="warning" size="small" v-model=this.user.password autocomplete="off" @click="$router.push('/register')">Register</el-button>
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
          {min: 1, max: 10, message: '1 to 10 characters in length', trigger: 'blur'}
        ],
        password: [
          {required: true, message: 'Please enter password', trigger: 'blur'},
          {min: 1, max: 10, message: '1 to 10 characters in length', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res => {
            if (res.code != '200') {

              this.$message.error(res.msg)
            } else {
              localStorage.setItem("user",JSON.stringify(res.data))
              localStorage.setItem("menus",JSON.stringify(res.data.menus ))
              this.$router.push("/");
              this.$message.success("Login successfully!")
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