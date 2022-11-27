<template>
  <div id="bgi">
    <el-card class="box-card" style="border-radius: 15px">
      <el-scrollbar>
        <div>
          <el-rate v-model="rate" disabled show-score text-color="#ff9900" score-template="{value}分/5分" />
          <br />
        </div>
        <div>{{ commentNum }}条评论</div>
        <div style="margin: 30px 0">
          <div style="margin: 10px 0">
            <div style="border-bottom: 1px solid orangered; padding: 10px 0; font-size: 20px">评论</div>
            <!-- <div style="padding: 10px 0">
              <el-input size="small" type="textarea" v-model="commentContent"></el-input>
            </div>
            <div class="" style="text-align: right">
              <el-button type="primary" size="small" @click="saveComment">评论</el-button>
            </div> -->
          </div>

          <div>
            <div v-for="item in commentList" :key="item.id"
              style="border-bottom: 1px solid #ccc; padding: 10px 0; display: flex">
              <div style="width: 100px; text-align: center">
                <el-image :src="item.headshotUrl" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
              </div>
              <div style="flex: 1; font-size: 14px; padding: 5px 0; line-height: 25px">
                <b> {{ item.username }} </b>:
                <span> {{ item.content }} </span>

                <div style="display: flex; line-height: 20px; margin-top: 5px">
                  <div style="width: 200px">
                    <el-icon>
                      <Timer />
                    </el-icon>
                    <span style="margin-left: 5px">{{ item.time }}</span>
                  </div>
                  <!-- <div style="text-align: right; flex: 1;">
                    <el-button style="margin-left: 5px" type="text">回复</el-button>
                    <el-button type="text" style="color: red">删除</el-button>
                  </div> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-scrollbar>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { Timer } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import request from '../../../utils/request';

interface Comment {
  id: number
  username: string
  headshotUrl: string
  time: string
  content: string
}

let commentContent = $ref('')

const saveComment = () => { }

const comment: Comment = {
  id: 1,
  username: 'RockyCYG',
  headshotUrl: 'https://quanquancho.com:8080/public/file/2022/10/30/ef284062fe96417ea17daf3bf1f92b42.jpg',
  time: '2022-10-30',
  content: '南科大很棒！'
}

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

const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

let hotelInfo = $ref<HotelInfo>()
let rate = $ref(hotelInfo ? hotelInfo.stars : 0.0)
let commentNum = $ref(hotelInfo ? hotelInfo.commentNum : 0)
let minPrice = $ref(hotelInfo ? hotelInfo.minPrice : 0)

request.get(`/public/get-hotelInfo-byId?hotelId=${hotelId}`)
  .then(res => {
    hotelInfo = res.data.data as HotelInfo
    console.log(hotelInfo)
    rate = hotelInfo.stars
    commentNum = hotelInfo.commentNum
    minPrice = hotelInfo.minPrice
  })

const commentList = Array.from({ length: 20 }).fill(comment) as Comment[]
console.log("router", router.currentRoute.value.fullPath)
</script>

<style scoped>
#bgi {
  /* background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png'); */
  width: auto;
  height: 86vh;
}
</style>
