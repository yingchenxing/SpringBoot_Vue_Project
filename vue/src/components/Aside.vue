<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect">
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top:5px;margin-left: 5px">
      <b style="color: white" v-show="!isCollapse">Management System</b>
    </div>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div  v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
<!--    <el-menu-item index="/">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-house"></i>-->
<!--        <span slot="title">Home</span>-->
<!--      </template>-->
<!--    </el-menu-item>-->

<!--    <el-submenu index="">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="tittle">System Manage</span></template>-->

<!--      <el-menu-item index="/user">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-user"></i>-->
<!--          <span slot="title">User Data</span>-->
<!--        </template>-->
<!--      </el-menu-item>-->

<!--      <el-menu-item index="/role">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-s-custom"></i>-->
<!--          <span slot="title">Role Data</span>-->
<!--        </template>-->
<!--      </el-menu-item>-->

<!--      <el-menu-item index="/menu">-->
<!--        <i class="el-icon-s-custom"></i>-->
<!--        <span slot="title">Menu Data</span>-->
<!--      </el-menu-item>-->

<!--      <el-menu-item index="/file">-->
<!--        <template slot="title">-->
<!--          <i class="el-icon-document"></i>-->
<!--          <span slot="title">File System</span>-->
<!--        </template>-->
<!--      </el-menu-item>-->
<!--    </el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  data(){
    return{
      menus: localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[],
      opens: localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v=>v.id+''):[]
    }
  },
  methods: {
    handleSelect(index) {
      this.$route.fullPath.split("/")
    }
  }
}
</script>

<style scoped>

</style>