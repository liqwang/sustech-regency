<template>
  <merchant-drawer @cancel="getCancel" :value1="dialog" />
  <div id="top">
    <el-row id="r1">
      <el-col :span="1"> </el-col>
      <el-col :span="19"></el-col>
      <el-col :span="2">
        <el-avatar :size="66" class="mr-3" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
      </el-col>

      <el-col :span="2"> </el-col>
    </el-row>
    <el-row id="r2">
      <el-col :span="1">
        <el-button @click="goback" id="back" type="success" :icon="ArrowLeft">Log out</el-button>
      </el-col>
      <el-col :span="19"></el-col>
      <el-col :span="2">
        <div style="position: relative; left: 1vw">
          <b>{{ username }}</b>
        </div>
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
                <el-dropdown-item command="addNew">Add a hotel</el-dropdown-item>
                <el-dropdown-item command="edit">profile</el-dropdown-item>
                <el-dropdown-item @click="toUserPage">用户端</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-col>
    </el-row>
  </div>

  <el-row id="r3">
    <el-col :span="4" id="menu">
      <merchant-menu  @select-hotel="load_hotel"></merchant-menu>
    </el-col>
    <el-col :span="16">
      <merchant-scroll :HotelId="hotelId" />
    </el-col>
    <el-col :span="4"></el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { ArrowDown, ArrowLeft, UserFilled } from '@element-plus/icons-vue';
import { ref } from 'vue';
import router from '../router';
import MerchantDrawer from './merchantDrawer.vue';
import MerchantScroll from './merchantScroll.vue'
import MerchantMenu from './merchantMenu.vue';
const getCancel = (cancel: boolean) => {
  dialog.value = cancel;
};
const hotelId = ref('kong');
const load_hotel = (index: string) => {
  hotelId.value = index;
};
const option = (command: string | number | object) => {
  if (command == 'addNew') {
    dialog.value = true;
  }
};

const goback = () => {
  localStorage.removeItem('token');
  localStorage.clear();
  router.push('/login');
};

const toUserPage = () => {
  router.push('/');
}

const dialog = ref(false);
const username = ref(localStorage.getItem('user') == null ? '未登录' : JSON.parse(localStorage.getItem('user') as string).name);
</script>
<style scoped lang="scss">
#top {
  background-color: rgba(128, 212, 238, 0.101);
}

@media screen and (max-width: 950px) {
  #r1 {
    display: none;
  }
  #menu{
    visibility: hidden;
  }
  #r2 {}

  #drop {
    top: 0;
  }
}

@media screen and (min-width: 950px) {
  #r2 {
    // display: none;
  }

  #r1 {
    height: 9vh;
  }

  #drop {
    position: relative;
    left: 0;
    top: 0;
  }

  #back {
    position: absolute;
    top: -4vh;
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
