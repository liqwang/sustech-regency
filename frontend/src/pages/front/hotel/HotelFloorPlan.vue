<template>
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
              <el-col :span="3" :offset="0">
                <div class="black"></div>
              </el-col>
              <el-col :span="9" :offset="0">
                <div class="black">入住日期</div>
              </el-col>
              <el-col :span="9" :offset="0">
                <div class="black">
                  <el-date-picker v-model="form.start" type="date" placeholder="入住日期" :disabled-date="disabledDate" style="width: 100%" />
                </div>
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
                <div class="black">退房日期</div>
              </el-col>
              <el-col :span="9" :offset="0">
                <div class="black">
                  <el-date-picker v-model="form.end" type="date" :disabled-date="disabledOut" placeholder="退房日期" style="width: 100%" />
                </div>
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

  <div>
    <!-- <el-card class="box-card" style="border-radius: 15px"> -->
    <!-- <el-row :gutter="20">
      <div style="display: inline-flex; align-items: center">
        <span class="text-gray-500" style="margin-left: 4rem">选择楼层</span>
      </div>
    </el-row> -->
    <div class="null"></div>
    <el-dialog v-model="show_dialog" style="position: static; width: 800px; height: 600px">
      <el-button-group class="ml-4">
        <el-button type="primary" @click="f1">L1</el-button>
        <el-button type="primary" @click="f2">L2</el-button>
        <el-button type="primary" @click="f3">L3</el-button>
      </el-button-group>
      <map name="floor_1_1">
        <area shape="rect" coords="185,120,240,260" @click="click_room('101')" />
        <area shape="rect" coords="256,120,311,260" @click="click_room('102')" />
        <area shape="rect" coords="327,120,382,260" @click="click_room('103')" />
        <area shape="rect" coords="398,120,453,260" @click="click_room('104')" />
        <area shape="rect" coords="469,120,524,260" @click="click_room('105')" />
        <area shape="rect" coords="540,120,595,260" @click="click_room('106')" />
        <area shape="rect" coords="611,120,666,260" @click="click_room('107')" />
        <area shape="rect" coords="682,120,737,260" @click="click_room('108')" />
        <area shape="rect" coords="185,315,240,430" @click="click_room('109')" />
        <area shape="rect" coords="256,315,311,430" @click="click_room('110')" />
        <area shape="rect" coords="327,315,382,430" @click="click_room('111')" />
        <area shape="rect" coords="398,315,453,430" @click="click_room('112')" />
      </map>
      <map name="floor_1_2">
        <area shape="rect" coords="185,120,240,260" @click="click_room('201')" />
        <area shape="rect" coords="256,120,311,260" @click="click_room('202')" />
        <area shape="rect" coords="327,120,382,260" @click="click_room('203')" />
        <area shape="rect" coords="398,120,453,260" @click="click_room('204')" />
        <area shape="rect" coords="469,120,524,260" @click="click_room('205')" />
        <area shape="rect" coords="540,120,595,260" @click="click_room('206')" />
        <area shape="rect" coords="611,120,666,260" @click="click_room('207')" />
        <area shape="rect" coords="682,120,737,260" @click="click_room('208')" />
        <area shape="rect" coords="185,315,240,430" @click="click_room('209')" />
        <area shape="rect" coords="256,315,311,430" @click="click_room('210')" />
        <area shape="rect" coords="327,315,382,430" @click="click_room('211')" />
        <area shape="rect" coords="398,315,453,430" @click="click_room('212')" />
      </map>
      <map name="floor_1_3">
        <area shape="rect" coords="185,120,240,260" @click="click_room('301')" />
        <area shape="rect" coords="256,120,311,260" @click="click_room('302')" />
        <area shape="rect" coords="327,120,382,260" @click="click_room('303')" />
        <area shape="rect" coords="398,120,453,260" @click="click_room('304')" />
        <area shape="rect" coords="469,120,524,260" @click="click_room('305')" />
        <area shape="rect" coords="540,120,595,260" @click="click_room('306')" />
        <area shape="rect" coords="611,120,666,260" @click="click_room('307')" />
        <area shape="rect" coords="682,120,737,260" @click="click_room('308')" />
        <area shape="rect" coords="185,315,240,430" @click="click_room('309')" />
        <area shape="rect" coords="256,315,311,430" @click="click_room('310')" />
        <area shape="rect" coords="327,315,382,430" @click="click_room('311')" />
        <area shape="rect" coords="398,315,453,430" @click="click_room('312')" />
      </map>
      <map name="floor_3_2">
        <area shape="rect" coords="35,50,100,180" @click="click_room('201')" />
        <area shape="rect" coords="100,50,165,180" @click="click_room('202')" />
        <area shape="rect" coords="165,50,230,180" @click="click_room('203')" />
        <area shape="rect" coords="230,50,295,180" @click="click_room('204')" />
        <area shape="rect" coords="375,250,445,390" @click="click_room('205')" />
        <area shape="rect" coords="445,250,515,390" @click="click_room('206')" />
        <area shape="rect" coords="515,250,585,390" @click="click_room('207')" />
        <area shape="rect" coords="585,250,655,390" @click="click_room('208')" />
      </map>
      <map name="floor_3_3">
        <area shape="rect" coords="35,50,100,180" @click="click_room('301')" />
        <area shape="rect" coords="100,50,165,180" @click="click_room('302')" />
        <area shape="rect" coords="165,50,230,180" @click="click_room('303')" />
        <area shape="rect" coords="230,50,295,180" @click="click_room('304')" />
        <area shape="rect" coords="375,250,445,390" @click="click_room('305')" />
        <area shape="rect" coords="445,250,515,390" @click="click_room('306')" />
        <area shape="rect" coords="515,250,585,390" @click="click_room('307')" />
        <area shape="rect" coords="585,250,655,390" @click="click_room('308')" />
      </map>
      <map name="floor_3_1">
        <area shape="rect" coords="35,50,100,180" @click="click_room('101')" />
        <area shape="rect" coords="100,50,165,180" @click="click_room('102')" />
        <area shape="rect" coords="165,50,230,180" @click="click_room('103')" />
        <area shape="rect" coords="230,50,295,180" @click="click_room('104')" />
        <area shape="rect" coords="375,250,445,390" @click="click_room('105')" />
        <area shape="rect" coords="445,250,515,390" @click="click_room('106')" />
        <area shape="rect" coords="515,250,585,390" @click="click_room('107')" />
        <area shape="rect" coords="585,250,655,390" @click="click_room('108')" />
      </map>
      <map name="floor_2_3">
        <area shape="rect" coords="415,100,485,225" @click="click_room('301')" />
        <area shape="rect" coords="485,100,555,225" @click="click_room('302')" />
        <area shape="rect" coords="555,100,625,225" @click="click_room('303')" />
        <area shape="rect" coords="625,100,695,225" @click="click_room('304')" />
        <area shape="rect" coords="60,300,130,435" @click="click_room('305')" />
        <area shape="rect" coords="130,300,200,435" @click="click_room('306')" />
        <area shape="rect" coords="200,300,270,435" @click="click_room('307')" />
        <area shape="rect" coords="270,300,340,435" @click="click_room('308')" />
      </map>
      <map name="floor_2_1">
        <area shape="rect" coords="415,100,485,225" @click="click_room('101')" />
        <area shape="rect" coords="485,100,555,225" @click="click_room('102')" />
        <area shape="rect" coords="555,100,625,225" @click="click_room('103')" />
        <area shape="rect" coords="625,100,695,225" @click="click_room('104')" />
        <area shape="rect" coords="60,300,130,435" @click="click_room('105')" />
        <area shape="rect" coords="130,300,200,435" @click="click_room('106')" />
        <area shape="rect" coords="200,300,270,435" @click="click_room('107')" />
        <area shape="rect" coords="270,300,340,435" @click="click_room('108')" />
      </map>
      <map name="floor_2_2">
        <area shape="rect" coords="415,100,485,225" @click="click_room('201')" />
        <area shape="rect" coords="485,100,555,225" @click="click_room('202')" />
        <area shape="rect" coords="555,100,625,225" @click="click_room('203')" />
        <area shape="rect" coords="625,100,695,225" @click="click_room('204')" />
        <area shape="rect" coords="60,300,130,435" @click="click_room('205')" />
        <area shape="rect" coords="130,300,200,435" @click="click_room('206')" />
        <area shape="rect" coords="200,300,270,435" @click="click_room('207')" />
        <area shape="rect" coords="270,300,340,435" @click="click_room('208')" />
      </map>
      <div v-show="v1 && v1_1" style="width: 100%; height: 100%">
        <img :src="f1_url" usemap="#floor_1_1" />
      </div>
      <div v-show="v1 && v1_2" style="width: 100%; height: 100%">
        <img :src="f1_url" usemap="#floor_2_1" />
      </div>
      <div v-show="v1 && v1_3" style="width: 100%; height: 100%">
        <img :src="f1_url" usemap="#floor_3_1" />
      </div>

      <div v-show="v2 && v2_1" style="width: 100%; height: 100%">
        <img :src="f2_url" usemap="#floor_1_2" />
      </div>
      <div v-show="v2 && v2_2" style="width: 100%; height: 100%">
        <img :src="f2_url" usemap="#floor_2_2" />
      </div>
      <div v-show="v2 && v2_3" style="width: 100%; height: 100%">
        <img :src="f2_url" usemap="#floor_3_2" />
      </div>

      <div v-show="v3 && v3_1" style="width: 100%; height: 100%">
        <img :src="f3_url" usemap="#floor_1_3" />
      </div>
      <div v-show="v3 && v3_2" style="width: 100%; height: 100%">
        <img :src="f3_url" usemap="#floor_2_3" />
      </div>
      <div v-show="v3 && v3_3" style="width: 100%; height: 100%">
        <img :src="f3_url" usemap="#floor_3_3" />
      </div>
    </el-dialog>
    <!-- <el-row :gutter="20"> -->
    <!-- <div style="height: 75vh"> -->

    <!-- </div> -->
    <!-- </el-row> -->
    <!-- </el-card> -->
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

