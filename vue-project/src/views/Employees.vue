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
        <el-button type="warning" @click="deleteSelectedRow">DELETE SELECTED ROWS</el-button>
        <el-button type="primary">IMPORT</el-button>
        <el-button type="success">EXPORT</el-button>
      </el-card>
    </div>

    <!-- table -->
    <div>
      <el-card>
        <el-table :data="data.tableData" stripe @selection-change="selectionChange">
          <el-table-column type="selection" width="80"></el-table-column>
          <el-table-column label="avatar">
            <template #default="scope">
              <img v-if="scope.row.avatar"  :src="scope.row.avatar"  alt=""
                   style="display:block; width: 40px;
              height:
               40px; border-radius: 50%">
            </template>
          </el-table-column>
          <el-table-column prop="name" label="Name" width="180" />
          <el-table-column prop="age" label="Age" width="180" />
          <el-table-column prop="position" label="Position" show-overflow-tooltip width="180" />
          <el-table-column prop="username" label="Username" width="180"></el-table-column>
          <el-table-column prop="role" label="Role" width="180"></el-table-column>

          <el-table-column label="Actions" width="180">
            <template #default="scope">
              <el-button
                type="primary"
                @click="handleUpdateEmployees(scope.row)"
                :icon="Edit"
                circle
              ></el-button>
              <el-button
                type="danger"
                :icon="Delete"
                circle
                @click="deleteEmployees(scope.row.id)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <div style="margin-top: 10px">
        <el-pagination
          @change="loadEmployees"
          v-model:current-page="data.tableProperty.currentPage"
          v-model:page-size="data.tableProperty.pageSize"
          :page-sizes="[5, 10, 15, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.tableProperty.total"
        />
      </div>
    </div>

    <el-dialog v-model="data.formVisible" title="Employee Information"
               width="500" destroy-on-close>
      <el-form ref="formRef" :rules="data.rules" :model="data.form"
               style="margin-right: 50px" label-position="left">
        <el-form-item label="Name" label-width="80px">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Age" label-width="80px">
          <el-input-number :min="18" v-model="data.form.age" autocomplete="off" />
        </el-form-item>
        <el-form-item label="Position" label-width="80px">
          <el-input v-model="data.form.position" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="username" label="Username" label-width="80px">
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item prop="role" label="Role" label-width="80px">
          <el-input v-model="data.form.role" autocomplete="off" />
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveEmployees"> Confirm </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import request from '@/utils/request.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit } from '@element-plus/icons-vue'

const data = reactive({
  searchName: null,
  tableData: [],
  tableProperty: {
    currentPage: 1,
    pageSize: 10,
    total: 0,
  },
  dialogTableVisible: false,
  editRow: null,
  formVisible: false,
  form: {
    name: null,
    age: null,
    position: null,
    username: null,
    password: null,
    role: null,
  },
  selectedRows: [],
  rules: {
    username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
    role: [{ required: true, message: 'Please enter role', trigger: 'blur' }],
  },
})

const formRef = ref(null)

const loadEmployees = () => {
  request
    .get('/employees/selectPageEmployees', {
      params: {
        pageNum: data.tableProperty.currentPage,
        pageSize: data.tableProperty.pageSize,
        searchName: data.searchName,
      },
    })
    .then((res) => {
      data.tableData = res.data.list
      data.tableProperty.total = res.data.total
    })
}
loadEmployees()

const reset = () => {
  data.searchName = null
  loadEmployees()
}

const loadForm = () => {
  data.formVisible = true
  data.form = {}
}

const saveEmployees = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.id ? updateEmployees() : addEmployees()
    }
  })
}

const addEmployees = () => {
  request.post('/employees/add', data.form).then((res) => {
    if (res.code == '200') {
      ElMessage.success('Successfully added!')
      data.formVisible = false
      loadEmployees()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const updateEmployees = () => {
  request.put('/employees/update', data.form).then((res) => {
    if (res.code == '200') {
      ElMessage.success('Successfully updated!')
      data.formVisible = false
      loadEmployees()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdateEmployees = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const deleteEmployees = (id) => {
  ElMessageBox.confirm('Are you sure you want to delete this employee?')
    .then(() => {
      request.delete('/employees/deleteById/' + id).then((res) => {
        if (res.code == '200') {
          ElMessage.success('Successfully deleted!')
          loadEmployees()
        } else {
          ElMessage.error(res.msg)
        }
      })
    })
    .catch(() => {})
}

const selectionChange = (rows) => {
  data.selectedRows = rows.map((row) => row.id)
}

const deleteSelectedRow = () => {
  if (data.selectedRows.length == 0) {
    ElMessage.warning('No selected rows found.')
    return
  }
  ElMessageBox.confirm('Are you sure you want to delete this employee?')
    .then(() => {
      request
        .delete('/employees/deleteBatch', {
          data: data.selectedRows,
        })
        .then((res) => {
          if (res.code == '200') {
            ElMessage.success('Successfully deleted!')
            loadEmployees()
          } else {
            ElMessage.error(res.msg)
          }
        })
    })
    .catch(() => {})
}
</script>
