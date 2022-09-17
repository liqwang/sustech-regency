<template>
  <div id="bigg">
    <div id="main">
      <div id="left">
        <!-- LOGO区域 -->
        <div id="logo">
          <div id="title">
            <div id="text">SUSTech Regency</div>
          </div>
          <div id="hotel">
            <div id="logoimg"></div>
          </div>
        </div>

        <div id="input_area">
          <el-row style="margin-bottom: 0">
            <el-col :span="3"></el-col>
            <el-col :span="10" style="display: flex; justify: start"
              ><div class="msg">请输入用户名</div></el-col
            >
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="18">
              <el-form-item label="" prop="username">
                <el-input
                  type="text"
                  maxlength="11"
                  placeholder="用户名"
                  v-model="form.username"
                  clearable
                /> </el-form-item
            ></el-col>
            <el-col :span="3"></el-col>
          </el-row>

          <el-row style="margin-bottom: 0">
            <el-col :span="3"></el-col>
            <el-col :span="10" style="display: flex; justify: start"
              ><div class="msg">请输入验证码</div></el-col
            >
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="13">
              <el-input
                type="text"
                placeholder="验证码"
                v-model="form.code"
              /> </el-col
            ><el-col :span="5">
              <el-button
                class="btn-orange"
                @click="getCode"
                :disabled="!btn_vaild"
                >{{ btn_msg }}</el-button
              >
            </el-col>
            <el-col :span="3"></el-col>
          </el-row>

          <el-row style="margin-bottom: 0" v-show="pass_check">
            <el-col :span="3"></el-col>
            <el-col :span="10" style="display: flex; justify: start"
              ><div class="msg">请输入新密码</div></el-col
            >
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="18"
              ><el-form-item label="" prop="newpwd" v-show="pass_check">
                <el-input
                  type="text"
                  maxlength="11"
                  placeholder="请输入新密码"
                  v-model="form.newpwd"
                /> </el-form-item
            ></el-col>
            <el-col :span="3"></el-col>
          </el-row>

          <el-row style="margin-bottom: 0" v-show="pass_check">
            <el-col :span="3"></el-col>
            <el-col :span="10" style="display: flex; justify: start"
              ><div class="msg">再次输入密码</div></el-col
            >
          </el-row>
          <el-row>
            <el-col :span="3"></el-col>
            <el-col :span="18">
              <el-form-item label="" prop="newpwd" v-show="pass_check">
                <el-input
                  type="text"
                  maxlength="11"
                  placeholder="请再次输入新密码"
                  v-model="form.newpwd2"
                /> </el-form-item
            ></el-col>
            <el-col :span="3"></el-col>
          </el-row>

          <div id="btn_area">
            <el-button
              type="primary"
              round
              id="btn_sub"
              v-show="!pass_check"
              @click="submit"
              :disabled="!has_get_code"
              >提交</el-button
            ><el-button
              type="primary"
              round
              id="btn_submit"
              v-show="pass_check"
              @click="doubleCheck"
              >提交并登陆账号</el-button
            >
            <el-button
              type="primary"
              round
              id="btn_get_pwd_back"
              @click="cancel"
              >取消并返回</el-button
            >
          </div>
        </div>
      </div>
      <div id="right">
        <div id="bgi"></div>
        <div id="content">
          <div id="welcome">在此页面更改您的用户密码</div>
          <div id="slogn">
            <ul>
              长度为8-30位
            </ul>
            <ul>
              包含大小写字母
            </ul>
            <ul>
              包含特殊字符
            </ul>
            <ul>
              包含数字
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#input_area {
  text-align: center;
}
#bigg {
  background-image: url('https://withpinbox.com/static/media/bg.aab24a9d.png');
  width: auto;
  height: 780px;
}

#main {
  position: absolute;
  width: 80%;
  height: 80%;
  top: 10%;
  left: 10%;
  display: flex;
  align-items: flex-start;
  padding: 0px;
  border-radius: 30px;
  background-color: rgb(255, 255, 255);
  filter: drop-shadow(10px 10px 4px rgba(0, 0, 0, 0.1));
}

/* 在850px以下直接用left */
@media screen and (max-width: 850px) {
  #btn_sign,
  #btn_get_pwd_back,
  #btn_sub,
  #btn_submit {
    margin: 0;
  }
  #right {
    display: none;
  }

  #left {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    /* 主轴居中 */
    justify-content: center;
    /* 交叉轴居中 */
    align-items: center;
    border-radius: 30px;
  }

  .msg {
    color: rgb(118, 171, 249);
  }

  .input_login {
    width: 80%;
    height: 60%;
    display: table;
    margin: 0 auto;
  }

  #logo {
    width: 100%;
    height: 20%;
    display: flex;
    flex-direction: row;
    padding: 0;
    margin: 0;
    margin-top: 0;
    gap: 0;
    justify-content: center;
    align-content: center;
    /* background-color: red; */
  }

  #title {
    order: 0;
    width: 55%;
    text-align: center;
    line-height: 50px;
    /* background-color: red; */
  }

  #hotel {
    order: 1;
    width: 100px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  #text {
    margin: 0;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  #text {
    font-size: 2.1rem;
  }

  #logoimg {
    width: 100px;
    height: 100px;
    background: url(../assets/hotel.png) 0% 0% / cover no-repeat;
    opacity: 0.75;
  }

  #btn_area {
    margin: 1vh;
    margin-top: 2vh;
    padding: 1.5vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 2vh;
  }

  #btn_get_pwd_back,
  #btn_sub,
  #btn_submit {
    min-width: 25vw;
    max-width: 45vw;
  }
}

