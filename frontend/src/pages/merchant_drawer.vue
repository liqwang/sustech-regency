<template>
    <el-drawer ref="drawerRef" v-model="dialog.value1" title="To register a new hotel!"
        :before-close="handleClose_drawer" direction="ltr" custom-class="demo-drawer">
        <div>
            <el-form :inline="true" :model="form" class="demo-form-inline">
                <el-form-item label="">
                    <el-input size="large" v-model="form.name" placeholder="Hotel Name" />
                </el-form-item>
                <el-form-item>
                    <el-select v-model="form.province" @change="selectProvince" filterable placeholder="Select">
                        <el-option v-for="item in ps.provinces" :key="item.name" :value="item.id" :label="item.name" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="form.city" @change="selectCity" filterable placeholder="Select">
                        <div v-for="item in cs.cities">
                            <el-option v-if="item.provinceId+''== form.province" :key="item.name" :value="item.id"
                                :label="item.name" />
                        </div>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="form.region" filterable placeholder="Select">
                        <div v-for="item in rs.regions">
                            <el-option v-if="item.cityId+''== form.city" :key="item.name" :value="item.id"
                                :label="item.name" />
                        </div>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-input size="large" v-model="form.address" placeholder="Detailed Address" />
                </el-form-item>
                <el-form-item label="">
                    <el-input size="large" v-model="form.latitude" placeholder="The latitude of hotel" />
                </el-form-item>
                <el-form-item label="">
                    <el-input size="large" v-model="form.longitude" placeholder="The longitude of hotel" />
                </el-form-item>
                <el-form-item label="">
                    <el-input size="large" v-model="form.tel" placeholder="The tel number" />
                </el-form-item>
            </el-form>
            <div>
                <el-button @click="cancelForm">Cancel</el-button>
                <el-button type="primary" @click="submitNewHotel">{{
                'Submit'
                }}</el-button>
            </div>
        </div>
    </el-drawer>
</template>
<script setup lang="ts">
import request from '../utils/request'
import { ref, reactive, h, onMounted } from 'vue'
import { ElNotification, DialogBeforeCloseFn, ElDrawer, ElMessageBox } from 'element-plus'
type Province = {
    id: Number,
    name: string,
    alias: string
}
interface provinces_reactive {
    provinces: Array<Province>
    provinceId: number
};
const ps: provinces_reactive = reactive({
    provinces: [],
    provinceId: -1
})

type City = {
    id: Number,
    provinceId: number,
    name: string,
    isProvincialCapital: boolean
}
interface cities_reactive {
    cities: Array<City>
};
const cs: cities_reactive = reactive({
    cities: []
})
type Region = {
    id: Number,
    name: string,
    cityId: number
}
interface regions_reactive {
    regions: Array<Region>
};
const rs: regions_reactive = reactive({
    regions: []
})
request.get('/info/province/all').then((response) => {
    ps.provinces = response.data.data
    // console.log(ps.provinces)
})
const selectProvince = () => {
    request.get('/info/city/all').then((response) => {
        cs.cities = response.data.data
        // console.log(cs.cities)
    })
}
const selectCity = () => {
    request.get('/info/region/all').then((response) => {
        rs.regions = response.data.data
        // console.log(cs.cities)
    })
}
const form = reactive({
    name: '',
    city: '',
    address: '',
    province: '',
    tel: '',
    region: '',
    latitude: '',
    longitude: ''
})
const emit = defineEmits(['cancel'])
const cancelForm = () => {
    emit('cancel', false)
}
const  submitNewHotel = () => {
    if (form.address == '' || form.name == '' || form.province == '' ||
        form.city == ''||form.region==''||form.latitude==''
        ||form.longitude=='' || form.tel == '') {
        ElNotification({
            title: 'Fail',
            message: h('i', { style: 'color: red' }, "Please complete infomation")
        })
    }
    else {
        console.log(form)
       request.post('/merchant/hotel/add?address=' + form.address +
            '&name=' + form.name + '&tel=' + form.tel +
            '&regionId=' + form.region + '&latitude=' + form.latitude +
            '&longitude=' + form.longitude)
            .then(function (response) {
                console.log(response.data)
                if (response.data.code === 200) {
                    ElNotification({
                        title: 'Success',
                        message: h('i', { style: 'color: green' }, "Success")
                    })
                    emit('cancel',false)
                } else {
                    ElNotification({
                        title: 'Error',
                        message: h('i', { style: 'color: green' }, 'code = ' + response.data.code)
                    })
                    emit('cancel',false)
                }
            })
        // drawerRef.value!.close()
    }
}

const drawerRef = ref<InstanceType<typeof ElDrawer>>()
type props = {
    value1: boolean
}
var dialog = defineProps<props>()
const handleClose_drawer: DialogBeforeCloseFn = () => {
    ElMessageBox.confirm('Do you want to cancel?')
        .then(() => {
            emit('cancel', false)
        })
        .catch(() => {
            // catch error
        })
}
</script>
<style lang="scss">

</style>