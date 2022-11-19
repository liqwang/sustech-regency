<template>
  <!-- 欢迎语 -->
  <el-row justfy="center">
    <el-col :span="0" :offset="0"></el-col>
    <el-col :span="24" :offset="0">
      <el-card class="box-card" style="display: flex; flex-direction: column; justify-content: center; align-items: center">
        <div>
          <h1 class="H">{{ Timer }}</h1>
        </div>
      </el-card>
    </el-col>
    <el-col :span="0" :offset="0"></el-col>
  </el-row>
  <!-- 空行间隔 -->
  <div class="null"></div>
  <!-- 选择区域 -->
  <el-row>
    <el-col :span="0" :offset="0"></el-col>
    <el-col :span="24" :offset="0">
      <el-card class="box-card">
        <el-form :model="form" label-width="0">
          <!-- 一个item是一行的意思 -->
          <el-form-item>
            <el-col :span="2" class="text-center">
              <span class="text-gray-500">行程安排</span>
            </el-col>
            <el-col :span="5">
              <el-date-picker v-model="form.start" type="date" placeholder="入住日期" style="width: 100%" />
            </el-col>
            <el-col :span="1" class="text-center">
              <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="5">
              <el-date-picker v-model="form.end" type="date" placeholder="退房日期" style="width: 100%" />
            </el-col>
            <el-col :span="1"></el-col>
            <el-col :span="2" class="text-center">
              <span class="text-gray-500">价格区间</span>
            </el-col>
            <el-col :span="2">
              <el-input v-model="form.min" placeholder="min" clearable />
            </el-col>
            <el-col :span="1" class="text-center">
              <span class="text-gray-500">-</span>
            </el-col>
            <el-col :span="2">
              <el-input v-model="form.max" placeholder="max" clearable />
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-col :span="2" class="text-center">
              <span class="text-gray-500">房型选择</span>
            </el-col>
            <el-col :span="4"
              ><el-select v-model="form.type" class="m-2" placeholder="房型选择"> <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" /> </el-select
            ></el-col>
            <el-col :span="1" :offset="0"></el-col>
            <el-col :span="2" class="text-center">
              <span class="text-gray-500">入住人数</span>
            </el-col>
            <el-col :span="2" :offset="0">
              <el-col :span="2" :offset="0"></el-col>
              <el-input-number v-model="form.hc" :min="1" :max="4" />
            </el-col>
            <el-col :span="4" :offset="0"> </el-col>
            <el-col :span="2" style="display: flex; flex-direction: row; justify-content: center"> <el-button @click="clear" style="width: 100%">清空</el-button></el-col>
            <el-col :span="1" :offset="0"></el-col>
            <el-col :span="2" style="display: flex; flex-direction: row; justify-content: center"> <el-button type="primary" @click="onSubmit" style="width: 100%">筛选</el-button></el-col>
          </el-form-item>
        </el-form>
      </el-card>
    </el-col>
    <el-col :span="0" :offset="0"></el-col>
  </el-row>
  <!-- 空行间隔 -->
  <div class="null"></div>
  <!-- 展示区域 -->
  <el-row>
    <el-col :span="0" :offset="0"></el-col>
    <el-col :span="24" :offset="0">
      <el-card class="box-card">
        <div v-for="o in 4" :key="o" class="text item">{{ 'Room ' + o }}</div>
      </el-card>
    </el-col>
    <el-col :span="0" :offset="0"></el-col>
  </el-row>
</template>

<style scoped>
.null {
  padding-bottom: 1rem;
}
.H {
  font-size: 2.5rem;
  font-weight: 550;
}
.HH {
  font-size: 1.5rem;
  font-weight: 350;
}
.box-card {
  border-radius: 15px;
}
</style>

<script lang="ts" setup>
import { da } from 'element-plus/es/locale'
import { reactive, ref, onMounted } from 'vue'

const Timer = ref('')
onMounted(() => {
  setInterval(() => {
    let now = new Date().toLocaleString()
    let date = now.split(' ')[0]
    let time = now.split(' ')[1]
    let year = date.split('/')[0]
    let month = date.split('/')[1]
    let day = date.split('/')[2]
    Timer.value = `${year}年${month}月${day}日  北京时间${time}`
  }, 500)
})
const options = [
  {
    value: '1',
    label: '单人间'
  },
  {
    value: '2',
    label: '双人间'
  }
]

// do not use same name with ref
const form = reactive({
  //开始和结束时间
  start: '',
  end: '',
  //最低花费和最多
  min: '',
  max: '',
  //房型
  type: '',
  //同住人数
  hc: 1
})

const clear = () => {
  ;(form.start = ''), (form.end = ''), (form.min = ''), (form.max = ''), (form.hc = 1), (form.type = '')
}

const onSubmit = () => {
  console.log(form)
}

const getRooms = () => {}
</script>
