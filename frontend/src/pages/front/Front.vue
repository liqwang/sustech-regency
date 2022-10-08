<template>
  <div class="header">
    <el-row>
      <el-col :span="8" background-color="red"> SUSTECH Rengency </el-col>
      <el-col :span="12">国内搜索</el-col>
      <el-col :span="4">
        <el-button @click="login" type="primary" round bg>登录</el-button>
        <el-button @click="signup" type="primary" round bg>注册</el-button>
        <el-dropdown trigger="click">
          <span style="margin-left: 12px">
            <el-avatar :icon="UserFilled"></el-avatar>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="signup">注册</el-dropdown-item>
              <el-dropdown-item @click="login">登录</el-dropdown-item>
              <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-col>
    </el-row>
    <el-carousel height="150px">
      <el-carousel-item v-for="item in 4" :key="item">
        <h3 class="small justify-center" text="2xl">{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup lang="ts">
import { UserFilled } from '@element-plus/icons-vue';
import router from '../../router';

const signup = () => {
  router.push('/signup');
}

const login = () => {
  router.push('/login');
}

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  router.push('/login');
};
</script>

<style scoped>
.header {
  margin-top: 10px;
}
</style>
