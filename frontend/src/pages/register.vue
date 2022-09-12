<template>

  <div class='register_page' >
    <p id="title">
      <b>SUSTech Regency</b>
    </p>
    <p id="p_mail">
      邮箱
    </p>
    <p id="p_name">
      姓名
    </p>
    <p id="p_phone">
      手机
    </p>
    <p id="p_pwd">
      密码
    </p>
    <el-input class="mail_input" size='large' v-model="input_email" placeholder="Please input" >
     <template #prefix>
      <el-icon><Message /></el-icon>
     </template>
    </el-input>

    <el-input class="name_input" size='large' v-model="input_name" placeholder="Please input" >
     <template #prefix>
      <el-icon><User /></el-icon>
     </template>
    </el-input>

    <el-input class="phone_input" size='large' v-model="input_phone" placeholder="Please input" >
     <template #prefix>
      <el-icon><Iphone /></el-icon>
     </template>
    </el-input>

    <el-input class="password_input" size="large"
    v-model="password"
    type="password"
    placeholder="Please input password"
    show-password
  >
  <template #prefix>
    <el-icon><Lock /></el-icon>
     </template>
</el-input>
<el-button id="signup"  type="primary" round>注册</el-button>
<el-link id="link_login" type="primary" href="../login" target="_blank">已有账号？点击这里</el-link>  
</div>
  <div class='page'>
    <!-- <img src="https://picx.zhimg.com/v2-fe2d9ef609280bad61b9be01314de881_r.jpg"> -->
  </div>
</template>

<style lang="scss" >
body{
  background-color: #a3c3ed;
}
.register_page{
  margin: 0 auto;
	background:hsl(0, 0%, 100%);
	width:367px;
  height: 650px;
	border-radius:25px;
  position: absolute;
  left: 250px;
  top:40px;
  box-shadow: 10px 10px 5px #888888;
}
.page{
	background:hsl(201, 81%, 49%);
	width:610px;
  height: 650px;
	border-radius:0px 25px 25px 0px;
  position: absolute;
  left: 600px;
  top:40px;
  box-shadow: 10px 10px 5px #888888;
  background-size:620px 800px;
  background-image:url('https://picx.zhimg.com/v2-fe2d9ef609280bad61b9be01314de881_r.jpg');
}
@media screen and (min-width: 520px) {
    .page {
        width:610;
    }
}
@media screen and (max-width: 1024px) {
    .page{
        width:0px;
    }
}
// img{
//   max-width: 100%;
//   height: auto;
// }
#p_mail{
  text-indent: 50px;
  position: relative;
  top:170px;
}
#p_phone{
  text-indent: 50px;
  position: relative;
  top:230px;
}
#p_name{
  text-indent: 50px;
  position: relative;
  top:50px;
}
#p_pwd{
  text-indent: 50px;
  position: relative;
  top:310px;
}
#title{
  font-family: "Lucida Calligraphy", cursive, serif, sans-serif;
    text-indent: 30px;
    position: relative;
    font-size: 25px;
    top:35px;
}
.phone_input{
  position: absolute;
  left:50px;
  top:330px;
  width:250px;
}
.password_input{
  position: absolute;
  left:50px;
  top:430px;
  width:250px;
}
.name_input{
  position: absolute;
  left:50px;
  top:130px;
  width:250px;
}


.mail_input{
  position: absolute;
  left:50px;
  top:230px;
  width:250px;
}
#signup{
  position: absolute;
  left:50px;
  top:530px;
  width:90px;
}
#link_login{
  position: absolute;
  left:150px;
  top:540px;
  width:130px;
}
</style>
<script lang="ts" setup>
import { ref } from 'vue'
import { User,Lock,Message,Iphone } from '@element-plus/icons-vue'
const input_email = ref('')
const password=ref('')
const input_name=ref('')
const input_phone = ref('')
</script>
  