<style scoped>
.null {
  padding-bottom: 1rem;
}

.img {
  margin-left: 4rem;
}
</style>

<script setup lang="ts">
import f3_2 from '../../../images/floor_3_2.png'
import f3_1 from '../../../images/floor_3_1.png'
import f2_3 from '../../../images/floor_2_3.png'
import f2_1 from '../../../images/floor_2_1.png'
import f1_1 from '../../../images/floor1.png'
import f1_2 from '../../../images/floor2.png'
import f1_3 from '../../../images/floor3.png'
import f3_3 from '../../../images/floor_3_3.png'
import { ref, reactive, h } from 'vue'
import { ElNotification } from 'element-plus'
// import router from '../../../router'
import request from '../../../utils/request'
import { ElCard, ElRow, ElButtonGroup, ElButton } from 'element-plus'
import { useRouter } from 'vue-router'
let show = $ref(false)
let paypage = $ref(false)
let base = $ref('')
let chooseid = $ref(0)

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

const v1 = ref(true)
const v2 = ref(false)
const v3 = ref(false)
const show_dialog = ref(true)
const f1 = () => {
  v1.value = true
  v2.value = false
  v3.value = false
}
const f2 = () => {
  v1.value = false
  v2.value = true
  v3.value = false
}
const f3 = () => {
  v1.value = false
  v2.value = false
  v3.value = true
}
const f2_url = ref()
const f3_url = ref()
const f1_url = ref()
var v1_1 = ref(false)
var v2_1 = ref(false)
var v3_1 = ref(false)
var v1_2 = ref(false)
var v3_2 = ref(false)
var v2_2 = ref(false)
var v1_3 = ref(false)
var v2_3 = ref(false)
var v3_3 = ref(false)
const router = useRouter()
const hotelId = parseInt(router.currentRoute.value.params['hotelId'] as string)
if (hotelId == 1) {
  f1_url.value = f1_1
  f2_url.value = f3_2
  f3_url.value = f2_3
  v1_1.value = true
  v1_2.value = false
  v1_3.value = false
  v2_3.value = true
  v2_1.value = false
  v2_2.value = false
  v3_3.value = false
  v3_2.value = true
  v3_1.value = false
}
if (hotelId == 2) {
  f1_url.value = f1_1
  f2_url.value = f3_2
  f3_url.value = f3_3
  v1_1.value = true
  v1_2.value = false
  v1_3.value = false
  v2_3.value = true
  v2_1.value = false
  v2_2.value = false
  v3_3.value = true
  v3_2.value = false
  v3_1.value = false
}
if (hotelId == 3) {
  f1_url.value = f2_1
  f2_url.value = f1_2
  f3_url.value = f2_3
  v1_1.value = false
  v1_2.value = true
  v1_3.value = false
  v2_3.value = false
  v2_1.value = true
  v2_2.value = false
  v3_3.value = false
  v3_2.value = true
  v3_1.value = false
}
if (hotelId == 4) {
  f1_url.value = f2_1
  f2_url.value = f3_2
  f3_url.value = f1_3
  v1_1.value = false
  v1_2.value = true
  v1_3.value = false
  v2_3.value = true
  v2_1.value = false
  v2_2.value = false
  v3_3.value = false
  v3_2.value = false
  v3_1.value = true
}
if (hotelId == 5) {
  f1_url.value = f3_1
  f2_url.value = f3_2
  f3_url.value = f1_3
  v1_1.value = false
  v1_2.value = false
  v1_3.value = true
  v2_3.value = true
  v2_1.value = false
  v2_2.value = false
  v3_3.value = false
  v3_2.value = false
  v3_1.value = true
}
type Room = {
  capacity: number
  coverUrl: string
  discount: number
  floor: number
  hasLivingRoom: boolean
  hotelId: number
  id: number
  isAvailable: boolean
  pictureUrls: Array<string>
  price: number
  roomNum: number
  roomTypeName: string
  toiletNum: number
  typeId: number
  videoUrls: Array<string>
  message: string
}
const room = ref<Room>()
const which_floor = ref('')

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

