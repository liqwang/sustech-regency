<template>
  <div class="demo-date-picker">
    <div class="block">
      <el-checkbox-group v-model="checkList" >
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
      <el-collapse-item title="The first order" name="1">
        <div>
          Consistent with real life: in line with the process and logic of real
          life, and comply with languages and habits that the users are used to;
        </div>
        <div>
          Consistent within interface: all elements should be consistent, such
          as: design style, icons and texts, position of elements, etc.
        </div>
      </el-collapse-item>
      <el-collapse-item title="The second order" name="2">
        <div>
          Operation feedback: enable the users to clearly perceive their
          operations by style updates and interactive effects;
        </div>
        <div>
          Visual feedback: reflect current state by updating or rearranging
          elements of the page.
        </div>
      </el-collapse-item>
      <el-collapse-item title="The third order" name="3">
        <div>
          Simplify the process: keep operating process simple and intuitive;
        </div>
        <div>
          Definite and clear: enunciate your intentions clearly so that the
          users can quickly understand and make decisions;
        </div>
        <div>
          Easy to identify: the interface should be straightforward, which helps
          the users to identify and frees them from memorizing and recalling.
        </div>
      </el-collapse-item>
      <el-collapse-item title="The forth order" name="4">
        <div>
          Decision making: giving advices about operations is acceptable, but do
          not make decisions for the users;
        </div>
        <div>
          Controlled consequences: users should be granted the freedom to
          operate, including canceling, aborting or terminating current
          operation.
        </div>
      </el-collapse-item>
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