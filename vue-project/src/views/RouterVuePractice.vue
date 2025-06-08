<template>
  <div>Hello! Router Practice, this page does not need to refresh through router</div>

  <div>query parameter {{ id }}</div>
  <div>description {{ desc }}</div>

  <el-button @click="router.push('/router-practice/router-child1')"> router child 1 </el-button>
</template>

<script setup>
import router from '@/router/index.js'

const id = router.currentRoute.value.query.id
const desc = router.currentRoute.value.query.desc
</script>
