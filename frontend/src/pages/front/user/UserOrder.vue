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
          酒店评分
        </el-col>
        <el-col :span="8">
          <el-rate v-model="stars" text-color="#ff9900" show-score score-template="{value}分/5分" />
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          评论
        </el-col>
        <el-col :span="20">
          <el-input class="textarea" v-model="commentText" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
            placeholder="请输入对订单的评价" />
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          上传图片/视频
        </el-col>
        <!-- <el-upload v-model:file-list="fileList" action="http://quanquancho.com:8080/consumer/comment/upload-media"
          list-type="picture">
          <el-icon>
            <Plus />
          </el-icon>
        </el-upload> -->
        <el-upload ref="uploadRef" class="upload-demo" :action="postUrl" :auto-upload="false"
          :headers="{ 'token': token }">
          <template #trigger>
            <el-button type="primary">上传图片/视频</el-button>
          </template>

          <!-- <el-button class="ml-3" type="success" @click="submitUpload">
            upload to server
          </el-button> -->

          <template #tip>
            <div class="el-upload__tip">
              jpg/png/jpeg/mp4 files with a size less than 500kb
            </div>
          </template>
        </el-upload>
      </el-row>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="submitUpload">
          提交
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Comment } from '@element-plus/icons-vue'
import { Plus } from '@element-plus/icons-vue'
import type { UploadProps, UploadUserFile } from 'element-plus'
import type { UploadInstance } from 'element-plus'

const token = localStorage.token ? JSON.parse(localStorage.token) : ''

interface OrderComment {
  star: number
  text: string
}

// TODO:
let postUrl = $ref('')
// let postUrl = $ref(`http://quanquancho.com:8080/consumer/comment/upload-media?orderId=${}`)

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

const fileList = ref<UploadUserFile[]>([
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'plant-1.png',
    url: '/images/plant-1.png',
  },
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'plant-2.png',
    url: '/images/plant-2.png',
  },
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'figure-1.png',
    url: '/images/figure-1.png',
  },
  {
    name: 'food.jpeg',
    url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  },
  {
    name: 'figure-2.png',
    url: '/images/figure-2.png',
  },
])

const dialogImageUrl = ref('')
const dialogVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!
  dialogVisible.value = true
}

const uploadRef = ref<UploadInstance>()

const submitUpload = () => {
  dialogVisible.value = false
  uploadRef.value!.submit()
}

</script>

<style scoped>

</style>
