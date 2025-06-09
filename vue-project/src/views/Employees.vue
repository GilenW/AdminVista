<template>
  <div>
    <div>
      <el-card style="">
        <el-input
          style="width: 240px; margin-right: 10px"
          v-model="data.searchName"
          placeholder="Search for something..."
          prefix-icon="Search"
        ></el-input>
        <el-button type="primary" @click="loadEmployees"> FIND</el-button>
        <el-button type="warning" @click="reset">RESET </el-button>
      </el-card>
    </div>

    <div>
      <el-card>
        <el-button type="primary" @click="loadForm">NEW</el-button>
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

    <el-dialog v-model="data.formVisible" title="Add new employee" width="500">
      <el-form :model="data.form" style="margin-right: 50px">
        <el-form-item label="Name" label-width="80px">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Age" label-width="80px">
          <el-input-number :min="18" v-model="data.form.age"
                           autocomplete="off" />
        </el-form-item>
        <el-form-item label="Position" label-width="80px">
          <el-input v-model="data.form.position" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveEmployees">
            Confirm
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, reactive } from 'vue'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus'

const data = reactive({
  searchName: null,
  tableData: [],
  tableProperty: {
    currentPage: 1,
    pageSize: 5,
    total: 0,
  },
  dialogTableVisible: false,
  editRow: null,
  formVisible:false,
  form:{
    name:null,
    age: null,
    position: null,

  }
})

const loadEmployees = () => {
  request.get('/employees/selectPageEmployees', {
    params: {
      pageNum: data.tableProperty.currentPage,
      pageSize: data.tableProperty.pageSize,
      searchName: data.searchName,
    }
  }).then((res) => {
    data.tableData = res.data.list;
    data.tableProperty.total = res.data.total;

  })
}
loadEmployees()

const reset = () =>{
  data.searchName = null
  loadEmployees()
}

const loadForm = () => {
  data.formVisible = true
  data.form = {}
}

const saveEmployees = () => {
  request.post('/employees/add', data.form).then((res) => {
    if(res.code == '200'){
      ElMessage.success("Successfully added!")
      data.formVisible = false
      loadEmployees()
    }else{
      ElMessage.error(res.msg)
    }
  })
}
</script>
