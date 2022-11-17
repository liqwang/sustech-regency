<template>
    <el-descriptions :title=room_id :column="3" border>
      <template #extra>
        <el-button type="primary" @click="modify">Operation</el-button>
      </template>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            Room
          </div>
        </template>
        {{ room_id }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            price
          </div>
        </template>
        {{ room?.price }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            Type
          </div>
        </template>
        {{ room?.roomTypeName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            Available
          </div>
        </template>
        <el-tag size="small">{{ room?.isAvailable ? 'Yes' : 'No' }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            Discount
          </div>
        </template>
        {{ room?.discount }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <div class="cell-item">
            Capacity
          </div>
        </template>
        {{ room?.capacity }}
      </el-descriptions-item>
    </el-descriptions>
    <el-dialog v-model="show_modify" title="Modify the room">
    <!-- <div style="position: relative; width: 80%"> -->
      <el-form label-width="30%" :model="room_edit">

        <el-form-item label="Price">
          <el-input size="large" v-model="room_edit.price" placeholder="Hotel Name" />
        </el-form-item>
        <el-form-item label="Is available">
          <el-radio-group v-model="room_edit.available" class="ml-4">
      <el-radio label="true" size="large">Yes</el-radio>
      <el-radio label="false" size="large">No</el-radio>
    </el-radio-group>
        </el-form-item>
        <el-form-item label="Discount">
          <el-input size="large" v-model="room_edit.discount" placeholder="The tel number" />
        </el-form-item>
      </el-form>
      <el-button @click="update" style="position: relative; left: 50%" size="large" type="success">
        <b>update</b>
      </el-button>
    <!-- </div> -->
  </el-dialog>

</template>
<script lang="ts" setup>
import {ref,reactive, Ref} from 'vue'
import router from '../../router';
import { LocationQuery, LocationQueryValue, useRoute } from 'vue-router'
import request from '../../utils/request';

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
var room_edit = reactive({
price:0,
discount:0.0,
available:'',
});
const room = ref<Room>()
const url = '/public/get-roomInfo-by-roomId?roomId=1'
request.get(url).then((response) => {
room.value = response.data.data
console.log(room.value)})
const x1 = localStorage.getItem('roomId')
const x2 = localStorage.getItem('hotelId')
const hotel_id = ref(x2)
const room_id = ref(x1)
console.log(x2)
const show_modify = ref(false)
const modify = ()=>{
  console.log(room.value?.isAvailable)
  room_edit.available= room.value?.isAvailable==true?'true':'false'
  room_edit.discount=room.value?.discount!
  room_edit.price=room.value?.price!
show_modify.value= true
}
const update=()=>{
  const url=`/room/room/updateOne?roomId=1&price=${room_edit.price}&discount=${room_edit.discount}&isAvailable=${room_edit.available}`
request.post(url).then((response)=>{
  if(response.data.code==200){
    console.log('success')
    show_modify.value=false
  }
})
}
</script>
<style lang="scss" scoped></style>