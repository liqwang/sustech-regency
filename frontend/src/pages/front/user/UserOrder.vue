<template>
  <el-card>
    我的订单
  </el-card>
  <br>
  <el-card>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="1" @click="loadOrders(0)">全部订单</el-menu-item>
      <el-menu-item index="2" @click="loadOrders(1)">待付款</el-menu-item>
      <el-menu-item index="3" @click="loadOrders(2)">已超时</el-menu-item>
      <el-menu-item index="4" @click="loadOrders(3)">已支付</el-menu-item>
      <el-menu-item index="5" @click="loadOrders(4)">待评价</el-menu-item>
      <el-menu-item index="6" @click="loadOrders(5)">已评价</el-menu-item>
      <el-menu-item index="7" @click="loadOrders(6)">已退款</el-menu-item>
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
            <el-button style="width: 100px; margin-left: 10px;" :icon="Comment" @click="dialogFormVisible = true">
              评价
            </el-button>
          </el-col>
        </el-row>
      </el-card>
    </div>
    <div class="h-6" />
  </el-card>

  <el-dialog v-model="dialogFormVisible" title="订单评价">
    <el-form>
      <el-row>
        <el-col :span="4">
          商品评分
        </el-col>
        <el-col :span="8">
          <el-rate v-model="stars" text-color="#ff9900" show-score score-template="{value}分/5分" />
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          商品评分
        </el-col>
        <el-col :span="20">
          <el-input class="textarea" v-model="commentText" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
            placeholder="请输入对订单的评价" />
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Comment } from '@element-plus/icons-vue'

interface OrderComment {
  star: number
  text: string
}

let commentText = $ref('')
let stars = $ref(0)

let orderStatus = $ref(0)

let dialogFormVisible = $ref(false)

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
