import axios from 'axios'
import {ElMessage} from 'element-plus';
import router from "@/router/index.js"

const request = axios.create({
  baseURL:'http://localhost:9090',
  timeout:30000
})

request.interceptors.request.use(config => {
  config.headers['Content-Type'] = 'application/json;charset=UTF-8';
  return config;
}, error => {
  return Promise.reject(error);
})

request.interceptors.response.use(
  response => {
    let res = response.data;
    if(typeof res === 'string'){
      res = res ? JSON.parse(res) : res;
    }
    return res;

  },
  error => {
    if(error.response.status === 404){ElMessage.error('Could not find request');
    }else if (error.response.status === 500){
      ElMessage.error('System Error, please check console');
    }else{
      ElMessage.error(error.message);
    }
    return Promise.reject(error);
  }
)

export default request;
