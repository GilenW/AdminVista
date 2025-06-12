

<template>
  <H1>TODO List</H1>

  <ul>
    <TodoItem
      v-for="task in tasks"
      :key="task.id"
      :task="task"
      @toggle="toggleTask"
      @remove="removeTask"
    ></TodoItem>
  </ul>

  <form @submit.prevent="addTask">
    <input v-model="newTask.value" placeholder="Add a task..."/>
    <button type="submit">Add</button>
  </form>

  <!--  <div>-->
  <!--    <span style="color: red" v-if="warning">Warning!</span>-->
  <!--  </div>-->


  <!--  v for practice -->
  <!--  <div>-->
  <!--    <div style="width: 300px; height: 300px; text-align: center; font-size:-->
  <!--    20px; background-color: aqua" v-for="item in existingTasks.mockTasks">{{item}}</div>-->
  <!--  </div>-->

  <!--  vue select module-->
  <!--<select>-->
  <!--  <option v-for="item in existingTasks.mockTasks">{{item}}</option>-->
  <!--</select>-->
  <!--  -->

</template>

<script setup>

import { onMounted, reactive, ref, watch } from 'vue'
import TodoItem from '@/components/TodoItem.vue'

const newTask = ref({});
const tasks = ref([])
const addTask = () => {
  if(newTask.value.length === 0) return
  tasks.value.push({
    id:Date.now(),
    text:newTask.value,
    completed: false
  })
  newTask.value = '';
}

const toggleTask = (id) =>{
  const task = tasks.value.find(t => t.id === id)
  if(task) task.completed = !task.completed
}
const removeTask = (id) =>{
  tasks.value = tasks.value.filter(t => t.id !== id)
}

onMounted(() => {
  const saved = localStorage.getItem('tasks');
  if(saved) tasks.value = JSON.parse(saved);
})

watch(tasks, (newVal)=>{
  localStorage.setItem('tasks', JSON.stringify(newVal));
}, {deep: true})

const warning = false;

const existingTasks = reactive({
  mockTasks: ["task1", "task2"],
})




</script>
