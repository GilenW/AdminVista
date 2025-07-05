<template>
  <div>
    <!--    TODO: add login image-->
    <div class="login-container" style="display: flex">
      <div class="login-left">
        <img src="/src/assets/img/LoginBg.svg" style="" />
      </div>
      <div class="login-box">
        <div
          style="padding: 30px; background-color: white; margin-left: 200px; border-radius: 20px"
        >
          <div
            style="
              margin-bottom: 30px;
              font-size: 25px;
              color: var(--primary-color);
              text-align: center;
            "
          >
            Welcome to access management system
          </div>
          <el-form ref="formRef" :rules="data.rules" :model="data.form" style="width: 400px">
            <el-form-item prop="username">
              <el-input
                size="large"
                prefix-icon="User"
                v-model="data.form.username"
                placeholder="Please enter username"
              ></el-input>
            </el-form-item>

            <el-form-item prop="password">
              <el-input
                show-password
                prop="password"
                size="large"
                prefix-icon="Lock"
                v-model="data.form.password"
                placeholder="Please enter password"
              ></el-input>
            </el-form-item>
            <el-form-item prop="role">
              <el-select v-model="data.form.role" placeholder="Please select role">
                <el-option value="ADMIN" label="admin"></el-option>
                <el-option value="EMP" label="employee"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button
            size="large"
            style="width: 100%; margin-bottom: 20px"
            type="primary"
            @click="login"
            >LOGIN
          </el-button>
          <div style="text-align: right; color: grey">
            Don't have an account?
            <a style="color: var(--primary-color); text-decoration: none" href="/register"
              >register</a
            >
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

const data = reactive({
  form: {
    username: '',
    password: '',
    role: 'ADMIN',
  },
  rules: {
    username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
    password: [{ required: true, message: 'Please enter password', trigger: 'blur' }],
  },
})

const formRef = ref()

const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/login', data.form).then((res) => {
        if (res.code == '200') {
          localStorage.setItem('login-user', JSON.stringify(res.data))
          ElMessage.success('Login successful!')
          setTimeout(() => {
            location.href = '/manager/home'
          }, 500)
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
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
.login-left {
  width: 70%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  //margin-left: 10%;
}
</style>
