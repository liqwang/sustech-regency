<template>
  <!-- <el-card class="box-card" style="border-radius: 15px"> -->
    <el-row :gutter="20">
      <div style="display: inline-flex; align-items: center">
        <span class="text-gray-500" style="margin-left: 4rem">选择楼层</span>
       
      </div>
    </el-row>
    <div class="null"></div>
    <el-dialog v-model="show_dialog" style="position:static;width: 800px;height: 600px;">
      <el-button-group class="ml-4">
          <el-button type="primary" @click="f1">L1</el-button>
          <el-button type="primary" @click="f2">L2</el-button>
          <el-button type="primary" @click="f3">L3</el-button>
        </el-button-group>
      <map name="floor_1_1"> 
              <area shape="rect" coords="185,120,240,260" @click="click_room('101')">
              <area shape="rect" coords="256,120,311,260" @click="click_room('102')">
              <area shape="rect" coords="327,120,382,260" @click="click_room('103')">
              <area shape="rect" coords="398,120,453,260" @click="click_room('104')">
              <area shape="rect" coords="469,120,524,260" @click="click_room('105')">
              <area shape="rect" coords="540,120,595,260" @click="click_room('106')">
              <area shape="rect" coords="611,120,666,260" @click="click_room('107')">
              <area shape="rect" coords="682,120,737,260" @click="click_room('108')">
              <area shape="rect" coords="185,315,240,430" @click="click_room('109')">
              <area shape="rect" coords="256,315,311,430" @click="click_room('110')">
              <area shape="rect" coords="327,315,382,430" @click="click_room('111')">
              <area shape="rect" coords="398,315,453,430" @click="click_room('112')">
            </map>
            <map name="floor_1_2"> 
              <area shape="rect" coords="185,120,240,260" @click="click_room('201')">
              <area shape="rect" coords="256,120,311,260" @click="click_room('202')">
              <area shape="rect" coords="327,120,382,260" @click="click_room('203')">
              <area shape="rect" coords="398,120,453,260" @click="click_room('204')">
              <area shape="rect" coords="469,120,524,260" @click="click_room('205')">
              <area shape="rect" coords="540,120,595,260" @click="click_room('206')">
              <area shape="rect" coords="611,120,666,260" @click="click_room('207')">
              <area shape="rect" coords="682,120,737,260" @click="click_room('208')">
              <area shape="rect" coords="185,315,240,430" @click="click_room('209')">
              <area shape="rect" coords="256,315,311,430" @click="click_room('210')">
              <area shape="rect" coords="327,315,382,430" @click="click_room('211')">
              <area shape="rect" coords="398,315,453,430" @click="click_room('212')">
            </map>
            <map name="floor_1_3"> 
              <area shape="rect" coords="185,120,240,260" @click="click_room('301')">
              <area shape="rect" coords="256,120,311,260" @click="click_room('302')">
              <area shape="rect" coords="327,120,382,260" @click="click_room('303')">
              <area shape="rect" coords="398,120,453,260" @click="click_room('304')">
              <area shape="rect" coords="469,120,524,260" @click="click_room('305')">
              <area shape="rect" coords="540,120,595,260" @click="click_room('306')">
              <area shape="rect" coords="611,120,666,260" @click="click_room('307')">
              <area shape="rect" coords="682,120,737,260" @click="click_room('308')">
              <area shape="rect" coords="185,315,240,430" @click="click_room('309')">
              <area shape="rect" coords="256,315,311,430" @click="click_room('310')">
              <area shape="rect" coords="327,315,382,430" @click="click_room('311')">
              <area shape="rect" coords="398,315,453,430" @click="click_room('312')">
            </map>
            <map name="floor_3_2">
              <area shape="rect" coords="35,50,100,180" @click="click_room('201')">
              <area shape="rect" coords="100,50,165,180" @click="click_room('202')">
              <area shape="rect" coords="165,50,230,180" @click="click_room('203')">
              <area shape="rect" coords="230,50,295,180" @click="click_room('204')">
              <area shape="rect" coords="375,250,445,390" @click="click_room('205')">
              <area shape="rect" coords="445,250,515,390" @click="click_room('206')">
              <area shape="rect" coords="515,250,585,390" @click="click_room('207')">
              <area shape="rect" coords="585,250,655,390" @click="click_room('208')">
            </map>
            <map name="floor_3_3">
              <area shape="rect" coords="35,50,100,180" @click="click_room('301')">
              <area shape="rect" coords="100,50,165,180" @click="click_room('302')">
              <area shape="rect" coords="165,50,230,180" @click="click_room('303')">
              <area shape="rect" coords="230,50,295,180" @click="click_room('304')">
              <area shape="rect" coords="375,250,445,390" @click="click_room('305')">
              <area shape="rect" coords="445,250,515,390" @click="click_room('306')">
              <area shape="rect" coords="515,250,585,390" @click="click_room('307')">
              <area shape="rect" coords="585,250,655,390" @click="click_room('308')">
            </map>
            <map name="floor_3_1">
              <area shape="rect" coords="35,50,100,180" @click="click_room('101')">
              <area shape="rect" coords="100,50,165,180" @click="click_room('102')">
              <area shape="rect" coords="165,50,230,180" @click="click_room('103')">
              <area shape="rect" coords="230,50,295,180" @click="click_room('104')">
              <area shape="rect" coords="375,250,445,390" @click="click_room('105')">
              <area shape="rect" coords="445,250,515,390" @click="click_room('106')">
              <area shape="rect" coords="515,250,585,390" @click="click_room('107')">
              <area shape="rect" coords="585,250,655,390" @click="click_room('108')">
            </map>
            <map name="floor_2_3">
              <area shape="rect" coords="415,100,485,225" @click="click_room('301')">
              <area shape="rect" coords="485,100,555,225" @click="click_room('302')">
              <area shape="rect" coords="555,100,625,225" @click="click_room('303')">
              <area shape="rect" coords="625,100,695,225" @click="click_room('304')">
              <area shape="rect" coords="60,300,130,435" @click="click_room('305')">
              <area shape="rect" coords="130,300,200,435" @click="click_room('306')">
              <area shape="rect" coords="200,300,270,435" @click="click_room('307')">
              <area shape="rect" coords="270,300,340,435" @click="click_room('308')">
            </map>
            <map name="floor_2_1">
              <area shape="rect" coords="415,100,485,225" @click="click_room('101')">
              <area shape="rect" coords="485,100,555,225" @click="click_room('102')">
              <area shape="rect" coords="555,100,625,225" @click="click_room('103')">
              <area shape="rect" coords="625,100,695,225" @click="click_room('104')">
              <area shape="rect" coords="60,300,130,435" @click="click_room('105')">
              <area shape="rect" coords="130,300,200,435" @click="click_room('106')">
              <area shape="rect" coords="200,300,270,435" @click="click_room('107')">
              <area shape="rect" coords="270,300,340,435" @click="click_room('108')">
            </map>
            <map name="floor_2_2">
              <area shape="rect" coords="415,100,485,225" @click="click_room('201')">
              <area shape="rect" coords="485,100,555,225" @click="click_room('202')">
              <area shape="rect" coords="555,100,625,225" @click="click_room('203')">
              <area shape="rect" coords="625,100,695,225" @click="click_room('204')">
              <area shape="rect" coords="60,300,130,435" @click="click_room('205')">
              <area shape="rect" coords="130,300,200,435" @click="click_room('206')">
              <area shape="rect" coords="200,300,270,435" @click="click_room('207')">
              <area shape="rect" coords="270,300,340,435" @click="click_room('208')">
            </map>
          <div v-show="v1&&v1_1" style="width: 100%; height: 100%">
            <img :src="f1_url" usemap="#floor_1_1" />
          </div>
          <div v-show="v1&&v1_2" style="width: 100%; height: 100%">
            <img :src="f1_url" usemap="#floor_2_1" />
          </div>
          <div v-show="v1&&v1_3" style="width: 100%; height: 100%">
            <img :src="f1_url" usemap="#floor_3_1" />
          </div>

          <div v-show="v2&&v2_1" style="width: 100%; height: 100%">
            <img :src="f2_url" usemap="#floor_1_2" />          
          </div>
          <div v-show="v2&&v2_2" style="width: 100%; height: 100%">
            <img :src="f2_url" usemap="#floor_2_2" />          
          </div>
          <div v-show="v2&&v2_3" style="width: 100%; height: 100%">
            <img :src="f2_url" usemap="#floor_3_2" />          
          </div>


          <div v-show="v3&&v3_1" style="width: 100%; height: 100%">
            <img :src="f3_url" usemap="#floor_1_3" />
          </div>
          <div v-show="v3&&v3_2" style="width: 100%; height: 100%">
            <img :src="f3_url" usemap="#floor_2_3" />
          </div>
          <div v-show="v3&&v3_3" style="width: 100%; height: 100%">
            <img :src="f3_url" usemap="#floor_3_3" />
          </div>
    </el-dialog>
    <!-- <el-row :gutter="20"> -->
      <!-- <div style="height: 75vh"> -->
        
      <!-- </div> -->
    <!-- </el-row> -->
  <!-- </el-card> -->
