<template>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="200px">
      <HotelAside />
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <UserIcon />
        </div>
      </el-header>
      <el-main>
        <router-view :key="route.path"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { Setting } from '@element-plus/icons-vue'
import HotelAside from './HotelAside.vue'
import UserIcon from '../../../components/UserIcon.vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter()

const hotelId = $ref(router.currentRoute.value.params['hotelId'])
console.log('hotelId: ', hotelId)

</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>
