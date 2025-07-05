<template>
  <div
    style="
      padding: 20px;
      border-radius: 8px;
      background: linear-gradient(to right, var(--primary-color), var(--login-backg-color));
      color: white;
      margin-bottom: 20px;
    "
  >
    <h2 style="margin: 0">👋 Welcome back, {{ data.loginUser.name }}!</h2>
    <p style="margin: 0">Here’s a quick overview of today’s insights and activities.</p>
  </div>
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card
        style="
          background-color: var(--dashboard-backg-color);
          box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
          border-radius: 16px;
          padding: 24px;
          transition: transform 0.3s;
        "
        @mouseenter.native="(e) => (e.currentTarget.style.transform = 'scale(1.03)')"
        @mouseleave.native="(e) => (e.currentTarget.style.transform = 'scale(1)')"
      >
        <div style="display: flex; align-items: center; justify-content: space-between">
          <div>
            <h3 style="color: var(--primary-color); margin-bottom: 6px; font-size: 18px">
              Active Projects
            </h3>
            <p style="font-size: 13px; color: #666; margin-bottom: 12px">Ongoing this month</p>
            <p style="font-size: 38px; font-weight: bold; color: var(--primary-color); margin: 0">
              12
            </p>
          </div>
          <div
            style="
              background: linear-gradient(to bottom right, #48a6a7, #006a71);
              padding: 14px;
              border-radius: 50%;
              box-shadow: 0 0 12px rgba(0, 106, 113, 0.4);
            "
          >
            <el-icon :size="32" style="color: white"><Folder /></el-icon>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card>
        <div style="display: flex; align-items: center; justify-content: space-between">
          <div>
            <h3 style="color: var(--primary-color); margin-bottom: 4px">Pending Tasks</h3>
            <p style="font-size: 14px; color: gray">To be completed today</p>
            <p style="font-size: 28px; font-weight: bold">34</p>
          </div>
          <el-icon :size="36"><Clock /></el-icon>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card>
        <div style="display: flex; align-items: center; justify-content: space-between">
          <div>
            <h3 style="color: var(--primary-color); margin-bottom: 4px">System Health</h3>
            <p style="font-size: 14px; color: gray">Status overview</p>
            <p style="font-size: 28px; font-weight: bold">✅ All good</p>
          </div>
          <el-icon :size="36"><Cpu /></el-icon>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import { reactive } from 'vue'
import { Folder, Clock, Cpu } from '@element-plus/icons-vue'

const data = reactive({
  loginUser: JSON.parse(localStorage.getItem('login-user')),
})
</script>
