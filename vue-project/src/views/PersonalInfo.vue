<template xmlns="http://www.w3.org/1999/html">
  <el-card style="width:700px">
      <el-form ref="formRef" :rules="data.rules" :model="data.form"
               style="margin-right: 50px">
        <el-form-item label="Name" label-width="80px">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Age" label-width="80px">
          <el-input-number :min="18" v-model="data.form.age" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="username" label="Username" label-width="80px">
          <el-input v-model="data.form.username" :disabled="data.loginUser.role === 'ADMIN'" autocomplete="off" />
        </el-form-item>
      </el-form>
  </el-card>
  <el-button @click="updateUser" type="primary"
             style="margin-top:20px; padding:
  20px
  30px"
  >Update Information</el-button>
</template>

<script setup>
import { reactive, ref } from 'vue'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'

const emit = defineEmits(['updateUser'])

const data = reactive({
  loginUser: JSON.parse(localStorage.getItem('login-user')),
  form: {
    name: '',
    age: 0,
    position: '',
    username: '',
    role: '',
  },
  rules: {
    username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
  },
})
const formRef = ref(null)

if(data.loginUser.role === 'EMP'){
  request.get('/employees/selectByEmployeeId/' + data.loginUser.id).then(res =>
  {
    data.form = res.data
  })
}else{
  data.form = data.loginUser
}

const updateUser = () =>{
  console.log(data.form)
  if(data.form.role === 'EMP'){

    request.put("/employees/update", data.form).then(res =>{
      if(res.code == 200){

        ElMessage.success("Update Successfully!")
      }else{
        ElMessage.error(res.msg)
      }
    })
  }else{
    request.put("/admin/update", data.form).then(res =>{
      console.log(data.form)
      if(res.code == 200){
        ElMessage.success("Update Successfully!")
      }else{
        ElMessage.error(res.msg)
      }
    })
  }
  localStorage.setItem('login-user', JSON.stringify(data.form))
  emit('updateUser')
}



</script>
