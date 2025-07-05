<template>
  <div>
    <div style="display: flex; grid-gap: 10px">
      <el-card id="main" style="flex: 1; width: 600px; height: 600px; padding: 50px"> </el-card>
      <el-card id="barChart" style="flex: 2; width: 600px; height: 600px; padding: 50px"></el-card>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue'
import * as echarts from 'echarts'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'

const data = reactive({})

const option = {
  title: {
    left: 'center',
  },
  tooltip: {
    trigger: 'item',
  },
  legend: {
    orient: 'vertical',
    left: 'left',
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      padAngle: 5,
      itemStyle: {
        borderRadius: 10,
      },
      label: {
        show: false,
        position: 'center',
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold',
        },
      },
      labelLine: {
        show: false,
      },
      data: [
        { value: 1048, name: 'Search Engine' },
        { value: 735, name: 'Direct' },
        { value: 580, name: 'Email' },
        { value: 484, name: 'Union Ads' },
        { value: 300, name: 'Video Ads' },
      ],
    },
  ],
}

onMounted(() => {
  const myChart = echarts.init(document.getElementById('main'))
  request.get('/pieChart').then((res) => {
    console.log(res)
    if (res.code == '200') {
      const newData = Object.entries(res.data).map(([key, value]) => ({
        value: value,
        name: key,
      }))
      option.series[0].data = newData
      myChart.setOption(option)
    } else {
      ElMessage.error(err.msg)
    }
  })

  // --- Bar chart setup below ---
  const barChart = echarts.init(document.getElementById('barChart'))

  const xAxisData = []
  const data1 = []
  const data2 = []
  for (let i = 0; i < 20; i++) {
    xAxisData.push('Dept ' + i)
    data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5)
    data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5)
  }

  const barOption = {
    title: {
      text: 'Employee Productivity by Department',
      left: 'center',
      top: 30,
    },
    legend: {
      data: ['Efficiency', 'Utilization'],
    },
    toolbox: {
      feature: {
        magicType: {
          type: ['stack'],
        },
        dataView: {},
        saveAsImage: {
          pixelRatio: 2,
        },
      },
    },
    tooltip: {},
    xAxis: {
      data: xAxisData,
      splitLine: {
        show: false,
      },
    },
    yAxis: {},
    series: [
      {
        name: 'Efficiency',
        type: 'bar',
        data: data1,
        emphasis: {
          focus: 'series',
        },
        animationDelay: function (idx) {
          return idx * 10
        },
      },
      {
        name: 'Utilization',
        type: 'bar',
        data: data2,
        emphasis: {
          focus: 'series',
        },
        animationDelay: function (idx) {
          return idx * 10 + 100
        },
      },
    ],
    animationEasing: 'elasticOut',
    animationDelayUpdate: function (idx) {
      return idx * 5
    },
  }

  barChart.setOption(barOption)
})
</script>
