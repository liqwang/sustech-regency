<template>
    <el-scrollbar height="80vh">
        
        <div v-if="show_content">
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
                    {{hotel.detail.name}}
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
                    {{hotel.detail.tel}}
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
                    CityName
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
                    {{hotel.detail.address}}
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
    </div>
    <div v-else>
        <el-empty  />
    </div>
    </el-scrollbar>
    <el-dialog v-model="show_input" title="This is a Dialog">
        <!-- <div id="chart" style="width:80%;height:60%"></div> -->

    </el-dialog>
</template>
<script lang ='ts' setup>
import { ref, reactive,watch } from 'vue'
import request from '../utils/request';
type props = {
    HotelId: string
}
var id_par = defineProps<props>()
console.log(id_par)
interface Hotel_type {
    address: string,
    id: number,
    latitude: number,
    longitude: number,
    merchantId: number,
    name: string,
    regionId: number,
    tel: string
}
const show_content = ref(true)
interface hotel_reactive {
    detail: any
}
const hotel: hotel_reactive = reactive({
    detail:{}
})
watch(id_par,(new_val,old_val)=>{
    if (id_par.HotelId != 'kong') {
    request.get('merchant/hotel/get?hotelId=' + id_par.HotelId).then(function (response) {
        if (response.data.code == 200) {
            hotel.detail = response.data.data
            console.log(hotel.detail)
        }else alert('Error')
    })
    show_content.value=true
} else {
    show_content.value = false
}
},{
    deep:true,
    immediate:true
})
var image1 = ref('https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg')
var image2 = ref('https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg')
var image3 = ref('https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg')
var v1 = ref(true)
var v2 = ref(false)
var v3 = ref(false)
const show_input = ref(false)
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
</script>
<style lang="scss">

</style>