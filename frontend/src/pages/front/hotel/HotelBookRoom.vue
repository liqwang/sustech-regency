<template>
  <div>
    <!-- 欢迎语 -->
    <el-row justfy="center">
      <el-col :span="0" :offset="0"></el-col>
      <el-col :span="24" :offset="0">
        <el-card shadow="always" class="box-card" style="display: flex; flex-direction: column; justify-content: center; align-items: center">
          <div>
            <h1 class="H">{{ Timer }}</h1>
          </div>
        </el-card>
      </el-col>
      <el-col :span="0" :offset="0"></el-col>
    </el-row>
    <!-- 空行间隔 -->
    <div class="null"></div>
    <!-- 选择区域 -->
    <el-row>
      <el-col :span="0" :offset="0"></el-col>
      <el-col :span="24" :offset="0">
        <el-card class="box-card" shadow="never">
          <el-form :model="form" label-width="0">
            <!-- 一个item是一行的意思 -->
            <el-form-item>
              <el-col :span="2" class="text-center">
                <span class="text-gray-500">行程安排</span>
              </el-col>
              <el-col :span="5">
                <el-date-picker value-format="YYYY-MM-DD" v-model="form.start" type="date" placeholder="入住日期" style="width: 100%" />
              </el-col>
              <el-col :span="1" class="text-center">
                <span class="text-gray-500">-</span>
              </el-col>
              <el-col :span="5">
                <el-date-picker value-format="YYYY-MM-DD" v-model="form.end" type="date" placeholder="退房日期" style="width: 100%" />
              </el-col>
              <el-col :span="1"></el-col>
              <el-col :span="2" class="text-center">
                <span class="text-gray-500">价格区间</span>
              </el-col>
              <el-col :span="2">
                <el-input v-model="form.min" placeholder="min" clearable />
              </el-col>
              <el-col :span="1" class="text-center">
                <span class="text-gray-500">-</span>
              </el-col>
              <el-col :span="2">
                <el-input v-model="form.max" placeholder="max" clearable />
              </el-col>
            </el-form-item>
            <el-form-item>
              <el-col :span="2" class="text-center">
                <span class="text-gray-500">房型选择</span>
              </el-col>
              <el-col :span="4">
                <el-select v-model="form.type" class="m-2" placeholder="房型选择">
                  <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
              </el-col>
              <el-col :span="1" :offset="0"></el-col>
              <el-col :span="2" class="text-center"> </el-col>
              <el-col :span="2" :offset="0">
                <el-col :span="2" :offset="0"></el-col>
              </el-col>
              <el-col :span="4" :offset="0"> </el-col>
              <el-col :span="2" style="display: flex; flex-direction: row; justify-content: center">
                <el-button @click="clear" style="width: 100%">清空</el-button>
              </el-col>
              <el-col :span="1" :offset="0"></el-col>
              <el-col :span="2" style="display: flex; flex-direction: row; justify-content: center">
                <el-button type="primary" @click="onSubmit" style="width: 100%">筛选</el-button>
              </el-col>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="0" :offset="0"></el-col>
    </el-row>
    <!-- 空行间隔 -->
    <div class="null"></div>
    <!-- 展示区域 -->
    <el-card class="box-card" shadow="never" style="height: 52vh">
      <el-scrollbar max-height="400px">
        <el-row>
          <el-col :span="12" :offset="0" v-for="i in rooms">
            <el-card class="box-card" @click="update(i.id)" shadow="hover">
              <div style="display: inline-flex; flex-direction: row; align-items: center">
                <div class="roomnum">{{ i.num }}</div>
                <div class="roomtype">房间类型：{{ i.type }}</div>
                <div class="disc" v-if="i.discount < 1">折扣：{{ String(i.discount).split('.')[1] }}折</div>
                <div class="nowPrice">价格：{{ i.price }}元</div>
                <div class="nowPrice" v-if="i.discount < 1">
                  <span>限时特价：</span>
                  <span id="kill">{{ (i.price * i.discount).toFixed(3) }}</span>
                  <span>元</span>
                </div>
              </div>
            </el-card>
            <!-- {{ i }} -->
          </el-col>
        </el-row>
      </el-scrollbar>
    </el-card>

    <!-- 按钮点开详情以及下订单 -->
    <el-dialog v-model="show" top="5vh" width="80%">
      <div style="height: 75vh" id="room">
        <!-- 在这里展示room的详细信息 -->
        <el-row :gutter="20">
          <el-col :span="24" :offset="0">
            <div id="head">{{ info.roomNum }} 房间详情</div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="16" :offset="0">
            <div id="left">
              <el-carousel :interval="4000" height="62vh" v-if="pictures.length > 0">
                <el-carousel-item v-for="i in pictures" :key="i">
                  <img :src="i" alt="" />
                </el-carousel-item>
              </el-carousel>
            </div>
          </el-col>
          <el-col :span="8" :offset="0">
            <div id="right">
              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">房间号</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.roomNum }}</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">楼层</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.floor }}层</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">房型</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.typeName }}</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>

                <el-col :span="9" :offset="0">
                  <div class="black">目前状态</div>
                </el-col>
                <el-col :span="9" :offset="0" v-if="info.isAvailable">
                  <div class="black">可预定</div>
                </el-col>
                <el-col :span="9" :offset="0" v-if="!info.isAvailable">
                  <div class="black">可预定</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">是否有客厅</div>
                </el-col>
                <el-col :span="9" :offset="0" v-if="info.haslivingroom">
                  <div class="black">是</div>
                </el-col>
                <el-col :span="9" :offset="0" v-if="!info.haslivingroom">
                  <div class="black">否</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">厕所个数</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.toiletNum }}个</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">价格</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.price }}元</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row" v-if="info.discount < 1">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">限时折扣</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ info.discount }}折</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row" v-if="info.discount < 1">
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">限时特价</div>
                </el-col>
                <el-col :span="9" :offset="0">
                  <div class="black">{{ (info.price * info.discount).toFixed(3) }}元</div>
                </el-col>
                <el-col :span="3" :offset="0">
                  <div class="black"></div>
                </el-col>
              </el-row>

              <el-row :gutter="10" class="row">
                <el-col :span="3" :offset="0"></el-col>
                <el-col :span="9" :offset="0">
                  <el-button type="primary" plain @click="booknow()">立即预定</el-button>
                </el-col>
                <el-col :span="9" :offset="0">
                  <el-button type="info" plain @click="show = !show">返回酒店</el-button>
                </el-col>
                <el-col :span="3" :offset="0"></el-col>
              </el-row>

              <!-- {{ info }} -->
            </div>
          </el-col>
        </el-row>
      </div>
    </el-dialog>

    <el-dialog v-model="paypage" title="付款码" width="30%">
      <el-image style="width: 100%; border-radius: 6px" :src="getBase64(base)"></el-image>
    </el-dialog>
  </div>
