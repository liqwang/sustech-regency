<template>
  <span v-if="!token">
    <el-button @click="login" type="primary" round bg>登录</el-button>
    <el-button @click="signup" type="primary" round bg>注册</el-button>
  </span>
  <el-dropdown trigger="click">
    <span style="margin-left: 12px">
      <el-avatar :icon="UserFilled"></el-avatar>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <span v-if="!token">
          <el-dropdown-item @click="signup">注册</el-dropdown-item>
          <el-dropdown-item @click="login">登录</el-dropdown-item>
        </span>
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
import router from '../router'

const token = $ref(localStorage.getItem('token'))
const user = $ref(localStorage.getItem('user'))
const username = user ? JSON.parse(localStorage.getItem('user') as string).name : ''
const userId = user ? JSON.parse(localStorage.getItem('user') as string).id : null

const login = () => {
  router.push('/login')
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.clear()
  router.push('/login')
}

const signup = () => {
  router.push('/signup')
}

const toUserPage = () => {
  let userRouteUrl = router.resolve({
    path: '/user'
  })
  window.open(userRouteUrl.href, '_blank')
}

const toChangePasswordPage = () => {
  let userRouteUrl = router.resolve({
    path: '/changepassword'
  })
  window.open(userRouteUrl.href, '_blank')
}
</script>

<style scoped>

</style>
