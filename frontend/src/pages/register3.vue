<template>
  <div id="big">
    <div id="main">
      <div id='register_page' class="flex justify-center">
        <el-row>
          <el-col :span="18">
            <div id="title_setup">
              <b>SUSTech Regency</b>
            </div>
          </el-col>
          <el-col span>
            <div id="icon1">
              <img src="../images/hotel.png" id='hotel_img'/>
            </div>
          </el-col>
        </el-row>
        <el-scrollbar id="bar" height="62%">
          <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm">
            <div class="input_area">
              <p class="text_area">用户名</p>
              <el-form-item prop="name">
                <el-input class="input" size='large' v-model="ruleForm.name" autocomplete="off"
                          placeholder="Please input">
                  <template #prefix>
                    <el-icon>
                      <User/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </div>
            <div class="input_area">
              <p class="text_area">邮箱</p>
              <el-form-item prop="mail">
                <el-input class="input" size='large' v-model="ruleForm.mail" autocomplete="off"
                          placeholder="Please input">
                  <template #prefix>
                    <el-icon>
                      <Message/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </div>
            <div class="input_area" v-show="false">
              <p class="text_area">手机</p>
              <el-form-item prop="phone">
                <el-input class="input" size='large' v-model="ruleForm.phone" autocomplete="off"
                          placeholder="Please input">
                  <template #prefix>
                    <el-icon>
                      <Iphone/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </div>
            <div class="input_area">
              <p class="text_area">密码</p>
              <el-form-item prop="password">
                <el-input class="input" size='large' v-model="ruleForm.password" type="password"
                          autocomplete="off" placeholder="Please input password">
                  <template #prefix>
                    <el-icon>
                      <Lock/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </div>
            <div class="input_area">
              <p class="text_area">再次输入</p>
              <el-form-item prop="checkPassword">
                <el-input class="input" size='large' v-model="ruleForm.checkPassword" type="password"
                          autocomplete="off" placeholder="Please input password again">
                  <template #prefix>
                    <el-icon>
                      <Lock/>
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
            </div>
            <div class="input_area" v-show=false>
              <p class="text_area">手机验证码</p>
              <el-form-item prop="verify_phone">
                <el-input class="input" size='large' v-model="ruleForm.verify_phone" autocomplete="off"
                          placeholder="Please input">
                  <template #prefix>
                    <el-icon>
                      <Iphone/>
                    </el-icon>
                  </template>
                </el-input>
                <!-- <el-button @click="send_phone" size="small" id="verify" type="success" round>发送验证码 -->
                <!-- </el-button> -->

              </el-form-item>

            </div>
            <div class="input_area">
              <p class="text_area">邮箱验证码</p>
              <el-form-item prop="verify_mail">
                <el-input class="input" size='large' v-model="ruleForm.verify_mail" autocomplete="off"
                          placeholder="Please input">
                  <template #prefix>
                    <el-icon>
                      <Message/>
                    </el-icon>
                  </template>
                </el-input>
                <el-button :disabled="isDisabled" @click="send_mail" size="small" id="verify"
                           type="success" round>
                  {{ button_msg }}
                </el-button>

              </el-form-item>

            </div>
          </el-form>
          <div class="input_area">
            <el-radio-group v-model="UserOrmerchant" class="ml-4">
              <el-radio label="1" size="large">用户</el-radio>
              <el-radio label="2" size="large">商家</el-radio>
            </el-radio-group>
          </div>
        </el-scrollbar>
        <el-button @click="signup" id="signup_button" size="large" type="primary" round>注册</el-button>
        <el-link id="link_login" type="primary" href="./login">已有账号？点击这里</el-link>
      </div>
      <div class='page'>
        <!-- <img src="https://picx.zhimg.com/v2-fe2d9ef609280bad61b9be01314de881_r.jpg"> -->
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#big {

  width: 100vw;

  height: 106vh;
  background: url('../images/gray.png') 0% 0% /cover no-repeat;
}

#register_page {
  overflow: hidden;
  margin: 0 0;
  background: hsl(0, 0%, 100%);
  width: 38%;
  height: 100%;
  position: relative;
  border-radius: 25px 25px 25px 25px;

  box-shadow: 10px 10px 5px #888888;
}

#main {
  border-radius: 25px;
  position: absolute;
  width: 64.5%;
  height: 90%;
  top: 8%;
  left: 20%;
  display: flex;
  align-items: flex-start;
  // box-shadow: 10px 10px 5px #888888;

}


.page {
  width: 60%;
  height: 100%;
  // margin: 0 0;
  border-radius: 0px 25px 25px 0px;
  position: relative;
  left: -2%;
  box-shadow: 10px 10px 5px #888888;
  background: url('../images/background.png') 0% 0% / cover no-repeat;
  // background-image: url('../images/background.png');
}

#bar {
  position: relative;
  top: 6%;
}

@media screen and (min-width: 950px) {
  .page {
    width: 60%;
  }
}

@media screen and (max-width: 950px) {
  .page {
    display: none;
  }

  #register_page {
    width: 100%;
  }
}


#title_setup {
  font-family: "Lucida Calligraphy", cursive, serif, sans-serif;
  text-indent: 30px;
  position: relative;
  font-size: 25px;
  top: 35px;
}


