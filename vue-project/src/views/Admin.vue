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
          <el-table-column label="avatar" width="180">
            <template #default="scope">
              <img v-if="scope.row.avatar" :src="scope.row.avatar"  alt=""
                   style="display:block; width: 40px;
              height:
               40px; border-radius: 50%">
            </template>
          </el-table-column>
          <el-table-column prop="name" label="Name" width="180" />
          <el-table-column prop="username" label="Username" width="180"></el-table-column>
          <el-table-column prop="content" label="Content"
                           width="180">
            <template #default="scope">
              <div v-html="scope.row.content"></div>
            </template>
          </el-table-column>

          <el-table-column label="Actions" width="250">
            <template #default="scope">
              <el-button
                type="primary"
                @click="editContent(scope.row)"
              >Edit Content</el-button>

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
               style="margin-right: 50px">
        <el-form-item label="Name" label-width="80px">
          <el-input v-model="data.form.name" autocomplete="off" />
        </el-form-item>

        <el-form-item prop="username" label="Username" label-width="80px">
          <el-input v-model="data.form.username" autocomplete="off" />
        </el-form-item>


      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveEmployees"> Confirm </el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="data.dialogContentVisible" title="Edit Content"
               width="800">
    <div style="padding: 20px">
      <div style="border: 1px solid #ccc; width: 100%">
        <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef"
                 :mode="mode">
        </Toolbar>
        <Editor
          style="height: 500px; overflow-y: hidden;"
          v-model="data.form.content"
          :defaultConfig="editorConfig"
          :mode="mode"
          @onCreated="handleContentCreated"
        />
      </div>
    </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.dialogContentVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveContent"> Confirm </el-button>
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

import '@wangeditor/editor/dist/css/style.css'
import {onBeforeUnmount, shallowRef} from 'vue'
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import { i18nChangeLanguage } from '@wangeditor/editor'

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
    username: null,
    password: null,
    role: null,
    content: null,
  },
  selectedRows: [],
  rules: {
    username: [{ required: true, message: 'Please enter username', trigger: 'blur' }],
  },
  dialogContentVisible:false,
})

const formRef = ref(null)

const loadEmployees = () => {
  request
    .get('/admin/selectPageAdmin', {
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
  request.post('/admin/add', data.form).then((res) => {
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
  request.put('/admin/update', data.form).then((res) => {
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
      request.delete('/admin/deleteById/' + id).then((res) => {
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
        .delete('/admin/deleteBatch', {
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

i18nChangeLanguage('en')

const editContent = (row) => {
  data.dialogContentVisible = true
  data.form.content = row.content
}

// editor content initialization

const baseURl = "http://localhost:9090"
const editorRef = shallowRef(null)
const mode = 'default'
const editorConfig = {MENU_CONF:{}}
editorConfig.MENU_CONF['uploadImage'] = {
  server:baseURl + '/files/wang/upload',
  fileName: 'file',
}

onBeforeUnmount(()=>{
  const editor = editorRef.value
  if (editor == null) {
    return
  }
  editor.destroy()
})

const handleContentCreated = (editor) => {
  editorRef.value = editor
}

const saveContent = () =>{
  data.dialogContentVisible = false
}
</script>
