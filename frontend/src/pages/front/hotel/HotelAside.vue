<template>
  <el-scrollbar>
    <el-menu :default-active="router.currentRoute.value.fullPath" class="el-menu-vertical-demo" @open="handleOpen"
      @close="handleClose" router style="border-radius: 15px">
      <el-menu-item :index="'/hotel/' + hotelId + '/introduction'">
        <el-icon>
          <HomeFilled />
        </el-icon>
        <span>酒店简介</span>
      </el-menu-item>
      <el-menu-item :index="'/hotel/' + hotelId + '/bookroom'">
        <el-icon>
          <House />
        </el-icon>
        <span>预订房间</span>
      </el-menu-item>
      <el-menu-item :index="'/hotel/' + hotelId + '/floorplan'">
        <el-icon>
          <MapLocation />
        </el-icon>
        <span>楼层平面图</span>
      </el-menu-item>
      <el-menu-item :index="'/hotel/' + hotelId + '/comment'">
        <el-icon>
          <Comment />
        </el-icon>
        <span>查看评论</span>
      </el-menu-item>
      <!-- 这里添加左侧菜单栏的chat栏 -->
      <el-menu-item :index="'/hotel/' + hotelId + '/chat'">
        <el-icon>
          <Comment />
        </el-icon>
        <span>联系客服</span>
      </el-menu-item>
    </el-menu>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import { HomeFilled, House, MapLocation, Comment } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const activePath = $ref()

interface HotelInfo {
  id: number
  latitude: number
  longitude: number
  name: string
  tel: string
  address: string
  provinceName: string
  cityName: string
  regionName: string
  stars: number
  coverUrl: string
  videoUrls: string[]
  pictureUrls: string[]
  minPrice: number
  commentNum: number
  likes_num: number
}

const hotelInfo = $ref<HotelInfo>()

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)
</script>
