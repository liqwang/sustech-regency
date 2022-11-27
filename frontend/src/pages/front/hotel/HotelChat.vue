<template>
  <div id="bigg">
    <el-row>
      <div v-if="username != to" style="width: 100%">
        <el-row>
          <el-col :span="24">
            <el-card class="box-card" style="border-radius: 15px" shadow="never">
              <!-- 三段式 -->
              <!-- head：显示当前聊天双方 -->
              <el-card shadow="always" class="box-card" id="head">
                <p id="from_and_to">{{ from }} 和 {{ to }} 的聊天</p>
              </el-card>
              <!-- body：显示历史来往消息 -->
              <div id="body">
                <el-scrollbar>
                  <el-card shadow="hover" class="" v-for="item in histories" style="border-radius: 1rem; margin: 1rem">
                    <div class="scrollbar-demo-item">
                      <!-- 发送者 -->
                      <span class="src">{{ item.src }}: </span>
                      <!-- 时间 -->
                      <span class="time">{{ item.time }}</span>
                      <!-- 发送的信息 -->
                      <p class="text">{{ item.text }}</p>
                    </div>
                  </el-card>
                </el-scrollbar>
              </div>
              <!-- foot：显示当前文本域以及发送按钮 -->
              <el-card shadow="hover" class="box-card" id="foot">
                <!-- 用一行来展示文本域 -->
                <el-row>
                  <el-input class="textarea" v-model="text" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea" placeholder="请输入聊天信息" />
                </el-row>
                <!-- 用一行来展示发送按钮，放在最右边 -->
                <el-row justify="end">
                  <el-button type="primary" @click="send"> send </el-button>
                </el-row>
              </el-card>
            </el-card>
          </el-col>
        </el-row>
      </div>
      <div v-if="username == to" style="width: 100%">
        <el-row>
          <el-col :span="4">
            <el-card class="box-card" style="border-radius: 15px" shadow="never">
              <el-card shadow="always" class="box-card" id="head">
                <p id="from_and_to">消息列表</p>
              </el-card>
              <el-scrollbar id="list">
                <!-- TODO:消息列表 -->
                <el-card shadow="hover" class="" v-for="i in users" style="border-radius: 1rem; margin: 1rem">{{ i }}</el-card>
              </el-scrollbar>
            </el-card>
          </el-col>
          <el-col :span="20">
            <el-card class="box-card" style="border-radius: 15px" shadow="never">
              <!-- 三段式 -->
              <!-- head：显示当前聊天双方 -->
              <el-card shadow="always" class="box-card" id="head">
                <p id="from_and_to">{{ from }} 和 {{ to }} 的聊天</p>
              </el-card>
              <!-- body：显示历史来往消息 -->
              <div id="body">
                <el-scrollbar>
                  <el-card shadow="hover" class="" v-for="item in histories" style="border-radius: 1rem; margin: 1rem">
                    <div class="scrollbar-demo-item">
                      <!-- 发送者 -->
                      <span class="src">{{ item.src }}: </span>
                      <!-- 时间 -->
                      <span class="time">{{ item.time }}</span>
                      <!-- 发送的信息 -->
                      <p class="text">{{ item.text }}</p>
                    </div>
                  </el-card>
                </el-scrollbar>
              </div>
              <!-- foot：显示当前文本域以及发送按钮 -->
              <el-card shadow="hover" class="box-card" id="foot">
                <!-- 用一行来展示文本域 -->
                <el-row>
                  <el-input class="textarea" v-model="text" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea" placeholder="请输入聊天信息" />
                </el-row>
                <!-- 用一行来展示发送按钮，放在最右边 -->
                <el-row justify="end">
                  <el-button type="primary" @click="send"> send </el-button>
                </el-row>
              </el-card>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </el-row>
  </div>
</template>

<style scoped>
#list {
  height: 73vh;
}

#left {
  display: inline-block;
  height: 50vh;
  width: 30%;
  background-color: black;
}
#right {
  display: inline-block;
  height: 50vh;
  width: 70%;
  background-color: red;
}

