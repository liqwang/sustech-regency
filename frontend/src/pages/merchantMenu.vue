<template>
    <el-tag size="large" type="success" style="margin-left:1vw">My Hotels</el-tag>
    <div id="menu">
        <el-menu default-active="1" @select="selectMenu">
            <div  v-for="i in hs.hotels.length" >
                <el-sub-menu  :index="hs.hotels[i-1].cityName" v-if="ss.values1[i-1]">
                    <template #title>
                        <el-icon>
                            <location />
                        </el-icon>
                        <span>{{hs.hotels[i-1].cityName}}</span>
                    </template>
                    <div v-for="i2 in hs.hotels.length">
                    <el-menu-item-group :title="hs.hotels[i2-1].regionName" v-if="rs.values1[i2-1]&&hs.hotels[i2-1].cityName==hs.hotels[i-1].cityName" :index="hs.hotels[i2-1].regionName">
                        <div v-for="i3 in hs.hotels.length">
                            <el-menu-item :index="hs.hotels[i3-1].id" v-if="hs.hotels[i3-1].regionName==hs.hotels[i2-1].regionName">
                                {{hs.hotels[i3-1].name}}
                            </el-menu-item>
                        </div>
                    </el-menu-item-group>
                </div>
                </el-sub-menu>
            </div>
        </el-menu>
    </div>
</template>
<script setup lang="ts">
import request from '../utils/request';
import { ref, reactive, computed } from 'vue'
import {
    Document,
    Menu as IconMenu,
    Setting, ArrowLeft, Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
} from '@element-plus/icons-vue'
import { fa, tr } from 'element-plus/es/locale';
const emit = defineEmits(['select-hotel'])
const selectMenu = (index: string, path: string[],
    item: string) => {
    emit('select-hotel',index)
}
type Hotel = {
    address: string,
    cityName: string,
    id: number,
    latitude: number,
    longitude: number,
    name: string,
    regionName: string,
    tel: string,
}
interface hotels_reactive {
    hotels: Array<Hotel>
}
const hs: hotels_reactive = reactive({
    hotels: []
})
interface shows {
    values1: Array<boolean>
}
const ss:shows=reactive({
    values1:[]
})
const rs:shows=reactive({
    values1:[]
})
request.get('merchant/hotel/all').then(function (response) {
    if(response.data.code==200){
    hs.hotels = response.data.data
    console.log(hs.hotels)
    let set=new Set()
    for(let i =0;i<hs.hotels.length;i++){
        if(set.has(hs.hotels[i].cityName)==false){
            ss.values1[i]=true
            set.add(hs.hotels[i].cityName)
        }else{
            ss.values1[i]=false
        }
        if(set.has(hs.hotels[i].regionName)==false){
            rs.values1[i]=true
            set.add(hs.hotels[i].regionName)
        }else{
            rs.values1[i]=false
        }
    }

}
})
</script>
<style lang="scss">
#menu {
    width: 20vh;
}
</style>