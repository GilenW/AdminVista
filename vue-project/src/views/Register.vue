<template>
  <div>
<!--    TODO: add login image-->
    <div class="login-container">

        <div class="login-box">
          <div style="padding: 30px; background-color: white; margin-left:
          100px; border-radius:20px">
            <div
              style="margin-bottom: 30px; font-size: 25px; color:
              var(--primary-color); text-align: center; ">Welcome to
              register
              management
              system</div>
            <el-form ref="formRef" :rules="data.rules" :model="data.form"
                     style="width: 400px">
              <el-form-item prop="username">
                <el-input size="large" prefix-icon="User"
                          v-model="data.form.username"
                          placeholder="Please enter username"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input show-password prop="password" size="large" prefix-icon="Lock"
                          v-model="data.form.password"
                          placeholder="Please enter password" ></el-input>
              </el-form-item>
              <el-form-item prop="confirmPassword">
                <el-input show-password prop="confirmPassword" size="large" prefix-icon="Lock"
                          v-model="data.form.confirmPassword"
                          placeholder="Please confirm password" ></el-input>
              </el-form-item>
            </el-form>
            <el-button size="large" style="width: 100%; margin-bottom: 20px"
                       type="primary" @click="register">REGISTER
            </el-button>
            <div style="text-align: right; color: grey"><a
              style="color:
            var(--primary-color); text-decoration: none"
                                          href="/login">Login with an
              account</a>
            </div>
          </div>
          </div>
    </div>
  </div>
</template>


<script setup>

import { reactive, ref } from 'vue'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'
const validatePass = (rule, value, callback) => {
  if(!value){
    callback(new Error('Please confirm password'))
  }else if (value !== data.form.password){
    callback(new Error('Passwords do not match!'))
  }else {
    callback()
  }
}
const data =reactive({
  form: {
    username: '',
    password: '',
    confirmPassword:'',
  },
  rules: {
    username:[{required: true, message: 'Please enter username', trigger: 'blur'}],
    password:[{required: true, message: 'Please enter password', trigger: 'blur'}],
    confirmPassword:[{
      validator: validatePass, trigger: 'blur'
    }]
  }
})


const formRef = ref()

const register = () => {
  formRef.value.validate((valid)=>{
      if(valid){

        request.post('/register', data.form).then((res) => {
          if (res.code == '200') {
            ElMessage.success('Registration successful!')
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

<style scoped>
.login-container {
  height: 100vh;
  background-color: var(--login-backg-color);
  background-size: cover;
  background-position: 0 -20px;
  overflow: hidden;
}
.login-box {
  height: 100%;
  width: 50%;
  right: 0;
  position: absolute;
  display: flex;
  align-items: center;

}
</style>
