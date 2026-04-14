<template>
  <div class="searchbar">
    <input v-model="term" @input="update" placeholder="Buscar título, país o texto..." />
    <select v-model="status" @change="update">
      <option value="">Todos</option>
      <option value="ACTIVE">Activo</option>
      <option value="ENDED">Finalizado</option>
    </select>
    <button class="btn" @click="$emit('refresh')">Refrescar</button>
  </div>
</template>

<script setup>
import { ref, watch, toRefs } from 'vue'
const props = defineProps({ modelValue: [String, Object], q: String, status: String })
const emit = defineEmits(['update:q','update:status','refresh'])
const term = ref('')
const status = ref('')

watch(term, v => emit('update:q', v))
watch(status, v => emit('update:status', v))

if (typeof props.q === 'string') term.value = props.q
if (typeof props.status === 'string') status.value = props.status

function update(){ }
</script>

<style scoped>
.searchbar{ display:flex; gap:10px; align-items:center }
.searchbar input{ padding:10px 12px; border-radius:10px; border:1px solid rgba(15,23,42,0.06); min-width:260px }
.searchbar select{ padding:8px 10px; border-radius:8px; border:1px solid rgba(15,23,42,0.06) }
.btn{ padding:8px 12px; border-radius:8px; background:transparent; border:1px solid rgba(15,23,42,0.06) }
</style>