#signup_button {
  position: absolute;
  left: 50px;
  // top: 530px;
  top: 82%;
  width: 90px;
}

// #verify {
//   position: absolute;
//   left: 50px;
//   top: 480px;
//   width: 90px;
// }

#link_login {
  position: absolute;
  left: 150px;
  top: 84%;
  // width: 130px;
}

.input_area {
  // display: flex;
  align-items: center;
  justify-content: center;
  height: 70px;
  margin: 30px;
  // text-align: center;
  border-radius: 4px;
  // background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}

#hotel_img {
  height: 50px;
  height: 50px;
  position: absolute;
  // top: 25px;
  top: 40%;
  // left: 290px;
  left: 75%;
}
</style>
<script lang="ts" setup>
import {ref, reactive} from 'vue'
import {h} from 'vue'
import request from '../utils/request'
import {formContextKey, FormInstance} from 'element-plus'
import {ElNotification} from 'element-plus'
import {User, Lock, Message, Iphone} from '@element-plus/icons-vue'
import axios from 'axios'
import router from '../router/index'

const UserOrmerchant = ref('')
const ruleForm = reactive({
  name: '',
  password: '',
  checkPassword: '',
  mail: '',
  phone: '',
  verify_mail: '',
  verify_phone: ''
})
var IsComplete = {
  name: 0,
  password: 0,
  checkPassword: 0,
  mail: 0,
  phone: 0,
  verify_mail: 0,
  verify_phone: 0,
  user: 0
}
const isDisabled = ref(false)
const button_msg = ref('发送验证码')
const ruleFormRef = ref<FormInstance>()
const checkName = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input your name'))
  } else {
    IsComplete.name = 1;
    callback()
  }
}
const checkPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password'))
  } else {

    const reg = new RegExp('(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,30}');
    if (!reg.test(value)) {
      callback(new Error('Your password is too simple'))
    } else {
      IsComplete.password = 1;
      callback()

    }
  }
}
const checkPassword2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    IsComplete.checkPassword = 1;
    callback()
  }
}
const checkMail = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input your mail'))
  } else {
    IsComplete.mail = 1;
    callback()
  }
}
const checkPhone = (rule: any, value: any, callback: any) => {
  const reg = /^[1-9][0-9]+$/gi;
  if (value === '') {
    callback(new Error('Please input your phone number'))
  } else if (!reg.test(value)) {
    callback(new Error('Please input a number'))
  } else {
    IsComplete.phone = 1;
    callback()
  }
}
const checkPhone_verify = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input verification code'))
  } else {
    IsComplete.verify_phone = 1;
    callback()
  }
}
const checkMail_verify = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('Please input verification code'))
  } else {
    IsComplete.verify_mail = 1;
    callback()
  }
}

const rules = reactive({
  password: [{validator: checkPassword, trigger: 'blur'}],
  checkPassword: [{validator: checkPassword2, trigger: 'blur'}],
  name: [{validator: checkName, trigger: 'blur'}],
  mail: [{validator: checkMail, trigger: 'blur'}],
  phone: [{validator: checkPhone, trigger: 'blur'}],
  verify_phone: [{validator: checkPhone_verify, trigger: 'blur'}],
  verify_mail: [{validator: checkMail_verify, trigger: 'blur'}],
})


const send_mail = () => {
  if (ruleForm.mail != '') {
    request.post('user/send-verification-code?email=' + ruleForm.mail)
      .then(function (response) {
        if (response.data.code == 200) {
          ElNotification({
            title: 'Success',
            message: h('i', {style: 'color: teal'}, 'You will receive an E-mail for verification'),
          })
          let timer = ref<number>(60)
          isDisabled.value = true
          const T = setInterval(function () {
            {
              timer.value--;
              button_msg.value = `${timer.value}s后重新获取`;
              if (timer.value == 0) {
                isDisabled.value = false;
                button_msg.value = "获取验证码";
                clearInterval(T);
              }
            }
          }, 1000);
        } else {
          ElNotification({
            title: 'Error',
            message: h('i', {style: 'color: red'}, response.data.message),
          })
        }
      })
  } else {
    ElNotification({
      title: 'Error',
      message: h('i', {style: 'color: red'}, 'Please input correct email'),
    })
  }
}

const signup = () => {
  if (UserOrmerchant.value != '' && IsComplete.name == 1 && IsComplete.password == 1 && IsComplete.checkPassword == 1 && IsComplete.mail == 1 && IsComplete.verify_mail == 1) {
    var id = 1;
    if (UserOrmerchant.value == '2') {
      id = 2;
    }
    request.post('/user/register?roleId=' + id + '&email=' + ruleForm.mail + '&verificationCode=' + ruleForm.verify_mail, {
      username: ruleForm.name,
      password: ruleForm.password,
    })
      .then(function (response) {
        if (response.data.code === 200) {
          ElNotification({
            title: 'Success',
            message: h('i', {style: 'color: green'}, "Register Success")
          })
          router.push('/')
        } else {
          ElNotification({
            title: 'Fail ',
            message: h('i', {style: 'color: red'}, "The username already exists")
          })
        }
      })
  } else {
    ElNotification({
      title: 'Fail',
      message: h('i', {style: 'color: red'}, "Please complete your infomation")
    })
  }

}
</script>
    