/* 这个是背景 */
#bigg {
  background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png');
  width: auto;
}

#main {
  /* border: black solid 1px; */
}

#head {
  text-align: center;
  line-height: 4vh;
  background-color: rgb(236, 246, 255, 0.4);
  /* border: black solid 1px; */
}

#body {
  height: 45vh;
  /* border: black solid 1px; */
}

#foot {
  /* border: black solid 1px; */
  background-color: rgb(241, 248, 255, 0.3);
}
.box-card {
  border-radius: 15px;
}

/* 修改默认输入字号 */
.textarea >>> .el-textarea__inner {
  font-family: 'Microsoft' !important;
  font-size: 18px !important;
  font-weight: 400;
  padding: 0.3rem;
  opacity: 0.75;
  background-color: rgb(241, 248, 255, 0.3);
}

/* 修改聊天区域的气泡 */
.scrollbar-demo-item {
  display: block;
  border-radius: 5px;
}

/* 填充聊天框上下的空白区域 */
#meaningless {
  height: 0px;
}

/* 修改历史消息的样式 */
.scrollbar-demo-item >>> .time {
  font-family: 'Microsoft' !important;
  font-size: 10px !important;
  color: gray;
}

.scrollbar-demo-item >>> .src {
  font-family: 'Microsoft' !important;
  font-size: 17px !important;
  color: green;
}

.scrollbar-demo-item >>> .text {
  font-family: 'Microsoft' !important;
  font-size: 16px !important;
}

#from_and_to {
  font-family: 'Gill Sans', 'Gill Sans MT';
  font-size: 1.5rem;
}
</style>

<script lang="ts" setup>
import { ElMessage, ElNotification } from 'element-plus'
import { ref, h, reactive, onMounted } from 'vue'
import { User, Lock } from '@element-plus/icons-vue'
import request from '../../../utils/request'
import { time } from 'echarts/core'
import { da, el, fr } from 'element-plus/es/locale'
import { useRouter } from 'vue-router'

interface Message {
  src: string
  dst: string
  text: string
  time: string
  hotelId: number
}

let status = ref('')

let from = $ref('')
let to = $ref('')
let text = $ref('')

const user = JSON.parse(localStorage.getItem('user') as string)
const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

request.get(`/public/merchant-username?hotelId=${hotelId}`).then((res) => {
  to = res.data.data
})

// histories 存储所有历史信息
let histories = reactive<Message[]>([])
let users: string[] = $ref([])
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')
users.push('yf')

// TODO: 判断当前用户是消费者还是商家
// 如果是消费者
if (user.isConsumer && !user.isMerchant) {
  request.get(`/consumer/hotel/get-chat-history?user1=${user.name}&user2=${to}&hotelId=${hotelId}`).then((res) => {
    histories = res.data
    console.log(histories)
  })
}

let username = $ref('')
username = JSON.parse(localStorage.getItem('user') as string).name
// const socketUrl = `ws://localhost:8080/websocket/${username}`
const socketUrl = `ws://quanquancho.com:8080/websocket/${username}`
const socket = new WebSocket(socketUrl)

from = username

socket.onopen = () => {
  console.log('websocket已打开')
}

socket.onmessage = (message) => {
  console.log(`收到数据: ${message.data}`)
  const data = JSON.parse(message.data) as Message
  if (data.src === to && hotelId === data.hotelId) {
    histories.push({ src: data.src, dst: data.dst, text: data.text, time: data.time, hotelId: hotelId })
  }
}

socket.onclose = () => {
  console.log('websocket已关闭')
}

socket.onerror = () => {
  console.log('websocket发生了错误')
}

const send = () => {
  if (!text) {
    ElMessage.error('请输入要发送的信息!')
  } else {
    const time = new Date().toLocaleString()
    const message: Message = { src: from, dst: to, text: text, time: time, hotelId: hotelId }
    socket.send(JSON.stringify(message))
    histories.push(message)
    text = ''
  }
}
</script>
