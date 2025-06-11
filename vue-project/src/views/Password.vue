<template>

  <div>
    <el-card style="padding: 40px 20px; width: 700px">
      <el-form ref="formRef" :rules="data.rules" :model="data.form"
               style="width: 400px">
        <el-form-item prop="password">
          <el-input show-password prop="password" size="large" prefix-icon="Lock"
                    v-model="data.form.password"
                    placeholder="Please enter old password" ></el-input>
        </el-form-item>
        <el-form-item prop="newPassword">
          <el-input show-password prop="newPassword" size="large"
                    prefix-icon="Lock"
                    v-model="data.form.newPassword"
                    placeholder="Please enter new password" ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input show-password prop="confirmPassword" size="large" prefix-icon="Lock"
                    v-model="data.form.confirmPassword"
                    placeholder="Please confirm new password" ></el-input>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
  <el-button @click="updatePassword" type="primary"
             style="margin-top:20px; padding:
  20px
  30px"
  >Change Password</el-button>
</template>

<script setup>

import { reactive, ref } from 'vue'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'
const validatePass = (rule, value, callback) => {
  if(!value){
    callback(new Error('Please confirm password'))
  }else if (value !== data.form.newPassword){
    callback(new Error('Passwords do not match!'))
  }else {
    callback()
  }
}
const data = reactive({
  loginUser: JSON.parse(localStorage.getItem('login-user')),
  form: {
    password: '',
    newPassword: '',
    confirmPassword:'',
  },
  rules: {
    username:[{required: true, message: 'Please enter username', trigger: 'blur'}],
    password:[{required: true, message: 'Please enter password', trigger: 'blur'}],
    newPassword:[{required: true, message: 'Please enter password', trigger:
        'blur'}],
    confirmPassword:[{
      validator: validatePass, trigger: 'blur'
    }]
  }
})


const formRef = ref()

const updatePassword = () => {
  data.form.id = data.loginUser.id;
  data.form.role = data.loginUser.role;
  formRef.value.validate((valid)=>{
      if(valid){
        request.put('/updatePassword', data.form).then((res) => {
          if (res.code == '200') {
            ElMessage.success('Update password successful!')
            localStorage.removeItem('login-user')
            setTimeout(()=>{
              location.href="/login";
            }, 500)

          } else {
            ElMessage.error(res.msg)
          }
        })
      }
    }
  )
}
</script>
