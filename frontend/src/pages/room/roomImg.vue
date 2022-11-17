<template>
          <el-upload ref="uploadRef" class="upload-demo" :headers="{ 'token': token }" :on-success="handleUploadSuccess"
        :before-upload="beforeUpload" name="picture" :action=upload_url :auto-upload="true">
        <template #trigger>
          <el-button type="primary">select file</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            {{ upload_tip }}
          </div>
        </template>
      </el-upload>
      <el-image style="width: 60vw; height: 60vh" :src="url" fit="cover" />
</template>
<script lang="ts" setup>
import {ref,reactive } from 'vue'
import request from '../../utils/request';

import type { UploadProps, UploadInstance } from 'element-plus'
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
var type = ''
const url = '/public/get-roomInfo-by-roomId?roomId=1'
var url_upload = 'http://quanquancho.com:8080/admin/room-type/upload-cover?roomTypeId='+ (type=="标准间"?'1':'2')
const upload_url = ref('')
request.get(url).then((response) => {
room.value = response.data.data
type = response.data.data.roomTypeName
console.log(type)
url_upload = 'http://quanquancho.com:8080/admin/room-type/upload-cover?roomTypeId='+ (type=="标准间"?'1':'2')
upload_url.value=url_upload
console.log(upload_url.value)
console.log(room.value)})
const token = ref('')
token.value = localStorage.token ? JSON.parse(localStorage.token) : ''

const upload_tip = ref('jpg/png files with a size less than 500kb')
console.log(upload_url.value)
const beforeUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type != 'image/png') {
    upload_tip.value = ('Hotel cover must be JPG or PNG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    upload_tip.value = ('Hotel cover size can not exceed 2MB!')
    return false
  }
  console.log(rawFile.type)
  return true
}
const imageUrl = ref('')
const handleUploadSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = response.data.url
  console.log(imageUrl.value)
}
</script>