</template>

<script setup lang="ts">
import f3_2 from '../../../images/floor_3_2.png'
import f3_1 from '../../../images/floor_3_1.png'
import f2_3 from '../../../images/floor_2_3.png'
import f2_1 from '../../../images/floor_2_1.png'
import f1_1 from '../../../images/floor1.png'
import f1_2 from '../../../images/floor2.png'
import f1_3 from '../../../images/floor3.png'
import f3_3 from '../../../images/floor_3_3.png'
import { ref, reactive } from 'vue'
// import router from '../../../router'
import request from '../../../utils/request'
import { ElCard, ElRow, ElButtonGroup, ElButton } from 'element-plus'
import { useRouter } from 'vue-router'
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
const f2_url =ref()
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
if (hotelId == 1){
        f1_url.value = f1_1
        f2_url.value = f3_2
        f3_url.value = f2_3
        v1_1.value=true
        v1_2.value =false
        v1_3.value =false
        v2_3.value =true
        v2_1.value =false
        v2_2.value =false
        v3_3.value =false
        v3_2.value =true
        v3_1.value =false
      }
      if (hotelId == 2){
        f1_url.value = f1_1
        f2_url.value = f3_2
        f3_url.value = f3_3
        v1_1.value=true
        v1_2.value =false
        v1_3.value =false
        v2_3.value =true
        v2_1.value =false
        v2_2.value =false
        v3_3.value =true
        v3_2.value =false
        v3_1.value =false
      }
      if (hotelId == 3){
        f1_url.value = f2_1
        f2_url.value = f1_2
        f3_url.value = f2_3
        v1_1.value=false
        v1_2.value =true
        v1_3.value =false
        v2_3.value =false
        v2_1.value =true
        v2_2.value =false
        v3_3.value =false
        v3_2.value =true
        v3_1.value =false
      }
      if (hotelId == 4){
        f1_url.value = f2_1
        f2_url.value = f3_2
        f3_url.value = f1_3
        v1_1.value= false
        v1_2.value =true
        v1_3.value =false
        v2_3.value =true
        v2_1.value =false
        v2_2.value =false
        v3_3.value =false
        v3_2.value =false
        v3_1.value =true
      }
      if (hotelId == 5){
        f1_url.value = f3_1
        f2_url.value = f3_2
        f3_url.value = f1_3
        v1_1.value= false
        v1_2.value =false
        v1_3.value =true
        v2_3.value =true
        v2_1.value =false
        v2_2.value =false
        v3_3.value =false
        v3_2.value = false
        v3_1.value =true
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
const click_room = (room_id: string) => {
  which_floor.value = room_id
  console.log(room_id)// 这里的room_id 是 酒店房号,如 301
  var roomId
  request.get(`/public/get-roomId-byHotelWithRoomNUm?hotelId=${hotelId}&roomId=${room_id}`).then((res)=>{
    roomId = res.data.data//房间ID
    console.log(roomId)
  })
}
</script>

<style scoped>
.null {
  padding-bottom: 1rem;
}
.img {
  margin-left: 4rem;
}
</style>
