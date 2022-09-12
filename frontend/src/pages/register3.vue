<template>

    <div id='register_page' class="flex justify-center">
        <el-row>
            <el-col :span="18">
                <div id="title">
                    <b>SUSTech Regency</b>
                </div>
            </el-col>
            <el-col span>
                <div id="icon1">
                    <img src="../images/hotel.png" id='hotel_img' />
                </div>
            </el-col>
        </el-row>
        <el-scrollbar id="bar" height="400px">
            <el-form ref="ruleFormRef" :model="ruleForm" status-icon :rules="rules" class="demo-ruleForm">
                <div class="input_area">
                    <p class="text_area">姓名</p>
                    <el-form-item prop="name">
                        <el-input class="input" size='large' v-model="ruleForm.name" autocomplete="off"
                            placeholder="Please input">
                            <template #prefix>
                                <el-icon>
                                    <User />
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
                                    <Message />
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                </div>
                <div class="input_area">
                    <p class="text_area">手机</p>
                    <el-form-item prop="phone">
                        <el-input class="input" size='large' v-model="ruleForm.phone" autocomplete="off"
                            placeholder="Please input">
                            <template #prefix>
                                <el-icon>
                                    <Iphone />
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                </div>
                <div class="input_area">
                    <p class="text_area">密码</p>
                    <el-form-item prop="password">
                        <el-input class="input" size='large' v-model="ruleForm.password" type="password" autocomplete="off"
                            placeholder="Please input password">
                            <template #prefix>
                                <el-icon>
                                    <Lock />
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                </div>
                <div class="input_area">
                    <p class="text_area">再次输入</p>
                    <el-form-item prop="checkPassword">
                        <el-input class="input" size='large' v-model="ruleForm.checkPassword" type="password" autocomplete="off"
                            placeholder="Please input password again">
                            <template #prefix>
                                <el-icon>
                                    <Lock />
                                </el-icon>
                            </template>
                        </el-input>
                    </el-form-item>
                </div>
                <div class="input_area">
                    <p class="text_area">手机验证码</p>
                    <el-form-item prop="verify_phone">
                        <el-input class="input" size='large' v-model="ruleForm.verify_phone" autocomplete="off"
                            placeholder="Please input">
                            <template #prefix>
                                <el-icon>
                                    <Iphone />
                                </el-icon>
                            </template>
                        </el-input>
                        <el-button @click="send_phone" size="small" id="verify" type="success" round>发送验证码</el-button>

                    </el-form-item>

                </div>
                <div class="input_area">
                    <p class="text_area">邮箱验证码</p>
                    <el-form-item prop="verify_mail">
                        <el-input class="input" size='large' v-model="ruleForm.verify_mail" autocomplete="off"
                            placeholder="Please input">
                            <template #prefix>
                                <el-icon>
                                    <Message />
                                </el-icon>
                            </template>
                        </el-input>
                        <el-button @click="send_mail" size="small" id="verify" type="success" round>发送验证码</el-button>

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
        <el-button @click="signup" id="signup" size="large" type="primary" round>注册</el-button>
        <el-link id="link_login" type="primary" href="../login" target="_blank">已有账号？点击这里</el-link>
    </div>
    <div class='page'>
        <!-- <img src="https://picx.zhimg.com/v2-fe2d9ef609280bad61b9be01314de881_r.jpg"> -->
    </div>
</template>
  
<style lang="scss" >
body {
    // background-color: #a3c3ed;
    background-image: url('../images/gray.png');
    background-repeat: no-repeat;
    background-size: 1560px 780px;
}

#register_page {
    margin: 0 auto;
    background: hsl(0, 0%, 100%);
    width: 367px;
    height: 650px;
    border-radius: 25px;
    position: absolute;
    left: 250px;
    top: 40px;
    box-shadow: 10px 10px 5px #888888;
}

#bar {
    position: relative;
    top: 50px;
}

.page {
    background: hsl(201, 81%, 49%);
    width: 610px;
    height: 650px;
    border-radius: 0px 25px 25px 0px;
    position: absolute;
    left: 600px;
    top: 40px;
    box-shadow: 10px 10px 5px #888888;
    background-size: 1240px 800px;
    background-repeat: no-repeat;
    // background-image: url('https://picx.zhimg.com/v2-fe2d9ef609280bad61b9be01314de881_r.jpg');
    background-image: url('../images/background.png');
}

@media screen and (min-width: 520px) {
    .page {
        width: 610;
    }
}

@media screen and (max-width: 1024px) {
    .page {
        width: 0px;
    }
}


#title {
    font-family: "Lucida Calligraphy", cursive, serif, sans-serif;
    text-indent: 30px;
    position: relative;
    font-size: 25px;
    top: 35px;
}



#signup {
    position: absolute;
    left: 50px;
    top: 530px;
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
    top: 540px;
    width: 130px;
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
    top: 25px;
    left: 290px;
}
</style>
<script lang="ts" setup>
import { ref, reactive } from 'vue'
import { h } from 'vue'
import type { FormInstance } from 'element-plus'
import { ElNotification } from 'element-plus'
import { User, Lock, Message, Iphone } from '@element-plus/icons-vue'
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

const ruleFormRef = ref<FormInstance>()
const checkName = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('Please input your name'))
    } else {
        callback()
    }
}
const checkPassword = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else {
        if (ruleForm.password !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('checkPass', () => null)
        }
        callback()
    }
}
const checkPassword2 = (rule: any, value: any, callback: any) => { if (value === '') {
    callback(new Error('Please input the password again'))
  } else if (value !== ruleForm.password) {
    callback(new Error("Two inputs don't match!"))
  } else {
    callback()
  }}
const checkMail = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('Please input your mail'))
    } else {
        callback()
    }
}
const checkPhone = (rule: any, value: any, callback: any) => {
    const reg=/^[1-9][0-9]+$/gi;
    if (value === '') {
        callback(new Error('Please input your phone number'))
    } else if(!reg.test(value)){
        callback(new Error('Please input a number'))
    }
    else{
        callback()
    }
}
const checkPhone_verify = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('Please input verification code'))
    } else {
        callback()
    }
}
const checkMail_verify = (rule: any, value: any, callback: any) => {
    if (value === '') {
        callback(new Error('Please input verification code'))
    } else {
        callback()
    }
}

const rules = reactive({
    password: [{ validator: checkPassword, trigger: 'blur' }],
    checkPassword: [{ validator: checkPassword2, trigger: 'blur' }],
    name: [{ validator: checkName, trigger: 'blur' }],
    mail: [{ validator: checkMail, trigger: 'blur' }],
    phone: [{ validator: checkPhone, trigger: 'blur' }],
    verify_phone: [{ validator: checkPhone_verify, trigger: 'blur' }],
    verify_mail: [{ validator: checkMail_verify, trigger: 'blur' }],
})





const send_mail = () => {
    ElNotification({
        title: 'Success',
        message: h('i', { style: 'color: teal' }, 'You will receive an E-mail for verification'),
    })
}
const send_phone = () => {
    ElNotification({
        title: 'Success',
        message: h('i', { style: 'color: teal' }, 'You will receive a note for verification'),
    })
}

const signup = () => {
    ElNotification({
        title: 'Fail',
        message: h('i', { style: 'color: red' }, "Function for register not yet open")
    })
}

</script>
    