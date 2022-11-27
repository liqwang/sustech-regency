<template>
  <div class="common-layout">
    <el-container>
      <el-header style="background-color: aliceblue; height: 5vh">
        <el-breadcrumb separator="/" style="top: 2vh; position: absolute">
          <el-breadcrumb-item :to="{ path: '/' }">home</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/merchant' }">merchant</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: '/merchant/room' }">room</el-breadcrumb-item>
        </el-breadcrumb>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: antiquewhite; height: 100vh">
          <el-menu active-text-color="#ffd04b" background-color="antiquewhite" default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
            <el-menu-item index="/merchant/room/detail">
              <template #title>
                <el-icon><location /></el-icon>
                <span>Information</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/merchant/room/order">
              <el-icon><icon-menu /></el-icon>
              <span>Orders</span>
            </el-menu-item>
            <el-menu-item index="/merchant/room/picture">
              <el-icon><document /></el-icon>
              <span>Picture</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script lang="ts" setup>
import { ref, reactive, Ref } from 'vue'
import router from '../../router'
import { LocationQuery, LocationQueryValue, useRoute } from 'vue-router'
import request from '../../utils/request'
import { Document, Menu as IconMenu, Location, Setting } from '@element-plus/icons-vue'
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>
<style lang="scss" scoped>
#d1 {
  background-color: burlywood;
}
</style>
