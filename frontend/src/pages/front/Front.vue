<template>
  <div class="header">
    <el-row>
      <el-col :span="6" :offset="2" background-color="red"> SUSTECH Rengency</el-col>
      <el-col :span="3" :offset="3">国内搜索</el-col>
      <el-col :span="4" :offset="6">
        <span v-if="!token">
          <el-button @click="login" type="primary" round bg>登录</el-button>
          <el-button @click="signup" type="primary" round bg>注册</el-button>
        </span>
        <el-dropdown trigger="click">
          <span style="margin-left: 12px">
            <el-avatar :icon="UserFilled"></el-avatar>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <span v-if="!token">
                <el-dropdown-item @click="signup">注册</el-dropdown-item>
                <el-dropdown-item @click="login">登录</el-dropdown-item>
              </span>
              <span v-if="token">
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </span>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <div class="ml-4">{{ username }}</div>
      </el-col>
    </el-row>
    <!--    <el-carousel height="500px">-->
    <!--      <el-carousel-item v-for="url in urls" :key="url">-->
    <!--        <h3 class="small justify-center" text="2xl">-->
    <!--          <el-image :src="url" />-->
    <!--        </h3>-->
    <!--      </el-carousel-item>-->
    <!--    </el-carousel>-->
  </div>
  <div>
    <el-row justify="center">
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

      <el-col :span=" 4">
        区
        <el-select v-model="region" placeholder="Select">
          <el-option v-for="region in regions" :key="region" :value="region" />
        </el-select>
      </el-col>
    </el-row>
  </div>
  <br /><br /><br />
  <div>
    <el-row v-for="row in 5" justify="space-evenly" :key="row" style="margin-bottom: 20px">
      <el-col v-for="col in 3" :key="col" :span="6">
        <el-image :src="url" />
        南科大专家公寓
        <span class="ml-12">8888条评论</span>
        <span class="ml-12">￥8888起</span>
        <br>
        <el-rate v-model="rating" disabled show-score text-color="#ff9900" score-template="{value}分" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { UserFilled } from '@element-plus/icons-vue'
import router from '../../router'
import request from '../../utils/request'

interface Province {
  id: number,
  name: string,
  abbreviation: string
}

interface City {
  id: number,
  name: string,
  provinceId: number,
  isProvincialCapital: boolean
}

interface Region {
  id: number,
  name: string,
  cityId: number
}

const token = $ref(localStorage.getItem('token'))
const username = $ref(localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string).name : '')

const rating = $ref(3.6)
let province = $ref('')
let city = $ref('')
let region = $ref('')

const url = 'https://z1.muscache.cn/im/pictures/24e8ce37-6b7a-41f7-99b1-e21319705bb0.jpg?aki_policy=large'

const urls = [
  'https://z1.muscache.cn/im/pictures/642803/e0a0ddc9_original.jpg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/miso/Hosting-45337054/original/d0e063e3-f760-4160-8f56-ec1dcc7d7392.jpeg?aki_policy=large',
  'https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large'
]

const signup = () => {
  router.push('/signup')
}

const login = () => {
  router.push('/login')
}

const logout = () => {
  localStorage.removeItem('token')
  localStorage.clear()
  router.push('/login')
}

let provinces: string[] = $ref<string[]>([])
let cities: string[] = $ref<string[]>([])
let regions = $ref<string[]>([])

request.get('/public/province/all').then(res => {
  const provinceList = res.data.data as City[]
  provinces = provinceList.map(p => p.name)
})

const changeCity = (province: string) => {
  request.get(`/public/city/all?province=${province}`).then(res => {
    const cityList = res.data.data as Province[]
    cities = cityList.map(c => c.name)
    city = ''
    region = ''
    console.log(cities)
  })
}

const changeRegion = (province: string, city: string) => {
  request.get(`/public/region/all?province=${province}&&city=${city}`).then(res => {
    const regionList = res.data.data as Province[]
    regions = regionList.map(c => c.name)
    region = ''
    console.log(cities)
  })
}
</script>

<style scoped>
.header {
  margin-top: 10px;
}
</style>
