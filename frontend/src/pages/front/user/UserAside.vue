<template>
  <el-scrollbar>
    <h5 class="mb-2">Sustech Rengency</h5>
    <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
      <el-menu-item index="1">
        <el-icon>
          <HomeFilled />
        </el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-menu-item index="/user/order">
        <el-icon>
          <Message />
        </el-icon>
        <span>我的订单</span>
      </el-menu-item>
      <el-menu-item index="3">
        <el-icon>
          <House />
        </el-icon>
        <span>我的积分</span>
      </el-menu-item>
      <el-menu-item index="/user/setting">
        <el-icon>
          <Setting />
        </el-icon>
        <span>我的信息</span>
      </el-menu-item>
    </el-menu>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import { HomeFilled, House, Setting, Message } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const router = useRouter()
</script>
