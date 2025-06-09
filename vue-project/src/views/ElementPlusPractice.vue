<template>
  <div style="margin-bottom: 20px">
    <div style="margin-bottom: 20px">
      <RouterLink to="/router-practice">router to Router Practice | </RouterLink>

      <a href="/router-practice">A href to Router pratice page</a>
    </div>
    <div style="margin-bottom: 20px">
      <!--    or route through function in script-->
      <el-button type="primary" @click="router.push('/router-practice')">Push route</el-button>
    </div>

    <!--    repalce cannot go back to previous page-->
    <el-button type="primary" @click="router.replace('/router-practice')">Replace route</el-button>
  </div>

  <!--  pass parameter in the router-->
  <div style="margin-bottom: 20px">
    <el-button type="primary" @click="router.push('router-practice?id=1')"
      >retrieve parameter method 1
    </el-button>
  </div>

  <div style="margin-bottom: 20px">
    <el-button
      type="primary"
      @click="router.push({ path: '/router-practice', query: { id: 2, desc: 'hello' } })"
    >
      retrieve parameter method 2
    </el-button>
  </div>
  <div style="margin-bottom: 20px">
    <el-button @click="luckIncrease" text :bg="false" :style="{ color: 'purple' }"
      >Feeling Lucky</el-button
    >
  </div>

  <div style="margin: 30px">
    <el-icon :size="20">
      <Edit />
    </el-icon>

    <span style="margin-left: 30px">
      <el-icon :size="30" color="#666" style="top: 4px"> <View /> </el-icon> 100
    </span>

    <el-button type="danger" :icon="Delete" circle></el-button>
  </div>

  <!--  04 element plus component-->
  <div>
    <el-input
      v-model="data.input"
      style="width: 240px"
      placeholder="Please
    input
    something..."
      prefix-icon="Search"
    />
    {{ data.input }}
    <el-input style="width: 200px" suffix-icon="Calendar"></el-input>
    <el-input type="textarea" style="width: 200px" v-model="data.long_search"></el-input>
  </div>

  <!--  select element -->
  <el-select
    v-model="data.value"
    placeholder="Select"
    size="large"
    style="width: 200px"
    clearable
    multiple
  >
    <el-option v-for="item in data.options_data" :key="item" :label="item" :value="item">
    </el-option>
  </el-select>

  <!--  radio-->
  <div>
    <el-radio-group v-model="data.radio1">
      <el-radio-button value="Value 1">Option 1</el-radio-button>
      <el-radio-button value="Value 2">Option 2</el-radio-button>
    </el-radio-group>
    radio option: {{ data.radio1 }}
  </div>

  <!--  checkbox group-->
  <div>
    <el-checkbox-group v-model="data.checkedList">
      <el-checkbox
        v-for="item in data.options_data"
        :key="item"
        :label="item"
        :value="item"
      ></el-checkbox>
    </el-checkbox-group>
    {{ data.checkedList }}
  </div>

  <!--  data picker-->
  <div>
    <el-date-picker
      v-model="data.date"
      type="date"
      placeholder="pick a day"
      :size="'default'"
      value-format="YYYY-MM-DD"
    >
    </el-date-picker>
    {{ data.date }}
  </div>

  <!--  table-->

  <div>
    <el-table :data="paginatedData" style="width: 80%" stripe>
      <el-table-column prop="date" label="Date" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="address" label="Address" />
      <el-table-column>
        <template #default="scope">
          <el-button type="primary" @click="editRow(scope.row)">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="demo-pagination-block">
      <el-pagination
        v-model:current-page="data.tableProperty.currentPage"
        v-model:page-size="data.tableProperty.pageSize"
        :page-sizes="[2, 3, 4]"
        :size="data.tableProperty.size"
        :disabled="data.tableProperty.disabled"
        :background="data.tableProperty.background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="data.table.length"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>

  <!--  dialog-->
  <div>
    <el-dialog v-model="data.dialogTableVisible" title="Edit Row" width="800">
      {{ data.editRow }}
    </el-dialog>
  </div>
</template>

<script setup>
import { Edit, Delete, View, Search } from '@element-plus/icons-vue'
import { computed, reactive } from 'vue'
import router from '@/router/index.js'
import request from '@/utils/request.js'

const luckIncrease = () => {
  alert('Lucky+1')
}

const handleSizeChange = (value) => {
  data.tableProperty.size = value
}
const handleCurrentChange = (value) => {
  data.tableProperty.currentPage = value
}

const data = reactive({
  input: '',
  long_search: '',
  options_data: ['apple', 'banana', 'orange'],
  radio1: '',
  checkedList: [],
  date: '',
  tableProperty: {
    currentPage: 1,
    pageSize: 2,
    size: null,
    disabled: false,
    background: null,
  },
  table: [
    {
      date: '2021-05-01',
      name: 'Apple',
      address: 'Apple',
    },
    {
      date: '2021-05-01',
      name: 'Ban',
      address: 'Apple',
    },
    {
      date: '2021-05-01',
      name: 'Orange',
      address: 'Apple',
    },
    {
      date: '2021-05-01',
      name: 'Watermelon',
      address: 'Apple',
    },
  ],
  dialogTableVisible: false,
  editRow: null,
})

const paginatedData = computed(() => {
  const start = (data.tableProperty.currentPage - 1) * data.tableProperty.pageSize
  const end = start + data.tableProperty.pageSize
  return data.table.slice(start, end)
})

const editRow = (row) => {
  data.dialogTableVisible = true
  data.editRow = row
}
</script>
