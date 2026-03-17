<template>
  <div class="card filters" style="display:flex;gap:8px;align-items:center;">
    <input v-model="localSearch" @input="update" placeholder="Buscar título o país..." />
    <select v-model="status" @change="update">
      <option value="">Todos</option>
      <option value="active">Activos</option>
      <option value="ended">Finalizados</option>
    </select>
    <div class="small">Resultados: {{count}}</div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
const props = defineProps({
  modelValue: { type: String, default: '' },
  initialStatus: { type: String, default: '' },
  count: { type: Number, default: 0 }
})
const emit = defineEmits(['update:modelValue', 'update:status'])

const localSearch = ref(props.modelValue)
const status = ref(props.initialStatus)

function update() {
  emit('update:modelValue', localSearch.value)
  emit('update:status', status.value)
}

watch(() => props.modelValue, v => { localSearch.value = v })
watch(() => props.initialStatus, v => { status.value = v })
</script>

<style scoped>
.filters input{ flex:1; padding:8px; border:1px solid var(--border,#e5e7eb); border-radius:6px }
.filters select{ padding:8px; border-radius:6px }
.small{ font-size:13px; color:var(--muted,#6b7280) }
</style>