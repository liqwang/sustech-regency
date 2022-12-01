<template>
  <div id="bgi">
    <el-card class="box-card" style="border-radius: 15px">
      <el-scrollbar>
        <div>
          <el-rate v-model="stars" disabled show-score text-color="#ff9900" score-template="{value}分/5分" />
          <br />
        </div>
        <div>{{ hotelInfo?.commentNum }}条评论</div>
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
            <div v-for="comment in commentList" style="border-bottom: 1px solid #ccc; padding: 10px 0; display: flex">
              <div style="width: 100px; text-align: center">
                <el-image :src="comment.headShotUrl" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
                <div></div>
                <b>{{ comment.userName }}</b>
              </div>
              <div style="flex: 1; font-size: 14px; padding: 5px 0; line-height: 25px; margin-left: 20px;">
                <el-rate v-model="comment.stars" disabled show-score text-color="#ff9900" score-template="{value}分/5分">
                </el-rate>
                <div> {{ comment.comment }} </div>
                <el-row :gutter="2">
                  <el-col :span="4" v-for="imageUrl in comment.pictureUrls">
                    <el-image style="width: 100%;" :src="imageUrl" fit="contain"
                      :preview-src-list="comment.pictureUrls"></el-image>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col v-for="videoUrl in comment.videoUrls">
                    <video :src="videoUrl"></video>
                  </el-col>
                </el-row>
                <!-- <video src="https://quanquancho.com:8080/public/file/2022/11/29/379552bc34c84d97a58b4237b477e7ba.mp4"></video> -->

                <div style="display: flex; line-height: 20px; margin-top: 5px">
                  <div style="width: 200px">
                    <el-icon>
                      <Timer />
                    </el-icon>
                    <span style="margin-left: 5px">{{ comment.commentTime }}</span>
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
import { Timer } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router';
import { HotelInfo } from '../../../type/type.d';
import request from '../../../utils/request';

interface Comment {
  commentTime: string
  comment: string
  userName: string
  hotelName: string
  roomType: string
  stars: number
  videoUrls: string[]
  pictureUrls: string[]
  headShotUrl: string
}

const router = useRouter()

const props = defineProps<{
  hotelInfo: HotelInfo
}
>()

let commentList = $ref<Comment[]>([])

const hotelInfo = $ref(props.hotelInfo)
const stars = $ref(hotelInfo?.stars)

console.log("router", router.currentRoute.value.fullPath)

const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

request.get(`/public/get-hotelComments?hotelId=${hotelId}`).then(res => {
  commentList = res.data.data
  console.log('commentList: ', commentList)
})
</script>

<style scoped>
#bgi {
  /* background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png'); */
  width: auto;
  height: 86vh;
}
</style>
