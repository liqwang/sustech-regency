<template>
  <div id="bigg">
    <link rel="stylesheet" href="login.css" />
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
        <div class="input_login">
          <div id="username">用户名/手机号/邮箱</div>
          <el-input v-model="username" placeholder="请输入用户名/手机号/邮箱" :prefix-icon="User" clearable id="input_username" />
          <div id="pwd">密码</div>
          <el-input v-model="pwd" type="password" placeholder="请输入密码" :prefix-icon="Lock" show-password id="input_pwd"
            @keyup.enter="Login" />
          <div id="err_msg" v-show="show_err_msg">用户名或密码错误，请重试</div>
          <div id="btns">
            <el-button type="primary" round id="btn_login" @click="Login">登录</el-button>
            <div id="sign_up" style="display: inline-block">
              <div>
                <span>没有账号？点此</span>
                <a href="./#/register" id="signup">注册</a>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="right">
        <div id="bgi"></div>
        <div id="content">
          <div id="welcome">Welcome to SUSTech Regency</div>
          <div id="slogn">Feel better than yesterday</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, getCurrentInstance } from "vue";
import { User, Lock } from "@element-plus/icons-vue";
const username = ref("");
const pwd = ref("");
const show_err_msg = ref(false);
const Login = () => {
  console.log(username, pwd);
};
</script>

<style >
body {
  background-image: url("https://withpinbox.com/static/media/bg.aab24a9d.png");
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

  #err_msg {
    color: red;
  }

  #signup {
    text-decoration: none;
  }

  #username,
  #pwd {
    color: rgb(118, 171, 249);
  }

  #username {
    margin-top: 1rem;
  }

  #pwd {
    margin-top: 0.7rem;
  }

  .input_login {
    width: 80%;
    height: 60%;
    display: table;
    margin: 0 auto;
  }

  #err_msg {
    margin: 1rem;
    margin-left: 0.5rem;
    font-size: 0.85rem;
    margin-bottom: 2rem;
  }

  #sign_up {
    margin-left: 1rem;
    margin-top: 1.3rem;
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
    height: 100px;
    /* background-color: red; */
  }

  #text,
  #logoimg {
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
    position: relative;
    width: 80px;
    height: 80px;
    display: block;
    background: url(../assets/hotel.png) 0% 0% / cover no-repeat;
    opacity: 0.75;
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
    font-family: "Inter";
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
    font-family: "Inter";
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

  #signup {
    text-decoration: none;
  }

  #username,
  #pwd {
    color: rgb(118, 171, 249);
  }

  #username {
    margin-top: 2rem;
  }

  #pwd {
    margin-top: 0.7rem;
  }

  .input_login {
    width: 67%;
    display: table;
    margin: 0 auto;
  }

  #err_msg {
    margin: 1rem;
    margin-left: 0.5rem;
    font-size: 0.85rem;
    margin-bottom: 2rem;
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
    height: 100px;
    /* background-color: red; */
  }

  #text,
  #logoimg {
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
    position: relative;
    width: 80px;
    height: 80px;
    display: block;
    background: url(../assets/hotel.png) 0% 0% / cover no-repeat;
    opacity: 0.75;
  }

  #btns {
    width: 100%;
    display: flex;
    flex-direction: row;
    padding: 0;
    margin-left: 1.2rem;
    margin-top: 1.3rem;
    gap: 3%;
    justify-content: start;
    align-content: center;
  }

  #sign_up {
    margin: 3px;
  }
}
</style>