/* 在850px以上，用left+right */
@media screen and (min-width: 850px) {
  #right {
    position: relative;
    width: 60%;
    height: 100%;
    display: block;
    border-radius: 0px 30px 30px 0px;
  }

  #bgi {
    position: absolute;
    width: 100%;
    height: 100%;
    display: block;
    border-radius: 0px 30px 30px 0px;
    background: url(../assets/background-img.jpg) 0% 0% / cover no-repeat;
    opacity: 0.75;
  }

  #content {
    position: absolute;
    display: block;
    width: 100%;
  }

  #welcome {
    font-family: 'Inter';
    font-style: normal;
    font-weight: 400;
    font-size: 3rem;
    line-height: 3rem;
    color: #fff;
    margin-top: 15%;
    margin-left: 5%;
    text-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
    opacity: 1;
  }

  #slogn {
    font-family: 'Inter';
    font-style: normal;
    font-weight: 200;
    font-size: 1.7rem;
    line-height: 1.7rem;
    color: #fff;
    margin-top: 2rem;
    margin-left: 5%;
    text-shadow: 4px 4px 4px rgba(0, 0, 0, 0.3);
    opacity: 1;
  }

  #left {
    width: 40%;
    height: 100%;
    display: block;
    border-radius: 30px 0px 0px 30px;
  }

  #err_msg {
    color: red;
  }

  .msg {
    color: rgb(118, 171, 249);
  }

  .input_login {
    width: 67%;
    display: table;
    margin: 0 auto;
  }

  #logo {
    width: 100%;
    height: 15%;
    display: flex;
    flex-direction: row;
    padding: 0;
    margin: 0;
    margin-top: 19%;
    gap: 0;
    justify-content: center;
    align-content: center;
    /* background-color: red; */
  }

  #title {
    order: 0;
    width: 55%;
    text-align: center;
    line-height: 50px;
    /* background-color: red; */
  }

  #hotel {
    order: 1;
    width: 100px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* background-color: red; */
  }

  #text {
    font-size: 2.1rem;
    margin: 0;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  #logoimg {
    width: 80px;
    height: 80px;
    background: url(../assets/hotel.png) 0% 0% / cover no-repeat;
    opacity: 0.75;
  }

  #btn_area {
    margin: 0.5vw;
    padding: 1.5vw;
    display: flex;
    flex-direction: row;
    justify-content: center;
    gap: 1vw;
  }
  #btn_get_pwd_back,
  #btn_sub,
  #btn_submit {
    min-width: 6vw;
    max-width: 9vw;
  }
}

.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>

<script lang="ts" setup>
import {ElNotification} from 'element-plus';
import {ref, h} from 'vue';
import {reactive} from 'vue';
import {onMounted} from 'vue';
import {User, Lock} from '@element-plus/icons-vue';
import request from '../utils/request';
import router from '../router';
import {el, fa, tr} from 'element-plus/es/locale';
import {fromPairs} from 'lodash';

// do not use same name with ref
let btn_msg = $ref('获取验证码');
const back_code = $ref('');
let btn_vaild = $ref(true);
let has_get_code = $ref(false);
let pass_check = $ref(false);
const form = reactive({
  username: '',
  code: '',
  newpwd: '',
  newpwd2: '',
});
const cancel = () => {
  return router.push('/');
};
const getCode = () => {
  //从后端获取验证码,btn_msg变成 “「60s」后再次获取” ，按钮在倒计时期间变成灰色
  if (form.username != '') {
    let timer = $ref<number>(60);
    btn_vaild = false;

    const T = setInterval(function () {
      {
        timer--;
        btn_msg = `${timer}s后重新获取`;
        if (timer == 0) {
          btn_vaild = true;
          btn_msg = '获取验证码';
          clearInterval(T);
        }
      }
    }, 1000);

    has_get_code = true;

    // 获取backcode
  } else {
    ElNotification({
      title: 'Failed',
      message: h('i', {style: 'color: red'}, '用户名为空'),
    });
  }
};
const submit = () => {
  console.log(form.code, 'input and back ', back_code);
  // 检测验证码是否和后端一致，若一致则通过检查,若不一致则弹窗报错
  // 获取后端返回的验证码 backcode

  //验证是否一致
  if (form.code == back_code) {
    pass_check = true;
    ElNotification({
      title: 'Success',
      message: h('i', {style: 'color: teal'}, '验证成功，请修改密码'),
    });
  } else {
    pass_check = false;
    ElNotification({
      title: 'Failed',
      message: h('i', {style: 'color: red'}, '验证码错误，请重试'),
    });
  }
};
const doubleCheck = () => {
  if (form.newpwd === form.newpwd2) {
    //修改密码，并用新密码登陆
    // 修改密码

    // 登陆
    request
      .post(`/user/login`, {
        password: form.newpwd,
        username: form.username,
      })
      .then((res) => {
        const token = res.data.data.token;
        localStorage.setItem('token', token);
        ElNotification({
          title: 'Success',
          message: h('i', {style: 'color: teal'}, 'Login success'),
        });
        router.push('/back');
      });
  } else {
    // 弹窗报错，清空两个新密码
    // 弹窗报错
    ElNotification({
      title: 'Failed',
      message: h('i', {style: 'color: red'}, '两次密码输入不一致，请重试'),
    });
    // 清空密码
    form.newpwd = '';
    form.newpwd2 = '';
  }
};
</script>
