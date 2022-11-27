<template>
  <el-card class="box-card" style="border-radius: 15px">
    <b style="font-size: 25px">{{ hotelInfo ? hotelInfo.name : '' }}</b>
    <br />
    <div>
      <el-icon :size="20">
        <Location />
      </el-icon>
      {{ hotelInfo ? hotelInfo.provinceName : '' }}, {{ hotelInfo ? hotelInfo.cityName : '' }}, {{ hotelInfo ?
          hotelInfo.regionName : ''
      }}
    </div>
    <br />
    <el-row>
      <el-col :span="12">
        <div class="block text-center">
          <el-carousel height="500px">
            <el-carousel-item v-for="url in pictureUrls" :key="url">
              <h3 class="small justify-center" text="2xl">
                <img :src="url" />
              </h3>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col :span="10" :offset="1">
        <div m="t-10">
          <div>
            <el-rate v-model="rate" disabled show-score text-color="#ff9900" score-template="{value}分/5分" />
            <br />
            <b style="font-size: 20px">￥{{ minPrice }}</b>
            起
          </div>
          <div>{{ commentNum }}条评论</div>
          <br />
          <br />
          <article>
            {{ hotelInfo ? hotelInfo.description : '' }}
          </article>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
import { Location } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router';
import request from '../../../utils/request';

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
  description: string
}

const route = useRoute()
const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

let hotelInfo = $ref<HotelInfo>()
let rate = $ref(hotelInfo ? hotelInfo.stars : 0.0)
let commentNum = $ref(hotelInfo ? hotelInfo.commentNum : 0)
let minPrice = $ref(hotelInfo ? hotelInfo.minPrice : 0)
let pictureUrls = $ref(hotelInfo ? hotelInfo.pictureUrls : [])

request.get(`/public/get-hotelInfo-byId?hotelId=${hotelId}`)
  .then(res => {
    hotelInfo = res.data.data as HotelInfo
    console.log(hotelInfo)
    rate = hotelInfo.stars
    commentNum = hotelInfo.commentNum
    minPrice = hotelInfo.minPrice
    pictureUrls = hotelInfo.pictureUrls
  })


</script>

<style scoped>

</style>
