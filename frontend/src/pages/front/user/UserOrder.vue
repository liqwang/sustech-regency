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
    <div v-for="orderInfo in orderInfos">
      <el-card class="m-5 h-100">
        <el-row justify="space-evenly">
          <el-col :span="4">
            {{ orderInfo.order.createTime }}
          </el-col>
          <el-divider direction="vertical" />
          <el-col :span="5">
            订单号: {{ orderInfo.order.id }}
          </el-col>
          <el-divider direction="vertical" />
          <el-col :span="3">
            付款人: {{ username }}
          </el-col>
          <el-divider direction="vertical" />
          <el-col :span="3">
            金额: ￥{{ orderInfo.order.fee }}
          </el-col>
          <el-divider direction="vertical" />
          <el-col :span="3">
            订单状态: {{ map1.get(orderInfo.order.status) }}
          </el-col>
          <div v-if="(orderInfo.order.status === 'NOT_COMMENTED' || orderInfo.order.status === 'COMMENTED')">
            <el-col :span="2">
              <el-button style="width: 100px; margin-left: 10px;" :icon="Comment"
                @click="(dialogFormVisible = true, orderId = orderInfo.order.id, comment = orderInfo.order.comment, star = orderInfo.order.stars)">
                评价
              </el-button>
            </el-col>
          </div>
          <div v-if="(orderInfo.order.status === 'NOT_PAYED')">
            <el-col :span="2">
              <el-button style="width: 100px; margin-left: 10px;" :icon="Money">
                付款
              </el-button>
            </el-col>
            <!-- <div>剩余时间:
              <el-icon>
                <Timer />
              </el-icon>
            </div> -->
          </div>
          <div v-if="(orderInfo.order.status === 'PAYED')">
            <el-col :span="2">
              <el-button style="width: 100px; margin-left: 10px;" :icon="Money"
                @click="(centerDialogVisible = true, orderId = orderInfo.order.id)">
                退款
              </el-button>
            </el-col>
          </div>
        </el-row>
        <el-row>
          <el-col>
            <router-link :to="'/hotel/' + orderInfo.hotelInfo.id + '/introduction'" target="_blank">{{
                orderInfo.hotelInfo.name
            }}
            </router-link>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="2">
            {{ orderInfo.roomInfo.floor }}层
          </el-col>
          <el-col :span="2">
            {{ orderInfo.roomInfo.roomNum }}室
          </el-col>
          <el-col :span="8">
            房型: {{ orderInfo.roomInfo.roomTypeName }}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4">
            <router-link :to="'/hotel/' + orderInfo.hotelInfo.id + '/introduction'" target="_blank">
              <el-image :src="orderInfo.roomInfo.coverUrl" style="width: 100%; height: 20vh" fit="contain"></el-image>
            </router-link>
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
          <el-rate v-model="star" :colors="colors" text-color="#ff9900" show-score score-template="{value}分/5分" />
        </el-col>
      </el-row>
      <br>
      <el-row>
        <el-col :span="4">
          评论
        </el-col>
        <el-col :span="20">
          <el-input class="textarea" v-model="comment" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
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
        <el-upload v-model:file-list="fileList" list-type="picture-card" ref="uploadRef" class="upload-demo"
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

  <el-dialog v-model="centerDialogVisible" title="Information" width="30%" align-center>
    <span>确定要退款吗？</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="refund">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { h, ref } from 'vue'
import { Comment, Money } from '@element-plus/icons-vue'
import { ElMessage, ElNotification, UploadProps, UploadUserFile } from 'element-plus'
import type { UploadInstance } from 'element-plus'
import request from '../../../utils/request'
import { OrderInfo } from "../../../type/type.d"
import { Timer } from '@element-plus/icons-vue'

const colors = $ref(['#99A9BF', '#F7BA2A', '#FF9900'])

const token = localStorage.token ? JSON.parse(localStorage.token) : ''

const user = JSON.parse(localStorage.getItem('user') as string)
const username = $ref(user?.name)

let orderInfos = $ref<OrderInfo[]>([])

// request.get('/consumer/get-orders').then(res => {
//   orderInfos = res.data.data
//   console.log('orderInfos: ', orderInfos)
// })

let orderId = $ref('')

let comment = $ref('')
let star = $ref(0)

let dialogFormVisible = $ref(false)

let status = $ref(0)

const loadOrders = (orderStatus: number) => {
  status = orderStatus
  let orderUrl = ''
  orderStatus--
  if (orderStatus >= 0) {
    orderUrl = `/consumer/hotel/get-selected-orders?status=${orderStatus}`
  } else {
    orderUrl = '/consumer/hotel/get-selected-orders'
  }
  request.get(orderUrl).then(res => {
    orderInfos = res.data.data
    console.log('orderInfos: ', orderInfos)
  })
}

loadOrders(0)

const activeIndex = ref('0')

const handleSelect = (key: string, keyPath: string[]) => {
  console.log('key: ', key)
  console.log('keyPath: ', keyPath)
}

const fileList = ref<UploadUserFile[]>()

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png' && rawFile.type !== 'video/mp4') {
    ElMessage.error('必须上传jpg格式的图片或mp4格式的视频!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 100) {
    ElMessage.error('上传的图片或视频不能超过100MB!')
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

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log('uploadFile: ', uploadFile)
  console.log('uploadFiles: ', uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!
  dialogFormVisible = true
}

const uploadRef = ref<UploadInstance>()

const submitUpload = () => {
  uploadRef.value!.submit()
  uploadRateAndComment()
  dialogFormVisible = false
  ElNotification({
    title: "Success",
    message: h("i", { style: "color: teal" }, "评论成功"),
  })
  loadOrders(status)
}

const map1 = new Map<string, string>()
map1.set('NOT_PAYED', '待付款')
map1.set('TIMEOUT', '已超时')
map1.set('PAYED', '已支付')
map1.set('NOT_COMMENTED', '待评价')
map1.set('COMMENTED', '已评价')
map1.set('REFUNDED', '已退款')

const uploadRateAndComment = () => {
  request.post(`/consumer/upload-comment-star?orderId=${orderId}&star=${star}`).then(res => {

  })
  request.post(`/consumer/upload-comment?orderId=${orderId}&comment=${comment}`).then(res => {

  })
}

const deletePictureAndVideo = () => {

}

const centerDialogVisible = ref(false)

const refund = () => {
  request.post(`/consumer/refund?orderId=${orderId}`).then(res => {
    centerDialogVisible.value = false
    if (res.data.code === 200) {
      ElNotification({
        title: 'Success',
        message: h('i', { style: 'color: teal' }, '退款成功！')
      })
    } else {
      ElNotification({
        title: 'Fail',
        message: h('i', { style: 'color: red' }, '退款失败！')
      })
    }
  })
}

</script>

<style scoped>

</style>
