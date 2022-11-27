<template>
          <el-upload ref="uploadRef" class="upload-demo" :headers="{ 'token': token }" :on-success="handleUploadSuccess"
        :before-upload="beforeUpload" name="picture" :action=upload_url :auto-upload="true">
        <template #trigger>
          <el-button type="primary">upload cover</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            {{ upload_tip }}
          </div>
        </template>
      </el-upload>

      
      <el-image style="width: 30vw; height: 30vh" :src="imageUrl" fit="contain" />

      <el-upload ref="uploadRef" class="upload-demo" :headers="{ 'token': token }" :on-success="handleUploadSuccess_media"
        :before-upload="beforeUpload_media" name="media" :action=upload_url_media :auto-upload="true">
        <template #trigger>
          <el-button type="primary">upload media</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            {{ upload_tip_media }}
          </div>
        </template>
      </el-upload>
      <div class="demo-image__lazy">
    <el-image v-for="url in media_urls" :key="url" :src="url" style="width: 30vw; height: 30vh" lazy />
  </div>
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
var type_id = 0

const imageUrl = ref('')
const mediaUrl = ref('')
const rid = localStorage.getItem('roomId')
const media_urls = ref([])
const url = '/public/get-roomInfo-by-roomId?roomId='+rid

var url_upload = 'http://quanquancho.com:8080/admin/room-type/upload-cover?roomTypeId='+ (type=="标准间"?'1':'2')
var url_upload_media = 'http://quanquancho.com:8080/admin/room-type/upload-media?roomTypeId='+ (type=="标准间"?'1':'2')
const upload_url = ref('')
const upload_url_media = ref('')
request.get(url).then((response) => {
room.value = response.data.data
type = response.data.data.roomTypeName
if(type=='标准间'){
  type_id = 1}
else if(type=='双人间'){type_id = 2}
else if(type=='大床房'){type_id = 3}
else if(type=='高级双床房'){type_id = 4}
else if(type=='豪华亲子房'){type_id = 5}
else if(type=='超享庭院大床房'){type_id = 6}
else if(type=='一室一厅城景套房'){type_id = 7}
else if(type=='精致房'){type_id = 8}
else if(type=='浪漫温馨情侣房'){type_id = 9}
else if(type=='电竞开黑四人间'){type_id = 10}

media_urls.value = response.data.data.pictureUrls

imageUrl.value = response.data.data.coverUrl
url_upload = 'http://quanquancho.com:8080/admin/room-type/upload-cover?roomTypeId='+ type_id 
url_upload_media = 'http://quanquancho.com:8080/admin/room-type/upload-media?roomTypeId='+ type_id
upload_url.value=url_upload
upload_url_media.value = url_upload_media
console.log(upload_url.value)
console.log(room.value)})
const token = ref('')
token.value = localStorage.token ? JSON.parse(localStorage.token) : ''

const upload_tip = ref('upload cover of current roomType')
const upload_tip_media = ref('upload presentation media of current roomType')

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
const beforeUpload_media: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type != 'image/png' &&rawFile.type != 'video/mp4') {
    upload_tip_media.value = ('Room media must be JPG or PNG or MP4 format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 100) {
    upload_tip_media.value = ('Room media size can not exceed 100MB!')
    return false
  }
  console.log(rawFile.type)

  return true
}
const handleUploadSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = response.data.url
  console.log(imageUrl.value)
}

const handleUploadSuccess_media: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  mediaUrl.value = response.data.url
  console.log(mediaUrl.value)
}
</script>
<style scoped>
.demo-image__lazy {
  height: 30vh;
  overflow-y: auto;
}
.demo-image__lazy .el-image {
  display: block;
  min-height: 200px;
  margin-bottom: 10px;
}
.demo-image__lazy .el-image:last-child {
  margin-bottom: 0;
}
</style>