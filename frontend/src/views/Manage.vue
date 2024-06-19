<template>
  <el-container style="height: 100%; ">

    <el-aside width="sideWidth + 'px'" style="box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow" />
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
      </el-header>

      <el-main>
        <router-view @refreshUser="getUser"/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  name: "Home",
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getUser()
  },
  components:{
    Header,
    Aside
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser(){
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      // 从后台获取数据
      this.request.get("/user/username/" + username).then(res => {
        this.user = res.data
      })
    },
  }
}
</script>

<style>

</style>
