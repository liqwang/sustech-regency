<template>
  <div id="bgi">
    <el-row>
      <el-container>
        <el-header id="head">
          <el-col :span="12" :offset="0">
            <a href="/" id="back" target="_blank">SUSTech Regency </a>
            <div id="cur-hotel" style="display: inline"> 当前酒店:{{ hotelId }}</div>
            <div id="cur-hotel" style="display: inline"> 当前酒店商家:{{ hotel_owner }}</div>
          </el-col>

          <el-col :span="12" :offset="0" id="user">
            <UserIcon />
          </el-col>
        </el-header>
      </el-container>
    </el-row>
    <el-row>
      <el-container id="body">
        <el-col :span="3" :offset="0">
          <el-container>
            <el-main>
              <div id="aside">
                <HotelAside />
              </div>
            </el-main>
          </el-container>
        </el-col>
        <el-col :span="21" :offset="0" id="mian">
          <el-container>
            <el-main>
              <router-view :key="route.path"></router-view>
            </el-main>
          </el-container>
        </el-col>
      </el-container>
    </el-row>
  </div>
</template>

<style scoped>
#bgi {
  background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png');
  padding-left: 20px;
  padding-right: 20px;
}

#head {
  height: 8vh;
  display: inline-flex;
  flex-direction: row;
  align-items: center;
}

#back {
  order: 0;
  display: inline-flex;

  font-size: 1.5rem;
  font-weight: 550;
}

#user {
  order: 1;
  display: inline-flex;
  align-items: center;
  flex-direction: row;
  justify-content: flex-end;
  height: 100%;
  right: 20px;
}

#body {
  height: 92vh;
  width: 100vw;
}

#aside {
  width: 100%;
  height: 100%;
}

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
  margin: 0;
}

.layout-container-demo {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

.el-main {
  padding: 0.5rem;
}
</style>

<script lang="ts" setup>
import { ref } from 'vue'
import { Setting } from '@element-plus/icons-vue'
import HotelAside from './HotelAside.vue'
import UserIcon from '../../../components/UserIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import request from '../../../utils/request'

const route = useRoute()
const router = useRouter()
const hotelId = $ref(router.currentRoute.value.params['hotelId'])

let hotel_owner = $ref('')
request.get(`/public/merchant-username?hotelId=${hotelId}`).then(res => {
  console.log(res.data.data)
  hotel_owner = res.data.data
})
</script>
