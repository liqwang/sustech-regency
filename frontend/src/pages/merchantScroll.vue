<template>

  <el-scrollbar height="80vh">
    <div v-if="show_content">
      <el-descriptions class="margin-top" title="The Hotel" :column="3" size="large" border>
        <template #extra>
          <el-button @click="edit" type="primary" :icon="Edit">Modify hotel</el-button>
          <el-button @click="show_floor = true" type="warning" :icon="Location" id="floor">Floor Graph</el-button>
          <el-button type="danger" @click="on_sale = true" :icon="Bell">On sale!!!</el-button>
          <el-button @click="to_chat" :icon="ChatLineRound"  color="#626aef">Chat</el-button>
        </template>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <user />
              </el-icon>
              Name
            </div>
          </template>
          {{ hotel.detail.name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <iphone />
              </el-icon>
              Telephone
            </div>
          </template>
          {{ hotel.detail.tel }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <location />
              </el-icon>
              Place
            </div>
          </template>
          {{ hotel.detail.cityName }}{{ hotel.detail.regionName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <tickets />
              </el-icon>
              Province
            </div>
          </template>
          <el-tag size="small">{{ hotel.detail.provinceName }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon>
                <office-building />
              </el-icon>
              Address
            </div>
          </template>
          {{ hotel.detail.address }}
        </el-descriptions-item>
      </el-descriptions>
      <br>
      <br>

      <el-upload ref="uploadRef" class="upload-demo" :headers="{ 'token': token }" :on-success="handleUploadSuccess"
        :before-upload="beforeUpload" name="picture" :action=upload_cover_url :auto-upload="true">
        <template #trigger>
          <el-button type="primary" :icon="Upload">upload cover</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            {{ cover_tip }}
          </div>
        </template>
      </el-upload>

      <el-upload ref="uploadRef" class="upload-demo" :headers="{ 'token': token }"
        :on-success="handleUploadSuccess_media" :before-upload="beforeUpload_media" name="media"
        :action=upload_media_url :auto-upload="true">
        <template #trigger>
          <el-button type="primary" plain :icon="Upload">upload media</el-button>
        </template>
        <template #tip>
          <div class="el-upload__tip">
            {{ media_tip }}
          </div>
        </template>
      </el-upload>
      <el-select @change="change_chart" v-model="room_type" class="m-2" placeholder="Select Room Type">
        <el-option label="取消筛选" value="0"></el-option>
    
        <el-option
      label="标准间"
      value="1"
    />
    <el-option label="双人间" value="2"></el-option>

    <el-option label="大床房" value="3"></el-option>

    <el-option label="高级双床房" value="4"></el-option>
    
    <el-option label="豪华亲子房" value="5"></el-option>
    <el-option label="超享庭院大床房" value="6"></el-option>

    <el-option label="一室一厅城景套房" value="7"></el-option>
    <el-option label="精致房" value="8"></el-option>
    <el-option label="浪漫温馨情侣房" value="9"></el-option>
    <el-option label="电竞开黑四人间" value="10"></el-option>

  </el-select>

      <!-- <el-button-group>
        <el-button type="primary" @click="change_chart(1)" color="#626aef" plain>标准间</el-button>
        <el-button type="primary" @click="change_chart(2)" color="#626aef" plain >
          双人间
        </el-button>
        <el-button type="primary" @click="change_chart(0)" color="#626aef" plain>无筛选</el-button>
      </el-button-group> -->

      <el-dialog v-model="show_floor" style="position:static;width: 800px;height: 600px;">
        <div v-show="which_floor == ''">
          <el-button-group class="ml-4">
            <el-button type="primary" @click="f1">F1</el-button>
            <el-button type="primary" @click="f2">F2</el-button>
            <el-button type="primary" @click="f3">F3</el-button>
            <br />
          </el-button-group>
          <el-button type="success" @click="show_floor = false; which_floor = ''"> cancel</el-button>
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

          <!-- <div id="chart" style="width:80%;height:60%"></div> -->
        </div>
        <div v-show="which_floor != ''">
          <el-button type="success" @click="which_floor = ''"> back</el-button>
          <el-descriptions :title=which_floor :column="3" border>
            <template #extra>
              <el-button type="primary">Operation</el-button>
            </template>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  Room
                </div>
              </template>
              {{ which_floor }}
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
        </div>
      </el-dialog>



    </div>

    <div v-else>
      <el-empty />
    </div>
    <div v-show="show_content" id="graph" style="position:relative;width: 60vw;height: 500%;">

    </div>
  </el-scrollbar>
  <el-dialog v-model="show_input" title="Input the message you need">
    <div style="position: relative; width: 80%">
      <el-form label-width="30%" :model="form">
        <el-form-item label="Name">
          <el-input size="large" v-model="form.name" placeholder="Hotel Name" />
        </el-form-item>
        <el-form-item label="Address">
          <el-input size="large" v-model="form.address" placeholder="Detailed Address" />
        </el-form-item>
        <el-form-item label="Tel">
          <el-input size="large" v-model="form.tel" placeholder="The tel number" />
        </el-form-item>
      </el-form>
      <el-button @click="update" style="position: relative; left: 50%" size="large" type="success">
        <b>update</b>
      </el-button>
    </div>
  </el-dialog>
  <el-dialog title="Make a type of rooms be On-Sale!" v-model="on_sale">
    <el-input-number v-model="sale_discount" :step="0.05" :min="0.05" :max="1" />
    <br><br>
    <el-radio v-model="sale_type" label=1>标准间</el-radio>
    <el-radio v-model="sale_type" label=2>双人间</el-radio>
    <br>
    <br>
    <br>
    <br>
    <el-button @click="confirm_sale" type="primary">Confirm</el-button>
    <el-button @click="cancle_sale" type="danger">Cancel</el-button>
  </el-dialog>

</template>
<script lang="ts" setup>
import { Delete, Edit, Search, Share, Upload,Location,Bell,ChatLineRound } from '@element-plus/icons-vue'
import { onMounted, onUpdated, onBeforeUpdate, onBeforeMount, ref, reactive, watch, h, Ref } from 'vue';
import request from '../utils/request';
import { ElNotification, ElMessage } from 'element-plus';
import * as echarts from 'echarts/core';
import {
  GridComponent,
  GridComponentOption
} from 'echarts/components';
import {
  LineChart,
  LineSeriesOption
} from 'echarts/charts';
import {
  UniversalTransition
} from 'echarts/features';
import {
  CanvasRenderer
} from 'echarts/renderers';
import f3_2 from '../images/floor_3_2.png'
import f3_1 from '../images/floor_3_1.png'
import f2_3 from '../images/floor_2_3.png'
import f2_1 from '../images/floor_2_1.png'
import f1_1 from '../images/floor1.png'
import f1_2 from '../images/floor2.png'
import f1_3 from '../images/floor3.png'
import f3_3 from '../images/floor_3_3.png'


import type { UploadProps, UploadInstance } from 'element-plus'
import router from '../router';
const f2_url =ref()
const f3_url = ref()
const f1_url = ref()

const room_type = ref()
const on_sale = ref(false)
const sale_type = ref(0)
const sale_discount = ref(1)
const confirm_sale = () => {
  if (sale_type.value > 0) {
    let url = `/room/room/updateRooms?discount=${sale_discount.value}&typeId=${sale_type.value}&hotelId=${id_par.HotelId}`
    request.post(url).then((res) => {
      if (res.data.code == 200) {
        ElNotification({
          title: 'Success',
          message: h('i', { style: 'color: green' }, 'these rooms are on sale now!')
        })
        on_sale.value = false
      } else {
        ElNotification({
          title: 'Error',
          message: h('i', { style: 'color: red' }, 'Some errors happened')
        })
      }
    })
  } else {
    ElMessage('Please complete the infomation')
  }
}
const to_chat=()=>{
  let userRouteUrl = router.resolve({
    path: `hotel/${id_par.HotelId}/chat`
  })
  window.open(userRouteUrl.href, '_blank')
}
const cancle_sale = () => {
  on_sale.value = false
  sale_type.value = 0
}
const token = ref('')
token.value = localStorage.token ? JSON.parse(localStorage.token) : ''
const upload_cover_url = ref('')
const upload_media_url = ref('')
const cover_tip = ref('upload the cover of your hotel')
const media_tip = ref('upload the presentation media')
const beforeUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type != 'image/png') {
    cover_tip.value = ('Hotel cover must be JPG or PNG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    cover_tip.value = ('Hotel cover size can not exceed 2MB!')
    return false
  }
  console.log(rawFile.type)
  return true
}
const beforeUpload_media: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type != 'image/png' && rawFile.type != 'video/mp4') {
    media_tip.value = ('Hotel media must be JPG or PNG or mp4 format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 100) {
    media_tip.value = ('Hotel media size can not exceed 100MB!')
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
const mediaUrl = ref('')
const handleUploadSuccess_media: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  mediaUrl.value = response.data.url
  console.log(mediaUrl.value)
}
const Time = new Date();
const date = Time.toISOString().split('T')[0]
console.log(date);
let day = date.split('-')
const day2string1 = parseInt(day[2]) - 7 <= 0 ? parseInt(day[2]) - 7 + 31 : parseInt(day[2]) - 7
const day2string2 = parseInt(day[2]) - 6 <= 0 ? parseInt(day[2]) - 6 + 31 : parseInt(day[2]) - 6
const day2string3 = parseInt(day[2]) - 5 <= 0 ? parseInt(day[2]) - 5 + 31 : parseInt(day[2]) - 5
const day2string4 = parseInt(day[2]) - 4 <= 0 ? parseInt(day[2]) - 4 + 31 : parseInt(day[2]) - 4
const day2string5 = parseInt(day[2]) - 3 <= 0 ? parseInt(day[2]) - 3 + 31 : parseInt(day[2]) - 3
const day2string6 = parseInt(day[2]) - 2 <= 0 ? parseInt(day[2]) - 2 + 31 : parseInt(day[2]) - 2
const day2string7 = parseInt(day[2]) - 1 <= 0 ? parseInt(day[2]) - 1 + 31 : parseInt(day[2]) - 1
const format1 = day2string1 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format2 = day2string2 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format3 = day2string3 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format4 = day2string4 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format5 = day2string5 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format6 = day2string6 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'
const format7 = day2string7 > day2string7 ? day[0] + '-' + (parseInt(day[1]) - 1) + '-' : day[0] + '-' + day[1] + '-'

type EChartsOption = echarts.ComposeOption<
  GridComponentOption | LineSeriesOption
>
var chartDom: HTMLElement;
var option: EChartsOption;

onUpdated(() => {
  echarts.use(
    [GridComponent, LineChart, CanvasRenderer, UniversalTransition]
  );
  chartDom = document.getElementById('graph')!;
  var myChart = echarts.init(chartDom);

  option = {
    xAxis: {
      type: 'category',
      data: [format1 + day2string1, format2 + day2string2, format3 + day2string3, format4 + day2string4, format5 + day2string5, format6 + day2string6, format7 + day2string7]
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        // data: [chart_data.value[0], chart_data.value[1], chart_data.value[2], chart_data.value[3], chart_data.value[4], chart_data.value[5], chart_data.value[6]],
        data :[0,0,0,0,0,0,0],
        type: 'line'
      }
    ]
  };

  option && myChart.setOption(option)
})

type props = {
  HotelId: string;
};


const test1 = () => {
  alert('213')
}
var id_par = defineProps<props>();


const show_floor = ref(false)
const which_floor = ref('')

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


const click_room = (room_id: string) => {
  which_floor.value = room_id
  var roomid =''
  request.get(`/public/get-roomId-byHotelWithRoomNUm?hotelId=${id_par.HotelId}&roomId=${room_id}`).then((res)=>{
    roomid = res.data.data
    console.log(roomid)
  localStorage.setItem('roomId', roomid)
    localStorage.setItem('roomId2' ,room_id)// 房间号
  })

  router.push({
    path: 'merchant/room',
  })
  localStorage.setItem('hotelId', id_par.HotelId)

  // })
}

type Province = {
  id: Number;
  name: string;
  alias: string;
};
interface provinces_reactive {
  provinces: Array<Province>;
  provinceId: number;
}
const ps: provinces_reactive = reactive({
  provinces: [],
  provinceId: -1
});

type City = {
  id: Number;
  provinceId: number;
  name: string;
  isProvincialCapital: boolean;
};
interface cities_reactive {
  cities: Array<City>;
}
const cs: cities_reactive = reactive({
  cities: []
});
type Region = {
  id: Number;
  name: string;
  cityId: number;
};
interface regions_reactive {
  regions: Array<Region>;
}
const rs: regions_reactive = reactive({
  regions: []
});
var form = reactive({
  name: '',
  city: '',
  address: '',
  province: '',
  tel: '',
  region: '',
  latitude: '',
  longitude: ''
});
const show_content = ref(true);
interface hotel_reactive {
  detail: any;
}
const hotel: hotel_reactive = reactive({
  detail: {}
});
request.get('/public/province/all').then((response) => {
  ps.provinces = response.data.data;
  // console.log(ps.provinces)
});
const edit = () => {
  console.log(hotel.detail)
  form.address = hotel.detail.address
  form.tel = hotel.detail.tel
  form.name = hotel.detail.name
  form.province = hotel.detail.provinceName
  form.city = hotel.detail.cityName
  form.region = hotel.detail.regionName
  show_input.value = true
}
const selectProvince = () => {
  form.city = '';
  form.region = '';
  request.get('/public/city/all').then((response) => {
    cs.cities = response.data.data;
    // console.log(cs.cities)
  });
};
const selectCity = () => {
  form.region = '';
  request.get('/public/region/all').then((response) => {
    rs.regions = response.data.data;
  });
};
// var chart_data:Array<number> =[0,0,0,0,0,0,0]
const chart_data: Ref<number[]> = ref([])
watch(
  id_par,
  (new_val, old_val) => {
    if (id_par.HotelId != 'kong') {
      if (id_par.HotelId == '1'){
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
      if (id_par.HotelId == '2'){
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
      if (id_par.HotelId == '3'){
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
      if (id_par.HotelId == '4'){
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
      if (id_par.HotelId == '5'){
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


      upload_media_url.value = 'http://quanquancho.com:8080/merchant/hotel/upload-media?hotelId=' + id_par.HotelId
      upload_cover_url.value = 'http://quanquancho.com:8080/merchant/hotel/upload-cover?hotelId=' + id_par.HotelId
      request.get('public/get-hotelInfo-byId?hotelId=' + id_par.HotelId).then(function (response) {
        if (response.data.code == 200) {
          hotel.detail = response.data.data;
          console.log(hotel.detail);
          
        } else alert('Error');
      });
      show_content.value = true;
    } else {
      show_content.value = false;
    }
  },
  {
    deep: true,
    immediate: true
  }
);
const change_chart=(index:number)=>{
  let url = '/merchant/hotel/get-HistoricalBills?startTime=' + format1 + day2string1 + '&endTime='
            + format7 + (day2string7 + 1) + '&hotelId=' + id_par.HotelId+'&roomTypeId='+index
  if (index == 0){
    url =  '/merchant/hotel/get-HistoricalBills?startTime=' + format1 + day2string1 + '&endTime='
            + format7 + (day2string7 + 1) + '&hotelId=' + id_par.HotelId

  }
          request.get(url).then(function (response) {
            if (response.data.code == 200) {
              chart_data.value = []
              console.log(response.data.data)
              for (let i = 0; i < response.data.data.length; i++) {
                if (response.data.data[i] == null) {
                  chart_data.value.push(0)
                } else {
                  chart_data.value.push(response.data.data[i])
                }
              }

              console.log(chart_data.value)
              var myChart = echarts.init(chartDom);
              option = {
                xAxis: {
                  type: 'category',
                  data: [format1 + day2string1, format2 + day2string2, format3 + day2string3, format4 + day2string4, format5 + day2string5, format6 + day2string6, format7 + day2string7]
                },
                yAxis: {
                  type: 'value'
                },
                series: [
                  {
                    data: [chart_data.value[0], chart_data.value[1], chart_data.value[2], chart_data.value[3], chart_data.value[4], chart_data.value[5], chart_data.value[6]],
                    type: 'line'
                  }
                ]
              };
              option && myChart.setOption(option)
            }
          })
}
var v1 = ref(false);
var v2 = ref(false);
var v3 = ref(false);
var v1_1 = ref(false)
var v2_1 = ref(false)
var v3_1 = ref(false)
var v1_2 = ref(false)
var v3_2 = ref(false)
var v2_2 = ref(false)
var v1_3 = ref(false)
var v2_3 = ref(false)
var v3_3 = ref(false)



const show_input = ref(false);
const update = () => {
  let url = `merchant/hotel/update?hotelId=${id_par.HotelId}&address=${form.address}
  &latitude=${form.latitude}&longitude=${form.longitude}&name=${form.name}&tel=${form.tel}
 `
  console.log(url);
  request.post(url).then(function (response) {
    if (response.data.code == 200) {
      ElNotification({
        title: 'Success',
        message: h('i', { style: 'color: green' }, 'update successfully')
      });
      show_input.value = false
      window.location.reload()
    } else {
      ElNotification({
        title: 'Error',
        message: h('i', { style: 'color: red' }, response.data.code)
      });
    }
  });
};
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
</script>
<style lang="scss">
// #floor {
//   position: relative;
//   top: 10px;
// }

.d {
  width: 100%;
  height: 15vh;
  // background-color: aqua;
}

.d:hover {
  transition: all .2s;
  transform: translateY(-10px);
  box-shadow: 0 26px 40px -24px rgb(0 36 100 / 50%);
  background-color: aqua;
}

.txt {
  position: absolute;
  font-size: 1vw;
  left: 1.5vw;
  top: 2.5vh;
}

#floor_image {
  background-color: antiquewhite;
}
</style>
