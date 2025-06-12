<template>

  <div >
    <div style="display: flex; grid-gap: 10px">
      <el-card id="main"
               style="flex:1; width: 600px; height: 400px;padding: 50px">
      </el-card>
<!--      <el-card id="main"-->
<!--               style="flex:1; width: 600px; height: 400px;padding: 50px">-->
<!--      </el-card>-->

    </div>


  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import  * as echarts from 'echarts'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'

const data =  reactive({

})

const option = {
  title: {
    text: 'Positions',
    subtext: 'Fake Data',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

onMounted(() => {
  const myChart = echarts.init(document.getElementById('main'));
  request.get("/pieChart").then(res => {
    console.log(res)
    if (res.code == "200") {
      const newData = Object.entries(res.data).map(([key, value]) => ({
        value: value,
        name: key
      }));
      option.series[0].data = newData;
      myChart.setOption(option);

    } else{
    ElMessage.error(err.msg);

  }
  })

})



</script>
