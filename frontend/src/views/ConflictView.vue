<template>
  <section class="conflict-detail">
    <div class="panel">
      <nav class="crumbs"><router-link to="/">Home</router-link> • <router-link to="/conflicts">Conflictos</router-link> • Detalle</nav>

      <div class="panel-body detail">
        <div class="left">
          <div class="thumb-large">
            <img :src="imageFor(conflict)" alt="Imagen conflicto" />
          </div>
        </div>

        <div class="right">
          <h1>{{ conflict.name || '—' }}</h1>
          <p class="excerpt">{{ conflict.description }}</p>

          <div class="meta">
            <div class="meta-row">
              <strong>Países:</strong>
              <template v-if="(conflict.countryCodes || []).length">
                <span v-for="code in (conflict.countryCodes || [])" :key="code" class="meta-country">
                  <img v-if="flagUrl(code)" :src="flagUrl(code, '24x18')" :alt="code" class="meta-flag" width="22" height="14" />
                  <span class="meta-code">{{ code }}</span>
                </span>
              </template>
              <span v-else class="no-countries"> — </span>
            </div>

            <div><strong>Inicio:</strong> {{ formatDate(conflict.startDate || conflict.start_date) }}</div>
            <div><strong>Estado:</strong> {{ conflict.status || '—' }}</div>
          
          </div>

          <div class="actions">
            <button class="btn" @click="goEdit">Editar</button>
            <button class="btn danger" @click="requestDelete">Eliminar</button>
            <router-link to="/conflicts" class="link">Volver</router-link>
          </div>
        </div>
      </div>
    </div>

    <ConfirmModal
      :visible="confirmVisible"
      title="Eliminar conflicto"
      :message="confirmMessage"
      @confirm="doDelete"
      @cancel="closeConfirm"
      @update:visible="confirmVisible = $event"
    />
  </section>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import api from '../api/axios'
import { useToast } from '../components/Toast.vue'
import ConfirmModal from '../components/ConfirmModal.vue'
import heroImg from '../assets/hero.png'
import { getFlagUrl } from '../utils/flags'

const route = useRoute()
const router = useRouter()
const toast = useToast()

const id = Number(route.params.id)

const conflict = reactive({
  id: null,
  name: '',
  description: '',
  countryCodes: [],
  startDate: null,
  start_date: null,
  status: '',
  imageIndex: 0
})

const loading = ref(false)
const confirmVisible = ref(false)

const confirmMessage = computed(() => {
  if (conflict.name) return `Eliminar '${conflict.name}' (#${conflict.id})?`
  if (conflict.id !== null) return `Eliminar conflicto #${conflict.id}?`
  return '¿Eliminar conflicto?'
})

async function load() {
  loading.value = true
  try {
    const res = await api.get(`/api/v1/conflicts/${id}`)
    const data = res.data
    conflict.id = data.id
    conflict.name = data.name
    conflict.description = data.description
    conflict.countryCodes = data.countryCodes || data.countries || []
    conflict.startDate = data.startDate || data.start_date
    conflict.start_date = data.startDate || data.start_date
    conflict.status = data.status
    conflict.imageIndex = data.imageIndex || 0
  } catch (e) {
    console.error('Error loading conflict', e)
    toast.error('No se pudo cargar el conflicto')
  } finally {
    loading.value = false
  }
}

onMounted(load)

function formatDate(d){
  if (!d) return '—'
  try { return new Date(d).toLocaleDateString() } catch { return d }
}

function imageFor(c){
  const hero = heroImg
  if (!c) return hero
  const idx = c.imageIndex
  if (idx && [1,2,3].includes(Number(idx))) return `/images/war${Number(idx)}.jpg`
  const idv = c.id
  if (idv !== undefined && idv !== null) {
    const n = (Math.abs(Number(idv)) % 3) + 1
    return `/images/war${n}.jpg`
  }
  return hero
}

function imageLabel(idx){
  if (!idx) return 'war1.jpg (opción 1)'
  return `war${idx}.jpg (opción ${idx})`
}

function goEdit(){
  router.push({ name: 'conflictEdit', params: { id: conflict.id } })
}

function requestDelete(){
  confirmVisible.value = true
}

async function doDelete(){
  try {
    await api.delete(`/api/v1/conflicts/${conflict.id}`)
    toast.success('Conflicto eliminado')
    confirmVisible.value = false
    router.push('/conflicts')
  } catch (e) {
    console.error('delete error', e)
    toast.error('Error eliminando conflicto')
    confirmVisible.value = false
  }
}

function closeConfirm(){
  confirmVisible.value = false
}

const flagUrl = (code, size = '24x18') => getFlagUrl(code, size)
</script>

<style scoped>
.panel{ background:var(--bg-card); border-radius:12px; padding:18px; border:1px solid rgba(15,23,42,0.04); box-shadow:0 8px 24px rgba(12,15,30,0.03) }
.panel-body.detail{ display:flex; gap:24px; align-items:flex-start }
.left{ width:200px }
.thumb-large{ width:200px; height:200px; background:#f8fafc; border-radius:8px; display:flex; align-items:center; justify-content:center; padding:10px; border:1px solid rgba(2,6,23,0.03) }
.thumb-large img{ max-width:100%; max-height:100%; object-fit:contain }
.right{ flex:1 }
.excerpt{ color:var(--muted); margin-bottom:12px }
.meta{ margin:16px 0; color:var(--muted); display:flex; flex-direction:column; gap:8px }
.meta-row{ display:flex; align-items:center; gap:8px; flex-wrap:wrap }
.meta-country{ display:inline-flex; align-items:center; gap:6px; margin-right:8px }
.meta-flag{ border-radius:2px; display:inline-block; object-fit:cover }
.meta-code{ font-weight:700; color:var(--text) }
.actions{ display:flex; gap:10px; align-items:center; margin-top:8px }
.btn{ padding:6px 10px; border-radius:8px; border:1px solid rgba(15,23,42,0.06); background:transparent; cursor:pointer }
.btn.danger{ border-color:#fca5a5; color:#b91c1c }
.link{ color:var(--accent); text-decoration:underline; font-weight:700 }
.no-countries{ color:var(--muted) }
</style>