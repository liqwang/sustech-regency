<template>
  <el-card>
    我的订单
  </el-card>
  <br>
  <el-card>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1" @click="loadOrders(0)">全部订单</el-menu-item>
      <el-menu-item index="2" @click="loadOrders(1)">待付款</el-menu-item>
      <el-menu-item index="3" @click="loadOrders(2)">待评价</el-menu-item>
    </el-menu>
    <div v-for="row in 3">
      <el-card class="m-5 h-100">
        <el-row>
          <el-col :span="6">
            2022-01-01 00:00:00
          </el-col>
          <el-col :span="6">
            订单号: 252139501544
          </el-col>
        </el-row>
        <el-row>
          <!-- <el-image
            src="https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large" /> -->
          <el-col :span="6">
            cyg
          </el-col>
          <el-col :span="6">
            支付金额: ￥1000.00
          </el-col>
          <el-col :span="6">
            <el-button style="width: 100px; margin-left: 10px;" :icon="Comment" @click="addComment">
              评价
            </el-button>
          </el-col>
        </el-row>
      </el-card>
    </div>
    <div class="h-6" />
  </el-card>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Comment } from '@element-plus/icons-vue'

let orderStatus = $ref(0)

const loadOrders = (type: number) => {

}

const addComment = () => {

}

const activeIndex = ref('1')

const handleSelect = (key: string, keyPath: string[]) => {
  console.log('key: ', key)
  console.log('keyPath: ', keyPath)
}

</script>

<style scoped>

</style>
