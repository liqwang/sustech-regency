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

      <el-button-group class="ml-4">
        <br />
        <br />
        <br />
        <el-button type="primary" @click="f1">F1</el-button>
        <el-button type="primary" @click="f2">F2</el-button>
        <el-button type="primary" @click="f3">F3</el-button>
        <br />
      </el-button-group>
      <el-image v-show="v1" style="width: 100%; height: 100%" :src="image1" />
      <el-image v-show="v2" style="width: 100%; height: 100%" :src="image2" />
      <el-image v-show="v3" style="width: 100%; height: 100%" :src="image3" />
      <!-- <div id="chart" style="width:80%;height:60%"></div> -->
    </div>
    <div v-else>
      <el-empty />
    </div>
  </el-scrollbar>
  <el-dialog v-model="show_input" title="Input the message you need">
    <div style="position: relative; width: 80%">
      <el-form label-width="30%" :model="form">
        <el-form-item label="Name">
          <el-input size="large" v-model="form.name" placeholder="Hotel Name" />
        </el-form-item>
        <el-form-item label="Province">
          <el-select v-model="form.province" @change="selectProvince" filterable placeholder="Select">
            <el-option v-for="item in ps.provinces" :key="item.name" :value="item.id" :label="item.name" />
          </el-select>
        </el-form-item>
        <el-form-item label="City">
          <el-select v-model="form.city" @change="selectCity" filterable placeholder="Select">
            <div v-for="item in cs.cities">
              <el-option v-if="item.provinceId + '' == form.province" :key="item.name" :value="item.id"
                :label="item.name" />
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="Region">
          <el-select v-model="form.region" filterable placeholder="Select">
            <div v-for="item in rs.regions">
              <el-option v-if="item.cityId + '' == form.city" :key="item.name" :value="item.id" :label="item.name" />
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="Address">
          <el-input size="large" v-model="form.address" placeholder="Detailed Address" />
        </el-form-item>
        <el-form-item label="Latitude">
          <el-input size="large" v-model="form.latitude" placeholder="The latitude of hotel" />
        </el-form-item>
        <el-form-item label="Longitude">
          <el-input size="large" v-model="form.longitude" placeholder="The longitude of hotel" />
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
import { ref, reactive, watch, h } from 'vue';
import request from '../utils/request';
import { ElNotification } from 'element-plus';

type props = {
  HotelId: string;
};
var id_par = defineProps<props>();
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
const edit =()=>{
  console.log(hotel.detail)
  form.address = hotel.detail.address
  form.tel = hotel.detail.tel
  form.longitude = hotel.detail.longitude
  form.latitude = hotel.detail.latitude
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
var image1 = ref('https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg');
var image2 = ref('https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg');
var image3 = ref('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg');
var v1 = ref(true);
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
      show_input.value=false
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

</style>
