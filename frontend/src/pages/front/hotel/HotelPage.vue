<template>
  <div id="bgi">
    <el-row>
      <el-container>
        <el-header id="head">
          <el-col :span="18" :offset="0">
            <a href="/" id="back" target="_blank">SUSTech Regency </a>
            <div id="cur-hotel" style="display: inline; margin-left: 60px;"> 当前酒店:{{ hotelInfo?.name }}</div>
            <div id="cur-hotel" style="display: inline; margin-left: 60px;"> 当前酒店商家:{{ hotel_owner }}</div>
            <span v-if="collectStatus">
              <el-button type="primary" style="width: 100px; margin-left: 60px" :icon="Star" @click="cancelCollect">
                取消收藏
              </el-button>
            </span>
            <span v-else>
              <el-button style="width: 100px; margin-left: 60px" :icon="Star" @click="collect">
                收藏
              </el-button>
            </span>
          </el-col>

          <el-col :span="6" :offset="0" id="user">
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
        <el-col :span="21" :offset="0">
          <el-container>
            <el-main>
              <router-view v-if="hotelInfo" :key="route.path" :hotelInfo="hotelInfo"></router-view>
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
import HotelAside from './HotelAside.vue'
import UserIcon from '../../../components/UserIcon.vue'
import { useRoute, useRouter } from 'vue-router'
import request from '../../../utils/request'
import { HotelInfo } from '../../../type/type'
import { Star } from '@element-plus/icons-vue'
import { ElMessage, ElNotification } from 'element-plus'
import { h } from 'vue'

let hotelInfo = $ref<HotelInfo>()

const route = useRoute()
const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

request.get(`/public/get-hotelInfo-byId?hotelId=${hotelId}`)
  .then(res => {
    hotelInfo = res.data.data as HotelInfo
  })

let hotel_owner = $ref('')
request.get(`/public/merchant-username?hotelId=${hotelId}`).then(res => {
  console.log(res.data.data)
  hotel_owner = res.data.data
})

let collectStatus = $ref(false)

const collect = () => {
  request.post(`/consumer/like-hotel?hotelId=${hotelId}`).then(res => {
    if (res.data.code === 200) {
      ElNotification({
        title: "Success",
        message: h("i", { style: "color: teal" }, "收藏成功！"),
      })
      collectStatus = true
    } else if (res.data.code === 401) {
      ElNotification({
        title: "Information",
        message: h("i", { style: "color: teal" }, "请先登录！"),
      })
    }
  })
}

const cancelCollect = () => {
  request.post(`/consumer/dislike-hotel?hotelId=${hotelId}`).then(res => {
    if (res.data.code === 200) {
      ElNotification({
        title: "Success",
        message: h("i", { style: "color: teal" }, "取消收藏成功"),
      })
      collectStatus = false
    }
  })
}

let collectHotels = $ref<number[]>([])

const token = localStorage.getItem('token')

if (token) {
  request.get('/consumer/get-likes-all').then(res => {
    const hotels = res.data.data as HotelInfo[]
    console.log('hotels: ', hotels)
    collectHotels = hotels.map(hotel => hotel.id)
    console.log('collectHotels: ', collectHotels)
    collectStatus = collectHotels.includes(hotelId)
  })
}

</script>