</template>

<style scoped>
.row {
  padding-top: 5px;
  padding-bottom: 3px;
}

.null {
  padding-bottom: 1rem;
}

.H {
  font-size: 2.5rem;
  font-weight: 550;
}

.HH {
  font-size: 1.5rem;
  font-weight: 350;
}

.box-card {
  border-radius: 15px;
}

#kill {
  color: red;
}

.black {
  font-weight: 500;
  font-size: 16px;
  display: inline-flex;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  height: 2rem;
  width: 100%;
}

.roomnum,
.roomtype,
.roomprice,
.disc {
  font-size: small;
  margin-right: 0.5rem;
}

.roomnum {
  font-size: 1.5rem;
  margin-right: 1rem;
}

.nowPrice {
  margin-right: 0.5rem;
}

#room {
  height: 100%;
  width: 100%;
  display: block;
  /* background-color: aqua; */
}

#head {
  font-size: 2rem;
  font-weight: 750;
  font-family: BlinkMacSystemFont, -apple-system, Roboto, Helvetica, Arial, sans-serif;
  display: inline-flex;
  align-items: center;
  flex-direction: row;
  justify-content: flex-start;
  height: 10vh;
  /* background-color: red; */
}

#left {
  height: 62vh;
  /* background-color: black; */
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-fit: cover;
}

#right {
  height: 62vh;
  /* background-color: greenyellow; */
}
</style>

<script lang="ts" setup>
import { useRouter } from 'vue-router'
import { reactive, ref, onMounted, h } from 'vue'
import request from '../../../utils/request'
import { ElNotification } from 'element-plus'

const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)

const Timer = ref('')
let now = $ref('')
let date = $ref('')
let time = $ref('')
let year = $ref('')
let month = $ref('')
let day = $ref('')
let paypage = $ref(false)
let base = $ref('')

const types = ['', '标准间', '双人间', '大床房', '高级双床房', '豪华亲子房', '超享庭院大床房', '一室一厅城景套房', '精致房', '浪漫温馨情侣房', '电竞开黑四人间']

const options: any[] = []
for (let index = 1; index < types.length; index++) {
  options.push({
    value: index,
    label: types[index]
  })
}

// do not use same name with ref
const form = reactive({
  //开始和结束时间
  start: '',
  end: '',
  //最低花费和最多
  min: '',
  max: '',
  //房型
  type: '',
  //同住人数
  hc: 1
})

