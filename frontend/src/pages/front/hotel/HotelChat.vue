<template>
  <div id="bigg">
    <el-row>
      <el-col :span="0"> </el-col>
      <el-col :span="24">
        <div id="meaningless"></div>
        <!-- main就是整个聊天窗口 -->
        <div id="main">
          <!-- 三段式 -->
          <!-- head：显示当前聊天双方 -->
          <div id="head">
            <p id="from_and_to">{{ from }} 和 {{ to }} 的聊天</p>
            <p style="color: gray">连接状态: {{ status }}</p>
          </div>
          <!-- body：显示历史来往消息 -->
          <div id="body">
            <el-scrollbar height="62vh">
              <div v-for="item in histories" class="scrollbar-demo-item">
                <!-- 发送者 -->
                <span class="src">{{ item.src }}: </span>
                <!-- 时间 -->
                <span class="time">{{ item.time }}</span>
                <!-- 发送的信息 -->
                <p class="text">{{ item.text }}</p>
              </div>
            </el-scrollbar>
          </div>
          <!-- foot：显示当前文本域以及发送按钮 -->
          <div id="foot">
            <!-- 用一行来展示文本域 -->
            <el-row>
              <el-input class="textarea" v-model="text" :autosize="{ minRows: 4, maxRows: 4 }" type="textarea"
                placeholder="请输入信息" />
            </el-row>
            <!-- 用一行来展示发送按钮，放在最右边 -->
            <el-row justify="end">
              <el-button type="primary" @click="send"> send </el-button>
            </el-row>
          </div>
        </div>
        <div id="meaningless"></div>
      </el-col>
      <el-col :span="0"> </el-col>
    </el-row>
  </div>
</template>

<style scoped>
/* 这个是背景 */
#bigg {
  background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png');
  width: auto;
}

#main {
  border: black solid 1px;
}

#head {
  text-align: center;
  line-height: 4vh;
  border: black solid 1px;
}

#body {
  height: 62.5vh;
  border: black solid 1px;
}

#foot {
  border: black solid 1px;
}

/* 修改默认输入字号 */
.textarea>>>.el-textarea__inner {
  font-family: 'Microsoft' !important;
  font-size: 18px !important;
  font-weight: 400;
  padding: 0.5rem;
  opacity: 0.75;
}

/* 修改聊天区域的气泡 */
.scrollbar-demo-item {
  display: block;
  border-radius: 10px;

  /* border: #3b64bc solid 1px; */
  /* border-color: #3b64bc #3b64bc #3b64bc #bc3b4a; */
  margin: 0.5rem;
  padding: 0.3rem;
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
  color: black;
  border-radius: 8px;
  border: #3b64bc solid 1px;
  border-color: #3b64bc #3b64bc #3b64bc #bc3b4a;
  margin: 3px;
  padding: 3px;
  background-color: #c5d8ff;
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
}

let status = ref('')

let from = $ref('')
let to = $ref('')
let text = $ref('')

const router = useRouter()
const hotelId = router.currentRoute.value.params['hotelId']

request.get(`/public/merchant-username?hotelId=${hotelId}`).then(res => {
  to = res.data.data
})

// histories 存储所有历史信息
let histories = reactive<Message[]>([])
const username = JSON.parse(localStorage.getItem('user') as string).name
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
  if (data.src === to) {
    histories.push({ src: data.src, dst: data.dst, text: data.text, time: data.time })
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
    const message: Message = { src: from, dst: to, text: text, time: time }
    socket.send(JSON.stringify(message))
    histories.push(message)
    text = ''
  }
}

/* socket.addEventListener('open', function () {
  //建立连接的时候发过去自己的名字表明身份
  socket.send(`FROM:${from}||TO:${from}||TEXT:MYNAME`)
  //第一次探试一下对方是否在线
  socket.send(`FROM:${from}||TO:${to}||TEXT:CONNECTION`)
}) */


//建立连接之后每三秒试探一次对方是否在线
/* setInterval(function () {
  socket.send(`FROM:${from}||TO:${to}||TEXT:CONNECTION`)
}, 3000) */


/* socket.addEventListener('message', function (e) {
  console.log(e.data)
  //分为两种情况，1.从SERVER发来的系统信息，2.从实际的用户处发过来的
  let arr = e.data.split('||')
  let FROM = arr[0].replace('FROM:', '')
  let TO = arr[1].replace('TO:', '')
  let TEXT = arr[2].replace('TEXT:', '')
  if (FROM === 'SERVER') {
    status.value = TEXT
  } else {
    let date = new Date().toLocaleString()
    histories.push({
      src: FROM,
      des: TO,
      text: TEXT,
      time: date
    })
  }
}) */

/* const send = () => {
  if (status.value == 'CONNECT_SUCCEED') {
    let msg = `FROM:${from}||TO:${to}||TEXT:${text.value}`
    socket.send(msg)
    let date = new Date().toLocaleString()
    histories.push({
      src: from,
      des: to,
      text: text.value,
      time: date
    })
    console.log(msg)
    text.value = ''
  }
} */
</script>
