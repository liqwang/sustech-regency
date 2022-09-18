<template>
<merchant_drawer @cancel="getCancel" :value1="dialog"></merchant_drawer>
  <div id="top">

    <el-row id="r1">
      <el-col :span="1">
      </el-col>
      <el-col :span="19"></el-col>
      <el-col :span="2">
        <el-avatar :size="66" class="mr-3" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
      </el-col>

      <el-col :span="2">
      </el-col>
    </el-row>
    <el-row id="r2">
      <el-col :span="1">
        <el-button @click="goback" id="back" type="primary" :icon="ArrowLeft">Previous Page</el-button>
      </el-col>
      <el-col :span = '19'></el-col>
      <el-col :span="2">
      <div style="position: relative;left:1vw"><b>{{username}}</b> </div>

      </el-col>
<el-col :span="1">
  <div id="drop">
        <el-dropdown @command="option">
          <el-button type="primary">
            Options<el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="addNew" divided>Add a hotel</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
</el-col>
    </el-row>
  </div>


  <el-row id="r3">
    <el-col :span="4">
      <el-tag size="large" type="success" style="margin-left:1vw">My Hotels</el-tag>
      <div id="menu">
        <el-menu default-active="2" class="el-menu-vertical-demo" 
          @select="selectMenu">
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <location />
              </el-icon>
              <span>北京</span>
            </template>
            <el-menu-item-group title="Group One">
              <el-menu-item index="1-1">item one</el-menu-item>
              <el-menu-item index="1-2">item two</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group Two">
              <el-menu-item index="1-3">item three</el-menu-item>
            </el-menu-item-group>
            <el-sub-menu index="1-4">
              <template #title>item four</template>
              <el-menu-item index="1-4-1">item one</el-menu-item>
            </el-sub-menu>
          </el-sub-menu>
          <el-menu-item index="2">
            <el-icon>
              <icon-menu />
            </el-icon>
            <span>上海</span>
          </el-menu-item>
          <div id="a3">
            <el-menu-item index="3">
              <el-icon>
                <document />
              </el-icon>
              <span>广州</span>
            </el-menu-item>
          </div>
          <el-menu-item index="4">
            <el-icon>
              <setting />
            </el-icon>
            <span>深圳</span>
          </el-menu-item>
        </el-menu>
      </div>
    </el-col>
    <el-col :span="16">
      <el-scrollbar height="80vh" >
        <el-descriptions class="margin-top" title="The Hotel" :column="3" size="large" border>
          <template #extra>
            <el-button @click="show_input=true" type="primary">Change name</el-button>
          </template>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                <el-icon>
                  <user />
                </el-icon>
                Owner
              </div>
            </template>
            kooriookami
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
            18100000000
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
            Suzhou
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
            <el-tag size="small">4</el-tag>
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
            No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu Province
          </el-descriptions-item>
        </el-descriptions>
        <el-button-group class="ml-4">
          <br>
          <br>
          <br>
          <el-button type="primary" @click="f1">F1</el-button>
          <el-button type="primary" @click="f2">F2</el-button>
          <el-button type="primary" @click="f3">F3</el-button>
          <br>
        </el-button-group>
        <el-image v-show="v1" style="width: 100%; height: 100%" :src="image1" />
        <el-image v-show="v2" style="width: 100%; height: 100%" :src="image2" />
        <el-image v-show="v3" style="width: 100%; height: 100%" :src="image3" />
        <!-- <div id="chart" style="width:80%;height:60%"></div> -->
      </el-scrollbar>
    </el-col>
    <el-col :span="4"></el-col>
  </el-row>
  <el-dialog v-model="show_input" title="This is a Dialog">
    <!-- <div id="chart" style="width:80%;height:60%"></div> -->

  </el-dialog>
</template>
  
<script lang="ts" setup>
import request from '../utils/request'
import { ArrowDown } from '@element-plus/icons-vue'
import { ref, reactive, h, onMounted } from 'vue'
import {
  Document,
  Menu as IconMenu,
  Setting, ArrowLeft,
} from '@element-plus/icons-vue'
import { ElNotification, DialogBeforeCloseFn, ElDrawer, ElMessageBox } from 'element-plus'
import {
  Iphone,
  Location,
  OfficeBuilding,
  Tickets,
  User,
} from '@element-plus/icons-vue'
import router from '../router';
import * as echarts from 'echarts';
import Merchant_drawer from './merchant_drawer.vue'


// onMounted(()=>{


// var chartDom = document.getElementById('chart')!;
// var myChart = echarts.init(chartDom);
//   type EChartsOption = echarts.EChartsOption


//   var option: EChartsOption;

//   option = {
//     xAxis: {
//       type: 'category',
//       data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
//     },
//     yAxis: {
//       type: 'value'
//     },
//     series: [
//       {
//         data: [820, 932, 901, 934, 1290, 1330, 1320],
//         type: 'line',
//         smooth: true
//       }
//     ]
//   };

//   option && myChart.setOption(option);
// })
const getCancel=(cancel:boolean)=>{
  dialog.value=cancel
}
const selectMenu = (index: string, path: string[],
  item: string) => {
  console.log(index, path, item)
}
const option = (command: string | number | object) => {

  if (command == 'addNew') {
    dialog.value = true
  }
}

const goback = () => {
  router.push('/')
}
window.onbeforeunload = function () {
  localStorage.removeItem("token");
  localStorage.removeItem("username");

};

const dialog = ref(false)
const username = ref(localStorage.getItem('username')==null?'未登录':localStorage.getItem('username'))
// const formLabelWidth = '30%'
var image1 = ref('https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg')
var image2 = ref('https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg')
var image3 = ref('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg')
var v1 = ref(true)
var v2 = ref(false)
var v3 = ref(false)
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

const show_input = ref(false)
</script>
<style scoped lang="scss">
#top {
  background-color: rgba(128, 212, 238, 0.101);
}

#menu {
  width: 20vh;
}

@media screen and (max-width:950px) {
  #r1 {
    display: none;

  }

  #r2 {}

  #drop {
    top: 0;
  }

}

@media screen and (min-width:950px) {
  #r2 {
    // display: none;
  }

  #r1 {
    // height: 10vh;
  }

  #drop {
    position: relative;
    left: 0%;
    top: 0%;
  }

  #back {
    position: relative;
    top: 0%;
  }
}

.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}

.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>