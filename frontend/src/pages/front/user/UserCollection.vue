<template>
  <el-card>
    我的收藏
  </el-card>
  <br>
  <el-card>
    <el-scrollbar>
      <el-row style="height: 100%" justify="space-evenly">
        <el-col :span="5" v-for="hotelInfo in hotelInfos">
          <el-card class="box-card" shadow="hover" style="border-radius: 10px; margin-top: 10px; height: 33vh">
            <router-link :to="'/hotel/' + hotelInfo.id + '/introduction'" target="_blank">
              <div>
                <el-image :src="hotelInfo.coverUrl" style="width: 100%; height: 20vh" fit="contain" />
              </div>
              <el-row>
                <el-col :span="12" :offset="0">
                  <div class="name" s>{{ hotelInfo.name }}</div>
                </el-col>
                <el-col :span="12" :offset="0">
                  <div class="rate">
                    <el-rate v-model="hotelInfo.stars" disabled text-color="#ff9900" />
                  </div>
                </el-col>
                <el-col :span="12" :offset="0" class="comment">
                  <div class="comment">
                    <div>共{{ hotelInfo.commentNum }}条评论</div>
                  </div>
                </el-col>
                <el-col :span="12" :offset="0" class="price">
                  ¥<span class="">{{ hotelInfo.minPrice }}</span>起
                </el-col>
              </el-row>
            </router-link>
          </el-card>
        </el-col>
      </el-row>
      <el-row justify="center">
        <div id="pages">
          <el-pagination v-model:currentPage="pageNum" v-model:page-size="pageSize" :page-sizes="[5, 10, 15, 20]"
            :small="small" :disabled="disabled" :background="background"
            layout="total, sizes, prev, pager, next, jumper" :total="totalNum" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" style="margin-top: 15px" />
        </div>
      </el-row>
    </el-scrollbar>
  </el-card>
</template>

<script setup lang="ts">
import { HotelInfo } from '../../../type/type'
import request from '../../../utils/request'

interface IPage {
  records: HotelInfo[]
  total: string
}

let pageNum = $ref(1)
let pageSize = $ref(5)
let totalNum = $ref(0)
const small = $ref(false)
const background = $ref(false)
const disabled = $ref(false)

let pages = $ref<IPage>()
let hotelInfos = $ref<HotelInfo[]>([])

request.get('/consumer/get-likes').then(res => {
  pages = res.data.data.records
  hotelInfos = pages.records
  console.log('hotelInfos: ', hotelInfos)
  totalNum = parseInt(pages.total)
})

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
}
</script>

<style scoped>

</style>