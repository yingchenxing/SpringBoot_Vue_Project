<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth+'px'"
              style="background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0,21,41,0.35)">
      <Aside :isCollapse="isCollapse"/>
    </el-aside>

    <el-container>

      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" @asideCollapse="collapse" :user="user"/>
      </el-header>

      <el-main>
        <!--        表示当前页面的子路由会在router-view中展示-->
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  components: {
    Header,
    Aside,
    HelloWorld
  },
  created() {
    this.getUser();
  },
  data() {
    return {

      msg: "hello,world",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      user: {}
    };
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64;
        this.collapseBtnClass = 'el-icon-s-unfold';
      } else {
        this.sideWidth = 200;
        this.collapseBtnClass = 'el-icon-s-fold';
      }
    },
    getUser() {
      let username = localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username : {};
      this.request.get("/user/username/" + username).then(res => {
        this.user = res.data
      })
    },
  }
}
</script>

<style scoped>
</style>
