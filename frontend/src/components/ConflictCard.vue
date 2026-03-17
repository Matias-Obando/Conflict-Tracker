<template>
  <article class="card">
    <div class="media">
      <img :src="imageUrl" :alt="altText" class="flag" />
    </div>

    <div class="content">
      <div class="row-top">
        <h3 class="title">{{ conflict.name }}</h3>
        <time class="date">{{ formattedDate }}</time>
      </div>

      <p class="desc">{{ conflict.description || 'Sin descripción.' }}</p>

      <div class="chips">
        <span v-for="c in conflict.countryCodes || []" :key="c" class="chip">
          <img v-if="flagUrl(c)" :src="flagUrl(c)" :alt="c" class="chip-flag" width="18" height="12" />
          <span class="chip-code">{{ c }}</span>
        </span>
      </div>

      <div class="actions">
        <router-link :to="{ name: 'conflictDetail', params: { id: conflict.id } }" class="link">Ver</router-link>
        <button class="btn" @click="$emit('edit', conflict)">Editar</button>
        <button class="btn danger" @click="$emit('delete', conflict.id)">Eliminar</button>
      </div>
    </div>
  </article>
</template>

<script setup>
import { computed } from 'vue'
import heroImg from '../assets/hero.png'
import { getFlagUrl } from '../utils/flags'

const props = defineProps({ conflict: { type: Object, required: true } })

const formattedDate = computed(() => {
  const d = props.conflict?.startDate || props.conflict?.start_date
  if (!d) return '—'
  try { return new Date(d).toLocaleDateString() } catch { return d }
})

const altText = computed(() => (props.conflict?.name ? `Imagen ${props.conflict.name}` : 'Imagen conflicto'))

const imageUrl = computed(() => {
  const idx = props.conflict?.imageIndex
  if (idx && [1,2,3].includes(Number(idx))) {
    return `/images/war${Number(idx)}.jpg`
  }

  const id = props.conflict?.id
  if (id !== undefined && id !== null) {
    const n = (Math.abs(Number(id)) % 3) + 1
    return `/images/war${n}.jpg`
  }

  return heroImg
})

const flagUrl = (code) => getFlagUrl(code, '18x12')
</script>

<style scoped>
.card{ display:flex; gap:16px; align-items:flex-start; padding:14px; border-radius:12px; background:#fff; border:1px solid #e6e7ee; box-shadow:0 8px 20px rgba(15,23,42,0.04) }

.media{
  width:120px;
  height:80px;
  display:flex;
  align-items:center;
  justify-content:center;
  background:#f8fafc;
  border-radius:8px;
  padding:6px;
  border:1px solid rgba(2,6,23,0.03);
  box-shadow: inset 0 1px 0 rgba(255,255,255,0.6);
}

.flag{
  max-width:100%;
  max-height:100%;
  object-fit:contain;
  display:block;
  border-radius:4px;
}

.content{ flex:1; display:flex; flex-direction:column; gap:8px }
.row-top{ display:flex; justify-content:space-between; align-items:flex-start }
.title{ margin:0; font-size:16px; color:#0f172a }
.date{ color:#6b7280; font-size:13px }
.desc{ margin:0; color:#374151; min-height:36px; line-height:1.35 }
.chips{ display:flex; gap:8px; margin-top:4px; flex-wrap:wrap }


.chip{
  background:#eef6ff;
  color:#073b79;
  padding:6px 8px;
  border-radius:999px;
  font-weight:600;
  font-size:13px;
  display:inline-flex;
  align-items:center;
  gap:8px;
}
.chip-flag{ border-radius:2px; display:inline-block }
.chip-code{ line-height:1 }

.actions{ margin-top:8px; display:flex; gap:8px; align-items:center }
.link{ color:#2563eb; text-decoration:underline; font-weight:700 }
.btn{ background:white; border:1px solid #e6e7ee; padding:6px 10px; border-radius:8px; cursor:pointer }
.btn.danger{ color:#b91c1c; border-color:#fca5a5; }
</style>