onMounted(() => {
  setInterval(() => {
    now = new Date().toLocaleString()
    date = now.split(' ')[0]
    time = now.split(' ')[1]
    year = date.split('/')[0]
    month = date.split('/')[1]
    day = date.split('/')[2]
    Timer.value = `${year}年${month}月${day}日  北京时间${time}`
  }, 500)
})

const clear = () => {
  form.start = ''
  form.end = ''
  form.min = ''
  form.max = ''
  form.type = ''
}

const onSubmit = () => {
  console.log('前端表单', form)
  request.get(`/consumer/hotel/consumer-select-rooms?hotelId=${hotelId}&startTime=${form.start}&endTime=${form.end}&minPrice=${form.min}&maxPrice=${form.max}&roomTypeId=${form.type}`).then((res) => {
    console.log('返回数据', res.data.data)
    rooms = []
    for (const key in res.data.data) {
      const element = res.data.data[key]
      let i: number = element.typeId
      let T: string = types[i]
      rooms.push({
        id: element.id,
        num: element.roomNum,
        type: T,
        discount: element.discount,
        price: element.price
      })
    }
    // clear()
  })
}
interface temproom {
  id: number
  num: number
  type: string
  discount: number
  price: number
}
let rooms: temproom[] = $ref()
const getRooms = () => {
  rooms = []
  request.get(`/public/get-rooms-by-hotel?hotelId=${hotelId}`).then((res) => {
    //console.log('全部房间')
    //console.log(res.data.data)
    for (const key in res.data.data) {
      const element = res.data.data[key]
      let i: number = element.typeId
      let T: string = types[i]
      rooms.push({
        id: element.id,
        num: element.roomNum,
        type: T,
        discount: element.discount,
        price: element.price
      })
    }
  })
}
getRooms()

let chooseid = $ref(0)
let show = $ref(false)
interface room {
  cover: string
  roomNum: number
  typeName: string
  isAvailable: boolean
  floor: number
  haslivingroom: boolean
  price: number
  discount: number
  toiletNum: number
  pics: string[]
  videos: string
}
//这里写的是展示房间详情页的东西
let pictures: string[] = $ref([])

let info: room = reactive({
  cover: 'null',
  roomNum: 0,
  typeName: '',
  isAvailable: false,
  floor: 10,
  haslivingroom: false,
  price: 1,
  discount: 1,
  toiletNum: 1,
  pics: [],
  videos: 'null'
})

const update = (i: number) => {
  chooseid = i
  request.get(`/public/get-roomInfo-by-roomId?roomId=${chooseid}`).then((res) => {
    console.log(res.data.data)
    pictures = []
    info.cover = res.data.data.coverUrl
    // info.cover = 'src/assets/1.jpeg'
    pictures.push(info.cover)
    info.roomNum = res.data.data.roomNum
    info.typeName = res.data.data.roomTypeName
    info.isAvailable = res.data.data.isAvailable
    info.floor = res.data.data.floor
    info.haslivingroom = res.data.data.hasLivingRoom
    info.price = res.data.data.price
    info.discount = res.data.data.discount
    info.toiletNum = res.data.data.toiletNum
    info.pics = res.data.data.pictureUrls
    info.typeName = res.data.data.roomTypeName
    // info.pics = ['src/assets/2.jpeg', 'src/assets/3.jpeg', 'src/assets/4.jpeg', 'src/assets/5.jpeg', 'src/assets/6.jpeg']
    for (const key in info.pics) {
      pictures.push(info.pics[key])
    }
    info.videos = res.data.data.videoUrls
    //
  })
  show = !show
}

const getBase64 = (base: string) => {
  return 'data:image/png;base64,' + base
}
const booknow = () => {
  if (localStorage.token == null) {
    ElNotification({
      title: 'Failed',
      message: h('i', { style: 'color: red' }, '请先登陆到您的账户')
    })
    router.push('/login')
  } else {
    if (form.start == '' || form.end == '') {
      ElNotification({
        title: 'Failed',
        message: h('i', { style: 'color: red' }, '请先选择预定日期')
      })
      show = !show
    } else {
      request
        .post(`/consumer/reserve-room`, {
          endTime: form.end,
          roomId: chooseid,
          startTime: form.start
        })
        .then((res) => {
          //  console.log(res)
          if (res.data.code == '400') {
            ElNotification({
              title: 'Failed',
              message: h('i', { style: 'color: red' }, res.data.message)
            })
            show = !show
          } else if (res.data.code == '200') {
            // console.log(res.data.data)
            base = res.data.data.base64QrCode.replace(/[\r\n]/g, '')
            // console.log(base)
            paypage = !paypage
          }
        })
    }
  }
}
</script>
