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
        value-format="YYYY-MM-DD"
        @change="date_change"
        range-separator="To"
        start-placeholder="Start date"
        end-placeholder="End date"
        size="default"
      />
    </div>
    </div>
    <el-scrollbar max-height="400px">
  <div class="demo-collapse">
    <el-collapse v-model="activeNames" @change="handleChange" accordion>
      <div v-for="i in orders">
      <el-collapse-item :title="i.id"  name="1">
        <el-descriptions
    class="margin-top"
    :column="3"
    size="small"
    border
  >

    <el-descriptions-item label="CreateTime">{{i.createTime}}</el-descriptions-item>
    <el-descriptions-item label="Price">{{i.fee}}</el-descriptions-item>
    <el-descriptions-item label="Start Time">{{i.dateStart}}</el-descriptions-item>
    <el-descriptions-item label="End Time">
      {{i.dateEnd}}
    </el-descriptions-item>

    <el-descriptions-item label="Comment"
      >{{i.comment}}    </el-descriptions-item>
  </el-descriptions>
      </el-collapse-item>
</div>
    </el-collapse>
  </div>
</el-scrollbar>
</template>
<script lang="ts" setup>
import { cond } from 'lodash';
import { ref } from 'vue'
import request from '../../utils/request';
const days = ref([''])
const checkList = ref([])
const activeNames = ref(['1'])
const value = ref(0)
const handleChange = (val: string[]) => {
  console.log(val)
}
type order={
			"comment": "",
			"commentTime": "",
			"createTime": "",
			"dateEnd": "",
			"dateStart": "",
			"fee": 0,
			"id": 0,
			"payTime": "",
			"payerId": 0,
			"refundTime": "",
			"roomId": 0,
			"stars": 0,
			"status": ""
		}
    const orders = ref<order[]>([])
    const rid = localStorage.getItem('roomId')
const change_result =()=>{
  let url = `/merchant/hotel/get-selected-orders?hotelId=${localStorage.getItem('hotelId')}&roomId=${rid}`//${localStorage.getItem('roomId')}
  console.log(days.value.length)
  for (let i=0;i<checkList.value.length;i++){
    if(checkList.value[i]=='has comment')
    url += '&isComment=true'
    if(checkList.value[i]=='has paid')
    url += '&status=5'
  }
  if (days.value.length>1){
    url += '&startTime='
    url += days.value[0]
    url+='&endTime='
    url +=days.value[1]
  }
  console.log(url)
  request.get(url).then((res)=>{
    orders.value =res.data.data

    console.log(orders.value)
    
  })
}
const date_change = (value:any)=>{
change_result()
}

const check_change = (value:string[])=>{
console.log(value)
change_result()

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