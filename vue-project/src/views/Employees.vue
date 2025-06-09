<template>
  <div>
    <div>
      <el-card style="">
        <el-input
          style="width: 240px; margin-right: 10px"
          v-model="data.name"
          placeholder="Search for something..."
          prefix-icon="Search"
        ></el-input>
        <el-button type="primary"> FIND</el-button>
        <el-button type="warning">RESET </el-button>
      </el-card>
    </div>

    <div>
      <el-card>
        <el-button type="primary">NEW</el-button>
        <el-button type="warning">MULTIPLE DELETE</el-button>
        <el-button type="primary">IMPORT</el-button>
        <el-button type="success">EXPORT</el-button>
      </el-card>
    </div>

    <!-- table -->
    <div>
      <el-card>
        <el-table :data="data.tableData" stripe>
          <el-table-column prop="name" label="Name" width="180" />
          <el-table-column prop="age" label="Age" width="180" />
          <el-table-column prop="position" label="Position" show-overflow-tooltip />
        </el-table>
      </el-card>
      <div style="margin-top: 10px">
        <el-pagination
          @change="loadEmployees"
          v-model:current-page="data.tableProperty.currentPage"
          v-model:page-size="data.tableProperty.pageSize"
          :page-sizes="[5,10,15,20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.tableProperty.total"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive } from 'vue'
import request from '@/utils/request.js'

const data = reactive({
  name: null,
  tableData: [],
  tableProperty: {
    currentPage: 1,
    pageSize: 5,
    total: 0,
  },
  dialogTableVisible: false,
  editRow: null,
})
const handleSizeChange = (value) => {
  data.tableProperty.size = value
}
const handleCurrentChange = (value) => {
  data.tableProperty.currentPage = value
}
const paginatedData = computed(() => {
  const start = (data.tableProperty.currentPage - 1) * data.tableProperty.pageSize
  const end = start + data.tableProperty.pageSize
  return data.tableData.slice(start, end)
})

const loadEmployees = () => {
  request.get('/employees/selectPageEmployees', {
    params: {
      pageNum: data.tableProperty.currentPage,
      pageSize: data.tableProperty.pageSize,
    }
  }).then((res) => {
    data.tableData = res.data.list;
    data.tableProperty.total = res.data.total;

  })
}
loadEmployees()

</script>
