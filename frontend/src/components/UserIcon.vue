<template>
  <span v-if="!token">
    <el-button @click="login" type="primary" round bg>登录</el-button>
    <el-button @click="signup" type="primary" round bg>注册</el-button>
  </span>
  <el-dropdown trigger="click">
    <span style="margin-left: 12px">
      <div v-if="avatarUrl">
        <el-avatar :src="avatarUrl"></el-avatar>
      </div>
      <div v-else>
        <el-avatar :icon="UserFilled"></el-avatar>
      </div>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="toFrontPage">首页</el-dropdown-item>
        <span v-if="isMerchant">
          <el-dropdown-item @click="toMerchantPage">管理端</el-dropdown-item>
        </span>
        <span v-if="!token">
          <el-dropdown-item @click="login">登录</el-dropdown-item>
        </span>
        <el-dropdown-item @click="signup">注册</el-dropdown-item>
        <span v-if="token">
          <el-dropdown-item divided @click="toUserPage">个人中心</el-dropdown-item>
          <el-dropdown-item @click="toChangePasswordPage">修改密码</el-dropdown-item>
          <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
        </span>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <div class="ml-4">{{ username }}</div>
</template>

<script setup lang="ts">
import { UserFilled } from '@element-plus/icons-vue'
import { ElNotification } from 'element-plus';
import { h } from 'vue';
import router from '../router'

const token = $ref(localStorage.token ? JSON.parse(localStorage.token) : '')
const user = $ref(localStorage.getItem('user'))
const username = user ? JSON.parse(localStorage.getItem('user') as string).name : ''
const userId = user ? JSON.parse(localStorage.getItem('user') as string).id : null

const avatarUrl = user ? JSON.parse(localStorage.getItem('user') as string).headshotUrl : ''
const isMerchant = user ? JSON.parse(localStorage.getItem('user') as string).isMerchant : false

const login = () => {
  router.push('/login')
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.clear()
  router.push('/login')
  ElNotification({
    title: 'Success',
    message: h('i', { style: 'color: green' }, '退出登录')
  })
}

const signup = () => {
  router.push('/signup')
}

const toUserPage = () => {
  let userRouteUrl = router.resolve({
    path: '/user/order'
  })
  window.open(userRouteUrl.href, '_blank')
}

const toChangePasswordPage = () => {
  let userRouteUrl = router.resolve({
    path: '/changepassword'
  })
  window.open(userRouteUrl.href, '_blank')
}

const toFrontPage = () => {
  let userRouteUrl = router.resolve({
    path: '/'
  })
  window.open(userRouteUrl.href, '_blank')
}

const toMerchantPage = () => {
  let userRouteUrl = router.resolve({
    path: '/merchant'
  })
  window.open(userRouteUrl.href, '_blank')
}
</script>

<style scoped>

</style>
