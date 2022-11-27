<template>
  <div class="demo-date-picker">
    <div class="block">
      <el-checkbox-group v-model="checkList" @change="check_change" >
    <el-checkbox label="has comment" border />
    <el-checkbox label="has paid" border/>
  </el-checkbox-group><br>
      <span class="demonstration">Select Days</span>
      <el-date-picker
        v-model="days"
        type="daterange"
        range-separator="To"
        start-placeholder="Start date"
        end-placeholder="End date"
        size="default"
      />
    </div>
    </div>
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange" accordion>
      <div v-for="i in order_list">
      <el-collapse-item  :title="i" name="1">
        <el-descriptions
    class="margin-top"
    title="this is an order"
    :column="3"
    size="small"
    border
  >

    <el-descriptions-item label="Username">kooriookami</el-descriptions-item>
    <el-descriptions-item label="Price">100</el-descriptions-item>
    <el-descriptions-item label="Start Time">Suzhou</el-descriptions-item>
    <el-descriptions-item label="End Time">
      <el-tag size="small">School</el-tag>
    </el-descriptions-item>
    <el-descriptions-item label="Address"
      >No.1188, Wuzhong Avenue, Wuzhong District, Suzhou, Jiangsu Province
    </el-descriptions-item>
  </el-descriptions>
      </el-collapse-item>
</div>
    </el-collapse>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
const days = ref()
const checkList = ref([])
const activeNames = ref(['1'])
const value = ref(0)
const handleChange = (val: string[]) => {
  console.log(val)
}
const check_change = (value:string[])=>{
console.log(value)

order_list.value = value
}
const order_list = ref([''])
order_list.value=['123','4123']

</script>
<style lang="scss" scoped>.demo-date-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}
.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}
.demo-date-picker .block:last-child {
  border-right: none;
}
.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}</style>