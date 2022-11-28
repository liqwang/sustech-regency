<template>
  <el-card>
    我的订单
  </el-card>
  <br>
  <el-card>
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index="0" @click="loadOrders(0)">全部订单</el-menu-item>
      <el-menu-item index="1" @click="loadOrders(1)">待付款</el-menu-item>
      <el-menu-item index="2" @click="loadOrders(2)">已超时</el-menu-item>
      <el-menu-item index="3" @click="loadOrders(3)">已支付</el-menu-item>
      <el-menu-item index="4" @click="loadOrders(4)">待评价</el-menu-item>
      <el-menu-item index="5" @click="loadOrders(5)">已评价</el-menu-item>
      <el-menu-item index="6" @click="loadOrders(6)">已退款</el-menu-item>
    </el-menu>
    <div v-for="order in orders">
      <el-card class="m-5 h-100">
        <el-row>
          <el-col :span="6">
            <!-- 2022-01-01 00:00:00 -->
            {{ order.createTime }}
          </el-col>
          <el-col :span="6">
            订单号: {{ order.id }}
          </el-col>
        </el-row>
        <el-row>
          <!-- <el-image
            src="https://z1.muscache.cn/im/pictures/c7e7c673-9673-4d08-be5a-ce7c2c7143dd.jpg?aki_policy=large" /> -->
          <el-col :span="6">
            {{ username }}
          </el-col>
          <el-col :span="6">
            支付金额: ￥{{ order.fee }}
          </el-col>
          <el-col :span="6">
            <el-button style="width: 100px; margin-left: 10px;" :icon="Comment"
              @click="dialogFormVisible = true, orderId = order.id">
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
        订单号: {{ orderId }}
      </el-row>
      <br>
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
        <el-upload v-model:file-list="fileList" ref="uploadRef" class="upload-demo"
          :action="'http://quanquancho.com:8080/consumer/comment/upload-media?orderId=' + orderId" :auto-upload="false"
          :headers="{ 'token': token }" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
          name="media" :on-preview="handlePictureCardPreview" :on-remove="handleRemove">
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
import { ElMessage, UploadProps, UploadUserFile } from 'element-plus'
import type { UploadInstance } from 'element-plus'
import request from '../../../utils/request';

const token = localStorage.token ? JSON.parse(localStorage.token) : ''

const user = JSON.parse(localStorage.getItem('user') as string)
const username = $ref(user?.name)

interface Order {
  id: string
  roomId: number
  dateStart: string
  dateEnd: string
  payerId: number
  fee: number
  status: string
  comment: string
  commentTime: string
  stars: number
  payTime: string
  createTime: string
  refundTime: string
}

let orders = $ref<Order[]>([])

request.get('/consumer/get-orders').then(res => {
  orders = res.data.data
  console.log(orders)
})

let orderId = $ref('')

let commentText = $ref('')
let stars = $ref(0)

let orderStatus = $ref(0)

let dialogFormVisible = $ref(false)

const loadOrders = (type: number) => {

}

const addComment = () => {

}

const activeIndex = ref('0')

const handleSelect = (key: string, keyPath: string[]) => {
  console.log('key: ', key)
  console.log('keyPath: ', keyPath)
}

const fileList = ref<UploadUserFile[]>([
  // {
  //   name: 'food.jpeg',
  //   url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
  // }
])

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png' && rawFile.type !== 'video/mp4') {
    ElMessage.error('必须上传jpg格式的图片或mp4格式的视频!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('上传的图片或视频不能超过2MB!')
    return false
  }
  return true
}

const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
  console.log(imageUrl.value)
  console.log(response.data.url)
}



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
