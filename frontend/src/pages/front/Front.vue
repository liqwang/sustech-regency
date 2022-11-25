<template>
  <div id="bgi">
    <el-row>
      <div id="head">
        <!-- 酒店名字 -->
        <span style="margin-left: 1vw; font-size: 27px; font-weight: 600; color: #fff">SUSTech Regency</span>
        <!-- 区域选择 -->
        <div style="width: 60vw; height: 100%; background-color: " id="area">
          <el-row justify="space-evenly">
            <el-col :span="8">
              <span class="info">省</span>
              <el-select v-model="province" placeholder="Select">
                <el-option v-for="province in provinces" :key="province" :value="province" @click="changeCity(province)" />
              </el-select>
            </el-col>

            <el-col :span="8">
              <span class="info">市</span>
              <el-select v-model="city" placeholder="Select">
                <el-option v-for="city in cities" :key="city" :value="city" @click="changeRegion(province, city)" />
              </el-select>
            </el-col>

            <el-col :span="8">
              <span class="info">区</span>
              <el-select v-model="region" placeholder="Select">
                <el-option v-for="region in regions" :key="region" :value="region" />
              </el-select>
            </el-col>
          </el-row>
        </div>
        <!-- 用户头像 -->
        <div style="height: 100%; align-self: flex-end; display: inline-flex; align-items: center">
          <UserIcon />
        </div>
      </div>
    </el-row>

    <el-row>
      <div id="main">
        <el-row style="height: 100%">
          <el-col :span="5" v-for="hotelInfo in hotelInfos" style="margin: 25px">
            <el-card class="box-card" shadow="hover" style="border-radius: 10px">
              <router-link :to="'/hotel/' + hotelInfo.id" target="_blank">
                <div><el-image :src="url" /></div>
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
                    ¥<span class="">{{ hotelInfo.minPrice }}</span
                    >起
                  </el-col>
                </el-row>
              </router-link>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-row>

    <el-row justify="center">
      <div id="pages">
        <el-pagination
          v-model:currentPage="currentPage4"
          v-model:page-size="pageSize4"
          :page-sizes="[100, 200, 300, 400]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          style="margin-top: 15px"
        />
      </div>
    </el-row>

    <el-row>
      <div id="foot">
        <p style="color: #fff">
          <span>广东省深圳市南山区学苑大道1088号　</span>
          <span>电话： +86-755-88010888 &nbsp;</span>
          <span>邮编： 518055 </span>
        </p>
        <p style="color: #fff">© 2022 All Rights Reserved.</p>
      </div>
    </el-row>
  </div>
</template>

<style scoped>
#pages {
  height: 7vh;
}
.name {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  font-size: 22px;
  font-weight: 600;
}
.rate {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
}
.comment {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  font-size: 14px;
  color: rgb(66, 66, 66);
}
.price {
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  text-align: center;
  align-items: center;
  font-size: 17px;
  color: #2a598a;
}

.null {
  margin-left: 10px;
  margin-right: 10px;
  height: auto;
}
.info {
  margin-left: 1vw;
  margin-right: 1vw;
  color: rgb(205, 204, 204);
}
#head {
  width: 100%;
  height: 8vh;
  margin-bottom: 1vh;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background-color: #2a598a;
}
#area {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
#bgi {
  background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png');
}
#main {
  width: 100%;
  height: 74vh;
}
#foot {
  width: 100%;
  height: 9vh;
  margin-top: 1vh;
  background-color: #2a598a;

  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;

  font-weight: 350;
  font: #fff;
}
</style>

<script setup lang="ts">
import { reactive } from 'vue'
import UserIcon from '../../components/UserIcon.vue'
import request from '../../utils/request'
import { Search } from '@element-plus/icons-vue'

interface Hotel {
  address: string
  cityName: string
  coverUrl: string
  id: number
  latitude: number
  longitude: number
  name: string
  pictureUrls: string[]
  provinceName: string
  regionName: string
  stars: number
  tel: string
  videoUrls: string[]
}

interface HotelInfo {
  id: number
  name: string
  stars: number
  commentNum: number
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
const listLoading = $ref(false)
const search = () => {}

let provinces = $ref<string[]>([])
let cities = $ref<string[]>([])
let regions = $ref<string[]>([])

request.get('/public/province/all').then((res) => {
  const provinceList = res.data.data as City[]
  provinces = provinceList.map((p) => p.name)
})

const getHotels = (provinceName: string, cityName: string, regionName: string) => {
  request.get(`/public/get-hotels-by-location?CityName=${cityName}&&ProvinceName=${provinceName}&&RegionName=${regionName}`).then((res) => {
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
      console.log('hotelInfos', hotelInfos)
    })
    console.log(hotels)
  })
}

getHotels('', '', '')

const changeCity = (province: string) => {
  console.log('myprovince: ' + province)
  getHotels(province, '', '')
  request.get(`/public/city/all?province=${province}`).then((res) => {
    const cityList = res.data.data as Province[]
    cities = cityList.map((c) => c.name)
    city = ''
    region = ''
    console.log(cities)
  })
}

const changeRegion = (province: string, city: string) => {
  getHotels(province, city, '')
  request.get(`/public/region/all?province=${province}&&city=${city}`).then((res) => {
    const regionList = res.data.data as Region[]
    regions = regionList.map((c) => c.name)
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
