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
          {{hotel.detail.cityName}}{{hotel.detail.regionName}}
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
      <el-button @click="show_floor=true" type="primary" id="floor">Floor Graph</el-button>









      <el-dialog v-model="show_floor" style="position:static;width: 800px;height: 600px;">
        <div v-show="which_floor==''">
        <el-button-group class="ml-4">
          <el-button type="primary" @click="f1">F1</el-button>
          <el-button type="primary" @click="f2">F2</el-button>
          <el-button type="primary" @click="f3">F3</el-button>
          <br />
        </el-button-group>
        <el-button type="success" @click="show_floor=false;which_floor=''"> cancel</el-button>

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
          <img src="../images/floor3.png" usemap="#floor_3" />
          <map name="floor_3">
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
        </div>
        <!-- <div id="chart" style="width:80%;height:60%"></div> -->
      </div>
      <div v-show="which_floor!=''">
      您点击了{{which_floor}}
      <el-button type="success" @click="which_floor=''"> back</el-button>
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
import { onMounted, ref, reactive, watch, h } from 'vue';
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
onMounted(() => {

  echarts.use(
    [GridComponent, LineChart, CanvasRenderer, UniversalTransition]
  );

  type EChartsOption = echarts.ComposeOption<
    GridComponentOption | LineSeriesOption
  >

  var chartDom = document.getElementById('graph')!;
  var myChart = echarts.init(chartDom);
  var option: EChartsOption;

  option = {
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [150, 230, 224, 218, 135, 147, 260],
        type: 'line'
      }
    ]
  };

  option && myChart.setOption(option);
})

type props = {
  HotelId: string;
};
var id_par = defineProps<props>();
const show_floor = ref(false)
const which_floor = ref('')
const click_room = (room_id: string) => {
  which_floor.value = room_id
  console.log(room_id)
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
watch(
  id_par,
  (new_val, old_val) => {
    if (id_par.HotelId != 'kong') {
      request.get('merchant/hotel/get?hotelId=' + id_par.HotelId).then(function (response) {
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
// var image1 = ref('../images/floor2.png');
// var image2 = ref('https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg');
// var image3 = ref('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg');
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
</style>
