<template>
  <li>
    <label :class="{done:task.completed}">
      <input type="checkbox" :checked="task.completed"
             @change="$emit('toggle', task.id)"/>
      {{task.text.value}}
    </label>
    <button @click="$emit('remove',task.id)">❌</button>
  </li>
</template>


<script setup>
defineProps({
  task:Object
})

defineEmits(['toggle', 'removeTask'])
</script>

<style scoped>
.done{
  text-decoration: line-through;
  color:grey;
}
li{
  display: flex;
  justify-content: space-between;
  margin: 5px 0;
}
</style>

