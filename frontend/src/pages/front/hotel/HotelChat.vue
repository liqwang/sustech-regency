<template>
  <div id="bigg">
    <el-row>
      <div v-if="username != hotel_owner" style="width: 100%">
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
                  <div v-for="item in histories">
                    <el-card shadow="hover" class="" style="border-radius: 1rem; margin: 1rem;">
                      <div class="scrollbar-demo-item">
                        <!-- 发送者 -->
                        <span class="src">{{ item.fromName }}: </span>
                        <!-- 时间 -->
                        <span class="time">{{ item.chatTime }}</span>
                        <!-- 发送的信息 -->
                        <p class="text">{{ item.content }}</p>
                      </div>
                    </el-card>
                  </div>
                </el-scrollbar>
              </div>
              <!-- foot：显示当前文本域以及发送按钮 -->
              <el-card shadow="hover" class="box-card" id="foot">
                <!-- 用一行来展示文本域 -->
                <el-row>
                  <el-input class="textarea" v-model="text" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
                    placeholder="请输入聊天信息" />
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
      <div v-if="username == hotel_owner" style="width: 100%">
        <el-row>
          <el-col :span="3">
            <el-card class="box-card" style="border-radius: 15px" shadow="never">
              <el-card shadow="always" class="box-card" id="head">
                <p id="from_and_to" style="font-size: 16px">消息列表</p>
              </el-card>
              <el-scrollbar id="list">
                <!-- TODO:消息列表 -->
                <el-card shadow="hover" class="" v-for="i in users" style="border-radius: 1rem" @click="choose_user(i)">
                  {{ i }}</el-card>
              </el-scrollbar>
            </el-card>
          </el-col>
          <el-col :span="21">
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
                      <span class="src">{{ item.fromName }}: </span>
                      <!-- 时间 -->
                      <span class="time">{{ item.chatTime }}</span>
                      <!-- 发送的信息 -->
                      <p class="text">{{ item.content }}</p>
                    </div>
                  </el-card>
                </el-scrollbar>
              </div>
              <!-- foot：显示当前文本域以及发送按钮 -->
              <el-card shadow="hover" class="box-card" id="foot">
                <!-- 用一行来展示文本域 -->
                <el-row>
                  <el-input class="textarea" v-model="text" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
                    placeholder="请输入聊天信息" />
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

#head {
  text-align: center;
  line-height: 3vh;
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
.textarea>>>.el-textarea__inner {
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
.scrollbar-demo-item>>>.time {
  font-family: 'Microsoft' !important;
  font-size: 10px !important;
  color: gray;
}

.scrollbar-demo-item>>>.src {
  font-family: 'Microsoft' !important;
  font-size: 17px !important;
  color: green;
}

.scrollbar-demo-item>>>.text {
  font-family: 'Microsoft' !important;
  font-size: 16px !important;
}

#from_and_to {
  font-family: 'Gill Sans', 'Gill Sans MT';
  font-size: 1.5rem;
}
</style>

<script lang="ts" setup>
import { ElMessage } from 'element-plus'
import { toRaw } from 'vue'
import request from '../../../utils/request'
import { useRouter } from 'vue-router'

interface Message {
  fromName: string
  toName: string
  content: string
  chatTime: string
  hotelId: number
}

let from = $ref('')
let to = $ref('')
let text = $ref('')

const user = JSON.parse(localStorage.getItem('user') as string)
const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

let hotel_owner = $ref('')

// histories 存储所有历史信息
let histories = $ref<Message[]>([])

const loadHistory = () => {
  request.get(`/consumer/hotel/get-chat-history?user1=${user.name}&user2=${to}&hotelId=${hotelId}`).then(res => {
    histories = res.data.data
    console.log("histories: ", histories)
  })
}

request.get(`/public/merchant-username?hotelId=${hotelId}`).then(res => {
  console.log(res.data.data)
  hotel_owner = res.data.data
  if (username == hotel_owner) {
    to = ''
  } else {
    to = hotel_owner
  }
  loadHistory()
})

let users = $ref<string[]>([])

let merchantHotelIds: number[] = user ? user.merchantHotelIds : []

if (user.isMerchant && merchantHotelIds.includes(hotelId)) {
  request.get(`/merchant/chat-users?hotelId=${hotelId}`)
    .then(res => {
      users = res.data.data
      console.log("users: ", users)
    })
}

console.log('user.name: ', user.name)
console.log('to: ', to)
loadHistory()

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
  if (data.fromName === to && hotelId === data.hotelId) {
    histories.push({ fromName: data.fromName, toName: data.toName, content: data.content, chatTime: data.chatTime, hotelId: hotelId })
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
    const message: Message = { fromName: from, toName: to, content: text, chatTime: time, hotelId: hotelId }
    socket.send(JSON.stringify(message))
    histories.push(message)
    text = ''
  }
}

const choose_user = (userName: string) => {
  to = userName
  loadHistory()
}
</script>
