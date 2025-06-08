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
        <el-table :data="paginatedData" stripe>
          <el-table-column prop="date" label="Date" width="180" />
          <el-table-column prop="name" label="Name" width="180" />
          <el-table-column prop="address" label="Address" />
        </el-table>
      </el-card>
      <div style="margin-top: 10px">
        <el-pagination
          v-model:current-page="data.tableProperty.currentPage"
          v-model:page-size="data.tableProperty.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.tableData.length"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, reactive } from 'vue'

const data = reactive({
  name: null,
  tableData: [
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
  tableProperty: {
    currentPage: 1,
    pageSize: 2,
    size: null,
    disabled: false,
    background: null,
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
</script>
