<template xmlns="http://www.w3.org/1999/html">
  <el-card style="width:700px;padding: 40px 20px; ">


<!--    personal information-->
      <el-form ref="formRef" :rules="data.rules" :model="data.form"
               style="">
        <!--    avatar-->
        <div  style="width: 100%; display: flex;
        justify-content: center; margin-bottom: 20px">
          <el-form-item>
            <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/files/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
            >
              <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>

          </el-form-item>
        </div>

        <el-form-item label="Name" label-width="80px" style="">
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
    avatar: '',
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

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.form.avatar = res.data
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

<style scoped>
.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 50%;
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
  width: 120px;
  height: 120px;
  text-align: center;
}
</style>
