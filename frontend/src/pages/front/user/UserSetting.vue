<template>
  <el-card class="box-card">
    <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="120px" class="demo-ruleForm"
      :size="formSize" status-icon>
      <el-form-item label="用户头像" prop="avatar">
        <el-upload class="avatar-uploader" action="http://quanquancho.com:8080/user/upload-headshot" name="picture"
          :headers="{ 'token': token }" :show-file-list="false" :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload" :auto-upload="true">
          <img v-if="imageUrl" :src="imageUrl" class="avatar" :alt="ruleForm.avatarUrl" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>
      <br>
      <el-form-item label="用户名" prop="username" required>
        <el-input v-model="ruleForm.username" disabled />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="ruleForm.phone" />
      </el-form-item>
      <el-form-item label="身份证" prop="idNumber">
        <el-input v-model="ruleForm.idNumber" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)">保存</el-button>
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script setup lang="ts">
import { ElMessage, ElNotification } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import { reactive, ref, h } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

import type { UploadProps } from 'element-plus'
import request from '../../../utils/request'

interface UserInfo {
  avatarUrl: string
  username: string
  phone: string
  idNumber: string
}

const token = localStorage.token ? JSON.parse(localStorage.token) : ''

const imageUrl = ref('')

const handleAvatarSuccess: UploadProps['onSuccess'] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
  console.log(imageUrl.value)
  console.log(response.data.url)
  headshotUrl = response.data.url
}

let headshotUrl = $ref('')

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

const user = JSON.parse(localStorage.getItem('user') as string)

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<UserInfo>({
  avatarUrl: user.headshotUrl,
  username: user.name,
  phone: '',
  idNumber: ''
})

const rules = reactive<FormRules>({
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ],
  region: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'change',
    },
  ],
  count: [
    {
      required: true,
      message: 'Please select Activity count',
      trigger: 'change',
    },
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  date2: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a time',
      trigger: 'change',
    },
  ],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change',
    },
  ],
  resource: [
    {
      required: true,
      message: 'Please select activity resource',
      trigger: 'change',
    },
  ],
  desc: [
    { required: true, message: 'Please input activity form', trigger: 'blur' },
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    // 有图片的时候再提交
    if (valid && headshotUrl) {
      const arr = headshotUrl.split('/')
      const s = arr[arr.length - 1]
      const headshotId = s.substring(0, s.indexOf('.'))
      console.log(headshotId)
      request.post(`/user/change-headshot?headshotId=${headshotId}`)
        .then(res => {
          if (res.data.code === 200) {
            let user = JSON.parse(localStorage.getItem('user') as string)
            user.headshotUrl = headshotUrl
            localStorage.setItem('user', JSON.stringify(user))
            location.reload()
            ElNotification({
              title: "Success",
              message: h("i", { style: "color: teal" }, "修改个人信息成功"),
            })
          }
        })
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const options = Array.from({ length: 10000 }).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

<style scoped>
.box-card {
  width: 600px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>