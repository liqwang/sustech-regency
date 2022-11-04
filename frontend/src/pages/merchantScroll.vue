<template>
  <el-scrollbar height="80vh">
    <div v-if="show_content">
      <el-descriptions class="margin-top" title="The Hotel" :column="3" size="large" border>
        <template #extra>
          <el-button @click="edit" type="primary">Modify hotel</el-button>
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
              Floors
            </div>
          </template>
          <el-tag size="small">how many floors</el-tag>
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
      <el-button @click="show_floor = true" type="primary" id="floor">Floor Graph</el-button>
      <br>
      <br>

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
      <!-- <el-image  :src=imageUrl fit="fill" /> -->



      <el-dialog v-model="show_floor" style="position:static;width: 800px;height: 600px;">
        <div v-show="which_floor == ''">
          <el-button-group class="ml-4">
            <el-button type="primary" @click="f1">F1</el-button>
            <el-button type="primary" @click="f2">F2</el-button>
            <el-button type="primary" @click="f3">F3</el-button>
            <br />
          </el-button-group>
          <el-button type="success" @click="show_floor = false; which_floor = ''"> cancel</el-button>

          <div v-show="v1" style="width: 100%; height: 100%">
            <!-- <el-image v-show="v1" style="width: 100%; height: 10vh" src='../images/floor2.png' /> -->
            <img src="../images/floor1.png" usemap="#floor_1" />
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
            <img src="../images/floor2.png" usemap="#floor_2" />
            <map name="floor_2">
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
          </div>


          <div v-show="v3" style="width: 100%; height: 100%">
            <!-- <el-image v-show="v1" style="width: 100%; height: 10vh" src='../images/floor2.png' /> -->
            <img src="../images/floor_2_3.png" usemap="#floor_3" />
            <map name="floor_3">
              <area shape="rect" coords="415,100,485,225" @click="click_room('301')">
              <area shape="rect" coords="485,100,555,225" @click="click_room('302')">
              <area shape="rect" coords="555,100,625,225" @click="click_room('303')">
              <area shape="rect" coords="625,100,695,225" @click="click_room('304')">


              <area shape="rect" coords="60,300,130,435" @click="click_room('305')">
              <area shape="rect" coords="130,300,200,435" @click="click_room('306')">
              <area shape="rect" coords="200,300,270,435" @click="click_room('307')">
              <area shape="rect" coords="270,300,340,435" @click="click_room('308')">

              <!-- <area shape="rect" coords="185,315,240,430" @click="click_room('309')"> -->
              <!-- <area shape="rect" coords="256,315,311,430" @click="click_room('310')"> -->
              <!-- <area shape="rect" coords="327,315,382,430" @click="click_room('311')"> -->
              <!-- <area shape="rect" coords="398,315,453,430" @click="click_room('312')"> -->
            </map>
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
</template>
<script lang="ts" setup>
import { onMounted, onUpdated, onBeforeUpdate, onBeforeMount, ref, reactive, watch, h, Ref } from 'vue';
import request from '../utils/request';
import { ElNotification } from 'element-plus';
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


import type { UploadProps, UploadInstance } from 'element-plus'
const token = ref('')
token.value = localStorage.token ? JSON.parse(localStorage.token) : ''
const upload_url = ref('')
const upload_tip = ref('jpg/png files with a size less than 500kb')

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
        data: [chart_data.value[0], chart_data.value[1], chart_data.value[2], chart_data.value[3], chart_data.value[4], chart_data.value[5], chart_data.value[6]],
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

  console.log(room_id)
  let url = '/public/get-roomInfo-by-roomId?roomId=1'
  request.get(url).then((response) => {
    room.value = response.data.data
    console.log(room.value)

  })
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

      upload_url.value = 'http://quanquancho.com:8080/merchant/hotel/upload-cover?hotelId=' + id_par.HotelId
      request.get('merchant/hotel/get?hotelId=' + id_par.HotelId).then(function (response) {
        if (response.data.code == 200) {
          hotel.detail = response.data.data;
          console.log(hotel.detail);
          let url = '/merchant/hotel/get-HistoricalBills?startTime=' + format1 + day2string1 + '&endTime='
            + format7 + (day2string7 + 1) + '&hotelId=' + id_par.HotelId
          request.get(url).then(function (response) {
            if (response.data.code == 200) {
              console.log(response.data.data)
              console.log(url)
              chart_data.value = []
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
var v1 = ref(false);
var v2 = ref(false);
var v3 = ref(false);

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
#floor {
  position: relative;
  top: 10px;
}

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
