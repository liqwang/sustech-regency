<template>
  <div class="header">
    <el-row>
      <el-col :span="8" background-color="red"> SUSTECH Rengency</el-col>
      <el-col :span="4" :offset="2">国内搜索</el-col>
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
                <el-dropdown-item divided @click="toMerchantPage">管理端</el-dropdown-item>
              </span>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <div>{{ username }}</div>
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
        <el-select v-model="value" placeholder="Select">
          <el-option-group v-for="group in options" :key="group.label" :label="group.label">
            <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value" />
          </el-option-group>
        </el-select>
      </el-col>

      <el-col :span="4">
        市
        <el-select v-model="value" placeholder="Select">
          <el-option-group v-for="group in options" :key="group.label" :label="group.label">
            <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value" />
          </el-option-group>
        </el-select>
      </el-col>

      <el-col :span="4">
        区
        <el-select v-model="value" placeholder="Select">
          <el-option-group v-for="group in options" :key="group.label" :label="group.label">
            <el-option v-for="item in group.options" :key="item.value" :label="item.label" :value="item.value" />
          </el-option-group>
        </el-select>
      </el-col>
    </el-row>
  </div>
  <br><br><br>
  <div>
    <el-row v-for="row in 5" justify="space-evenly" :key="row" style="margin-bottom: 20px">
      <el-col v-for="col in 3" :key="col" :span="6">
        <el-image :src="url" />
        <div>
          独立房间, · 1张床
        </div>
        <div>
          Brixton O2顶层公寓套房
        </div>
        <el-rate v-model="rating" disabled show-score text-color="#ff9900" score-template="{value} points" />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { UserFilled } from '@element-plus/icons-vue';
import router from '../../router';
import { ref } from "vue";

const token = $ref(localStorage.getItem('token'))
const username = $ref(localStorage.getItem('username'))

const rating = $ref(3.6)
const value = $ref('')

const url = "https://z1.muscache.cn/im/pictures/24e8ce37-6b7a-41f7-99b1-e21319705bb0.jpg?aki_policy=large"

const urls = ['https://z1.muscache.cn/im/pictures/642803/e0a0ddc9_original.jpg?aki_policy=large', 'https://z1.muscache.cn/im/pictures/miso/Hosting-45337054/original/d0e063e3-f760-4160-8f56-ec1dcc7d7392.jpeg?aki_policy=large', 'https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large']

const signup = () => {
  router.push('/signup');
}

const login = () => {
  router.push('/login');
}

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('username');
  router.push('/login');
};

const toMerchantPage = () => {
  router.push('/merchant');
}

const options = [
  {
    label: 'Popular cities',
    options: [
      {
        value: 'Shanghai',
        label: 'Shanghai',
      },
      {
        value: 'Beijing',
        label: 'Beijing',
      },
    ],
  },
  {
    label: 'City name',
    options: [
      {
        value: 'Chengdu',
        label: 'Chengdu',
      },
      {
        value: 'Shenzhen',
        label: 'Shenzhen',
      },
      {
        value: 'Guangzhou',
        label: 'Guangzhou',
      },
      {
        value: 'Dalian',
        label: 'Dalian',
      },
    ],
  },
]
</script>

<style scoped>
.header {
  margin-top: 10px;
}
</style>
