<template>
  <div class="header">
    <el-row>
      <el-col :span="6" :offset="2" background-color="red"> SUSTECH Rengency</el-col>
      <el-col :span="3" :offset="3">国内搜索</el-col>
      <el-col :span="4" :offset="6">
        <UserIcon />
      </el-col>
    </el-row>
    <el-carousel height="500px" align="center">
      <el-carousel-item v-for="url in urls" :key="url">
        <h3 class="small justify-center" text="2xl">
          <el-image :src="url" />
        </h3>
      </el-carousel-item>
    </el-carousel>
  </div>
  <div>
    <el-row justify="space-evenly">
      <el-col :span="4">
        省
        <el-select v-model="province" placeholder="Select">
          <el-option v-for="province in provinces" :key="province" :value="province" @click="changeCity(province)" />
        </el-select>
      </el-col>

      <el-col :span="4">
        市
        <el-select v-model="city" placeholder="Select">
          <el-option v-for="city in cities" :key="city" :value="city" @click="changeRegion(province, city)" />
        </el-select>
      </el-col>

      <el-col :span="4">
        区
        <el-select v-model="region" placeholder="Select">
          <el-option v-for="region in regions" :key="region" :value="region" />
        </el-select>
      </el-col>

      <el-col :span="4">
        <el-button type="primary" style="width: 100px; margin-left: 10px;" :icon="Search" @click="search"
          :disabled="listLoading">
          查询
        </el-button>
      </el-col>
    </el-row>
  </div>
  <br />
  <div>
    <el-row v-for="hotelInfo in hotelInfos" justify="space-evenly" :key="hotelInfo" class="mb-5">
      <el-col v-for="col in 3" :key="col" :span="7">
        <div style="boxShadow: var(--el-box-shadow); padding: 10px;">
          <!-- TODO: -->
          <router-link :to='"/hotel/" + hotelInfo.id' target="_blank">
            <el-image :src="url" />
            南科大专家公寓
          </router-link>
          <span class="ml-12">{{ hotelInfo.commentNum }}条评论</span>
          <span class="ml-12">￥{{ hotelInfo.minPrice }}起</span>
          <br>
          <el-rate v-model="hotelInfo.stars" disabled show-score text-color="#ff9900" score-template="{value}分/5分" />
        </div>
      </el-col>
    </el-row>
  </div>
  <div class="ml-12" style="margin-bottom: 200px;">
    <el-pagination v-model:currentPage="currentPage4" v-model:page-size="pageSize4" :page-sizes="[100, 200, 300, 400]"
      :small="small" :disabled="disabled" :background="background" layout="total, sizes, prev, pager, next, jumper"
      :total="400" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
  </div>
  <el-divider></el-divider>
  <el-row style="margin-bottom: 100px;" justify="space-evenly">关于我们 | 联系我们 | 联系客服</el-row>
  <el-row type="flex" style="margin-bottom: 100px;" justify="space-evenly">
    <el-col :offset="2" :span="5">广东省深圳市南山区学苑大道1088号 </el-col>
    <el-col :offset="2" :span="4">电话: +86-755-88010114</el-col>
    <el-col :offset="2" :span="4">邮编: 518055</el-col>
  </el-row>

</template>

<script setup lang="ts">
import { reactive } from 'vue';
import UserIcon from '../../components/UserIcon.vue';
import request from '../../utils/request'
import { Search } from '@element-plus/icons-vue'

interface Hotel {
  address: string,
  cityName: string,
  coverUrl: string,
  id: number,
  latitude: number,
  longitude: number,
  name: string,
  pictureUrls: string[],
  provinceName: string,
  regionName: string,
  stars: number,
  tel: string,
  videoUrls: string[]
}

interface HotelInfo {
  id: number,
  name: string,
  stars: number,
  commentNum: number,
  minPrice: number
}

interface Province {
  id: number
  name: string
  abbreviation: string
}

interface City {
  id: number
  name: string
  provinceId: number
  isProvincialCapital: boolean
}

interface Region {
  id: number
  name: string
  cityId: number
}

const token = $ref(localStorage.token ? JSON.parse(localStorage.token) : '')
const username = $ref(localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string).name : '')

let hotels: Hotel[] = []
let hotelInfos = reactive<HotelInfo[]>([])

let province = $ref('')
let city = $ref('')
let region = $ref('')

const url = 'https://z1.muscache.cn/im/pictures/24e8ce37-6b7a-41f7-99b1-e21319705bb0.jpg?aki_policy=large'

const urls = [
  'https://z1.muscache.cn/im/pictures/642803/e0a0ddc9_original.jpg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/miso/Hosting-45337054/original/d0e063e3-f760-4160-8f56-ec1dcc7d7392.jpeg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large'
]

let provinces = $ref<string[]>([])
let cities = $ref<string[]>([])
let regions = $ref<string[]>([])

const listLoading = $ref(false)

const search = () => {

}

request.get('/public/province/all').then(res => {
  const provinceList = res.data.data as City[]
  provinces = provinceList.map(p => p.name)
})

const getHotels = (provinceName: string, cityName: string, regionName: string) => {
  request.get(`/public/get-hotels-by-location?CityName=${cityName}&&ProvinceName=${provinceName}&&RegionName=${regionName}`).then(res => {
    hotels = res.data.data
    console.log('response data: ', hotels)
    hotels.forEach(async (hotel) => {
      let response = await request.get(`/public/get-comment_number-by-hotel?hotelId=${hotel.id}`)
      const comment = response.data.data
      response = await request.get(`/public/get-min_price-by-hotel?hotelId=${hotel.id}`)
      const price = response.data.data
      let hotelInfo: HotelInfo = {
        id: hotel.id,
        name: hotel.name,
        stars: hotel.stars,
        commentNum: comment,
        minPrice: price
      }
      hotelInfos.push(hotelInfo)
      console.log("hotelInfos", hotelInfos)
    })
    console.log(hotels)
  })
}

getHotels('', '', '')

const changeCity = (province: string) => {
  console.log("myprovince: " + province)
  getHotels(province, '', '')
  request.get(`/public/city/all?province=${province}`).then(res => {
    const cityList = res.data.data as Province[]
    cities = cityList.map(c => c.name)
    city = ''
    region = ''
    console.log(cities)
  })
}

const changeRegion = (province: string, city: string) => {
  getHotels(province, city, '')
  request.get(`/public/region/all?province=${province}&&city=${city}`).then(res => {
    const regionList = res.data.data as Region[]
    regions = regionList.map(c => c.name)
    region = ''
    console.log(cities)
  })
}

const currentPage4 = $ref(1)
const pageSize4 = $ref(100)
const small = $ref(false)
const background = $ref(false)
const disabled = $ref(false)

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
}
</script>

<style scoped>
.header {
  margin-top: 10px;
  margin-bottom: 30px;
}
</style>
