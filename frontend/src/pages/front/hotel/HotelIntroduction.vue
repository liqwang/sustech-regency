<template>
  <el-card class="box-card" style="border-radius: 15px">
    <b style="font-size: 25px">{{ hotelInfo?.name }}</b>
    <br />
    <div>
      <el-icon :size="20">
        <Location />
      </el-icon>
      {{ hotelInfo?.provinceName }}, {{ hotelInfo?.cityName }}, {{ hotelInfo?.regionName }}, {{ hotelInfo?.address }}
    </div>
    <br />
    <el-row>
      <el-col :span="12">
        <div class="block text-center">
          <el-carousel height="500px" v-if="hotelInfo?.pictureUrls.length > 0">
            <el-carousel-item v-for="url in hotelInfo?.pictureUrls" :key="url">
              <h3 class="small justify-center" text="2xl">
                <!-- <img :src="url" /> -->
                <el-image style="width: 100%; height: 500px" :src="url" fit="contain" />
              </h3>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col :span="10" :offset="1">
        <div m="t-10">
          <div>
            <el-rate v-model="stars" :colors="colors" disabled text-color="#ff9900" />
            <br />
            <b style="font-size: 20px">￥{{ hotelInfo?.minPrice }}</b>
            起
            <div>
              <div>共{{ hotelInfo.likes_num }}人收藏</div>
            </div>
          </div>
          <br />
          <br />
          <article>
            {{ hotelInfo?.description }}
          </article>
        </div>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup lang="ts">
import { Location } from '@element-plus/icons-vue'
import { HotelInfo } from '../../../type/type.d'

const props = defineProps<{
  hotelInfo: HotelInfo
}
>()

const colors = $ref(['#99A9BF', '#F7BA2A', '#FF9900'])

const hotelInfo = $ref(props.hotelInfo)
let stars = $ref(hotelInfo?.stars)

console.log('hotelInfo: ', hotelInfo)

</script>

<style scoped>

</style>
