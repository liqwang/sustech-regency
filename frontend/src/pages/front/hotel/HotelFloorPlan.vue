<template>
  <el-dialog v-model="show_dialog">
              <el-button-group class="ml-4">
            <el-button type="primary" @click="f1">F1</el-button>
            <el-button type="primary" @click="f2">F2</el-button>
            <el-button type="primary" @click="f3">F3</el-button>
            <br />
          </el-button-group>

          <div v-show="v1" style="width: 100%; height: 100%">
            <img src="../../../images/floor1.png" usemap="#floor_1" />
            <map name="floor_1">
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
          </div>


          <div v-show="v2" style="width: 100%; height: 100%">
            <!-- <el-image v-show="v1" style="width: 100%; height: 10vh" src='../images/floor2.png' /> -->
            <img src="../../../images/floor_3_2.png" usemap="#floor_2" />
            <map name="floor_2">
              <area shape="rect" coords="35,50,100,180" @click="click_room('201')" >
              <area shape="rect" coords="100,50,165,180" @click="click_room('202')">
              <area shape="rect" coords="165,50,230,180" @click="click_room('203')">
              <area shape="rect" coords="230,50,295,180" @click="click_room('204')">
              <area shape="rect" coords="375,250,445,390" @click="click_room('205')">
              <area shape="rect" coords="445,250,515,390" @click="click_room('206')">
              <area shape="rect" coords="515,250,585,390" @click="click_room('207')">
              <area shape="rect" coords="585,250,655,390" @click="click_room('208')">
            </map>
          </div>


          <div v-show="v3" style="width: 100%; height: 100%">
            <img src="../../../images/floor_2_3.png" usemap="#floor_3" />
            <map name="floor_3">
              <area shape="rect" coords="415,100,485,225" @click="click_room('301')">
              <area shape="rect" coords="485,100,555,225" @click="click_room('302')">
              <area shape="rect" coords="555,100,625,225" @click="click_room('303')">
              <area shape="rect" coords="625,100,695,225" @click="click_room('304')">
              
              <area shape="rect" coords="60,300,130,435" @click="click_room('305')">
              <area shape="rect" coords="130,300,200,435" @click="click_room('306')">
              <area shape="rect" coords="200,300,270,435" @click="click_room('307')">
              <area shape="rect" coords="270,300,340,435" @click="click_room('308')">
            </map>
          </div>
        </el-dialog>
</template>

<script setup lang="ts">
import {ref,reactive} from 'vue'
import router from '../../../router';
import request from '../../../utils/request';
const v1 = ref(true)
const v2 = ref(false)
const v3 = ref(false)
const show_dialog = ref(true)
const f1 = () => {
  v1.value = true;
  v2.value = false;
  v3.value = false;
};
const f2 = () => {
  v1.value = false;
  v2.value = true;
  v3.value = false;
};
const f3 = () => {
  v1.value = false;
  v2.value = false;
  v3.value = true;
};
type Room = {
  capacity: number,
  coverUrl: string,
  discount: number,
  floor: number,
  hasLivingRoom: boolean,
  hotelId: number,
  id: number,
  isAvailable: boolean,
  pictureUrls: Array<string>,
  price: number,
  roomNum: number,
  roomTypeName: string,
  toiletNum: number,
  typeId: number,
  videoUrls: Array<string>,
  message: string
}
const room = ref<Room>()
const which_floor = ref('')
const click_room = (room_id: string) => {
  which_floor.value = room_id

  console.log(room_id)
  let url = '/public/get-roomInfo-by-roomId?roomId=1'
  request.get(url).then((response) => {
    room.value = response.data.data
    console.log(room.value)
  })
}
</script>

<style scoped>

</style>