const update = (id: number) => {
  request.get(`/public/get-roomInfo-by-roomId?roomId=${id}`).then((res) => {
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
          if (res.data.code == '400') {
            ElNotification({
              title: 'Failed',
              message: h('i', { style: 'color: red' }, res.data.message)
            })
            show = !show
          } else if (res.data.code == '200') {
            base = res.data.data.base64QrCode.replace(/[\r\n]/g, '')
            paypage = !paypage
            const socketUrl = res.data.data.webSocketUrl
            const socket = new WebSocket(socketUrl)
            socket.onopen = () => {
              console.log('websocket已打开')
            }
            socket.onmessage = (message) => {
              console.log(`收到数据: ${message.data}`)
              if (message.data == 'ok') {
                ElNotification({
                  title: 'Success',
                  message: h('i', { style: 'color: teal' }, '预定成功')
                })
              }
              show = !show
              paypage = !paypage
            }

            socket.onclose = () => {
              console.log('websocket已关闭')
            }

            socket.onerror = () => {
              console.log('websocket发生了错误')
            }
          }
        })
    }
  }
}

const click_room = (room_id: string) => {
  which_floor.value = room_id
  console.log(room_id) // 这里的room_id 是 酒店房号,如 301
  request.get(`/public/get-roomId-byHotelWithRoomNUm?hotelId=${hotelId}&roomId=${room_id}`).then((res) => {
    chooseid = res.data.data //房间ID
    console.log('房间id', chooseid)
    update(chooseid)
  })
}
const disabledDate = (time: Date) => {
  return time.getTime() <= Date.now()
}
const disabledOut = (time: Date) => {
  if (form.start == '') return time.getTime() <= Date.now()
  else return time.getTime() <= Date.parse(form.start)
}
</script>
