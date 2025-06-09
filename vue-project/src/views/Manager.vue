<template>
  <div>
    <!--    header-->
    <div class="header" style="height: 60px; display: flex; align-items: center">
      <div style="margin-left: 20px; width: 500px; display: flex; align-items: center">
        <img src="" style="width: 40px; height: 40px; margin-right: 10px" />
        <span style="font-size: 20px; color: white"> Management System </span>
      </div>

      <div style="flex: 1"></div>
      <div style="width: fit-content; display: flex; align-items: center; padding-right: 10px">
        <img src="" style="width: 40px; height: 40px" />
        <span style="color: white; margin-left: 10px"> User Name</span>
      </div>
    </div>
  </div>
  <!--  header ends-->

  <div style="display: flex">
    <!--  left navigation menu-->

    <div style="width: 200px; border-right: 1px solid; color: #ddd; min-height: calc(100vh - 60px)">
      <el-menu router :default-active="router.currentRoute.value.path" :default-openeds="['1']">
        <el-menu-item index="/manager/home">
          <el-icon><House /></el-icon>
          Main
        </el-menu-item>
        <el-menu-item index="/manager/analysis">
          <el-icon><DataAnalysis /></el-icon>
          Analysis</el-menu-item
        >
        <el-sub-menu index="1">
          <template #title>
            <el-icon><User /></el-icon>
            <span>User Management</span>
          </template>
          <el-menu-item>Admin </el-menu-item>
          <el-menu-item index="/manager/employees">Employees </el-menu-item>
        </el-sub-menu>

        <el-menu-item index="/manager/person">
          <el-icon><UserFilled /></el-icon>
          Account
        </el-menu-item>
        <el-menu-item>
          <el-icon><SwitchButton /></el-icon>
          Logout
        </el-menu-item>
      </el-menu>
    </div>
    <!--    left navigation end-->

    <!--    right section starts-->
    <div style="flex: 1; width: 0; background-color: aliceblue; padding: 10px">
      <RouterView></RouterView>
    </div>
  </div>
</template>

<script setup>
import router from '@/router/index.js'
import {
  DataAnalysis,
  Loading,
  Location,
  SwitchButton,
  User,
  UserFilled,
} from '@element-plus/icons-vue'
import request from '@/utils/request.js'
</script>

<style scoped>
.header {
  background-color: var(--primary-color);
}
.el-menu-item.is-active {
  background-color: var(--buton--active-color);
  color: black;
}
</style>
