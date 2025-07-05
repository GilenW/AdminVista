<template>
  <div>
    <div>
      <el-card style="">
        <el-input
          style="width: 240px; margin-right: 10px"
          v-model="data.searchName"
          placeholder="Search for title..."
          prefix-icon="Search"
        ></el-input>
        <el-button type="primary" @click="loadArticles"> FIND</el-button>
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
          <el-table-column label="Cover" width="180">
            <template #default="scope">
              <el-image
                v-if="scope.row.img"
                :src="scope.row.img"
                :preview-src-list="[scope.row.img]"
                preview-teleported
                style="display: block; width: 100px; height: 60px"
              >
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="title" label="Title" width="180" />
          <el-table-column
            prop="description"
            label="Description"
            width="180"
            show-overflow-tooltip
          ></el-table-column>

          <!--          <el-table-column prop="content" label="Content"-->
          <!--                           width="180" show-overflow-tooltip>-->
          <!--            <template #default="scope">-->
          <!--              <div v-html="scope.row.content" style="max-height: 80px; overflow: auto; white-space: normal;"></div>-->
          <!--            </template>-->
          <!--          </el-table-column>-->
          <el-table-column prop="content" label="Content" width="180" show-overflow-tooltip>
            <template #default="scope">
              <el-button type="primary" @click="viewContent(scope.row.content)"
                >SEE CONTENT</el-button
              >
            </template>
          </el-table-column>
          <el-table-column prop="time" label="Time" width="180"></el-table-column>
          <el-table-column label="Actions" width="250">
            <template #default="scope">
              <el-button
                type="primary"
                @click="editContent(scope.row)"
                :icon="Edit"
                circle
              ></el-button>
              <el-button
                type="danger"
                :icon="Delete"
                circle
                @click="deleteArticles(scope.row.id)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <div style="margin-top: 10px">
        <el-pagination
          @change="loadArticles"
          v-model:current-page="data.tableProperty.currentPage"
          v-model:page-size="data.tableProperty.pageSize"
          :page-sizes="[5, 10, 15, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.tableProperty.total"
        />
      </div>
    </div>

    <el-dialog v-model="data.dialogContentVisible" title="Article" width="800">
      <div>
        <el-form ref="formRef" :model="data.form" style="margin-right: 50px" label-position="left">
          <el-form-item label="Title" label-width="80px">
            <el-input v-model="data.form.title" autocomplete="off" />
          </el-form-item>

          <el-form-item prop="description" label="Description" label-width="80px">
            <el-input type="textarea" v-model="data.form.description" autocomplete="off" />
          </el-form-item>

          <el-form-item label="cover">
            <el-upload
              action="http://localhost:9090/files/upload"
              list-type="picture"
              :on-success="handleCoverSuccess"
            >
              <el-button type="primary"> Upload Cover </el-button>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <div style="padding: 20px">
        <div style="border: 1px solid #ccc; width: 100%">
          <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :mode="mode">
          </Toolbar>
          <Editor
            style="height: 500px; overflow-y: hidden"
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
          <el-button type="primary" @click="saveArticles"> Confirm </el-button>
        </div>
      </template>
    </el-dialog>

    <!--    view content dialog-->
    <el-dialog
      title=""
      v-model="data.viewContentVisible"
      width="50%"
      :close-on-click-modal="false"
      destroy-on-close
    >
      <div class="editor-content-view" style="padding: 20px" v-html="data.form.content"></div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.viewContentVisible = false">CLOSE </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { computed, nextTick, reactive, ref } from 'vue'
import request from '@/utils/request.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit } from '@element-plus/icons-vue'

import '@wangeditor/editor/dist/css/style.css'
import { onBeforeUnmount, shallowRef } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
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
    id: null,
    title: null,
    description: null,
    content: null,
    img: null,
    time: null,
  },
  selectedRows: [],
  dialogContentVisible: false,
  viewContentVisible: false,
})

const formRef = ref(null)

const loadArticles = () => {
  request
    .get('/articles/selectPageArticles', {
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
loadArticles()

const reset = () => {
  data.searchName = null
  loadArticles()
}

const loadForm = () => {
  nextTick(() => {
    data.form.id = null
    data.form.title = ''
    data.form.description = ''
    data.form.content = ''
    data.form.img = ''
    data.form.time = null
    data.dialogContentVisible = true
  })
}

const saveArticles = () => {
  data.dialogContentVisible = false
  data.form.id ? updateArticles() : addArticles()
}

const addArticles = () => {
  request.post('/articles/add', data.form).then((res) => {
    if (res.code == '200') {
      ElMessage.success('Successfully added!')
      data.formVisible = false
      loadArticles()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const updateArticles = () => {
  request.put('/articles/update', data.form).then((res) => {
    if (res.code == '200') {
      ElMessage.success('Successfully updated!')
      data.formVisible = false
      loadArticles()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdateArticles = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const deleteArticles = (id) => {
  ElMessageBox.confirm('Are you sure you want to delete this article?')
    .then(() => {
      request.delete('/articles/deleteById/' + id).then((res) => {
        if (res.code == '200') {
          ElMessage.success('Successfully deleted!')
          loadArticles()
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
        .delete('/articles/deleteBatch', {
          data: data.selectedRows,
        })
        .then((res) => {
          if (res.code == '200') {
            ElMessage.success('Successfully deleted!')
            loadArticles()
          } else {
            ElMessage.error(res.msg)
          }
        })
    })
    .catch(() => {})
}

i18nChangeLanguage('en')

const editContent = (row) => {
  nextTick(() => {
    data.form = JSON.parse(JSON.stringify(row))
  })
  data.dialogContentVisible = true
}

// editor content initialization

const baseURl = 'http://localhost:9090'
const editorRef = shallowRef(null)
const mode = 'default'
const editorConfig = { MENU_CONF: {} }
editorConfig.MENU_CONF['uploadImage'] = {
  server: baseURl + '/files/wang/upload',
  fileName: 'file',
}

onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) {
    return
  }
  editor.destroy()
})

const handleCoverSuccess = (res) => {
  data.form.img = res.data
}

const handleContentCreated = (editor) => {
  editorRef.value = editor
}

const viewContent = (content) => {
  data.form.content = content
  data.viewContentVisible = true
}
</script>
