<template>
  <section class="list-wrap">
    <div class="grid" v-if="filtered.length">
      <ConflictCard
        v-for="c in filtered"
        :key="c.id"
        :conflict="c"
        @delete="$emit('delete', $event)"
        @edit="$emit('edit', $event)"
      />
    </div>

    <div v-else class="empty">No hay conflictos para mostrar.</div>
  </section>
</template>

<script setup>
import { computed } from 'vue'
import ConflictCard from './ConflictCard.vue'

const props = defineProps({
  items: { type: Array, default: () => [] },
  q: { type: String, default: '' },
  statusFilter: { type: String, default: '' }
})

const filtered = computed(() => {
  const term = (props.q || '').trim().toLowerCase()
  return (props.items || []).filter(c => {
    if (props.statusFilter && c.status !== props.statusFilter) return false
    if (!term) return true
    if ((c.name || '').toLowerCase().includes(term)) return true
    if ((c.countryCodes || []).join(',').toLowerCase().includes(term)) return true
    return false
  })
})
</script>

<style scoped>
.list-wrap{ max-width:980px; margin:0 auto; padding:6px }
.grid{ display:grid; grid-template-columns: repeat(auto-fill,minmax(320px,1fr)); gap:12px }
.empty{ color:#6b7280; text-align:center; padding:36px 0; font-size:16px }
</style>