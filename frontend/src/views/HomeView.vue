<template>
  <section class="home">
    <div class="panel">
      <div class="panel-head">
        <h2>Conflictos</h2>
        <div class="head-actions">
          <SearchBar v-model:q="q" v-model:status="statusFilter" @refresh="load" />
          <div class="counts">Total: <strong>{{ total }}</strong> • Activos: <strong>{{ activeCount }}</strong></div>
        </div>
      </div>

      <div class="panel-body">
        <table class="conflict-table" v-if="filtered.length">
          <thead>
            <tr>
              <th>ID</th>
              <th>Imagen</th>
              <th>Título</th>
              <th>Países</th>
              <th>Inicio</th>
              <th>Estado</th>
              <th class="actions-col">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="c in filtered" :key="c.id">
              <td class="mono">#{{ c.id }}</td>
              <td>
                <div class="thumb">
                  <img :src="imageFor(c)" class="row-flag" alt="Imagen conflicto" />
                </div>
              </td>
              <td>
                <div class="row-title">{{ c.name || '—' }}</div>
                <div class="row-excerpt">{{ excerpt(c) }}</div>
              </td>
              <td>
                <div v-if="(c.countryCodes || []).length" class="countries">
                  <span v-for="code in (c.countryCodes || [])" :key="code" class="country-item">
                    <img
                      v-if="getFlagUrl(code)"
                      :src="getFlagUrl(code)"
                      :alt="code"
                      class="country-flag"
                      width="20"
                      height="14"
                    />
                    <span class="country-code">{{ code }}</span>
                  </span>
                </div>
                <div v-else class="no-countries">—</div>
              </td>
              <td>{{ formatDate(c.startDate || c.start_date) }}</td>
              <td><span class="status" :data-status="c.status">{{ c.status || 'N/A' }}</span></td>
              <td class="actions-col">
                <router-link :to="{ name: 'conflictDetail', params: { id: c.id } }" class="link">Ver</router-link>
                <button class="btn" @click="edit(c)">Editar</button>
                <button class="btn danger" @click="requestDelete(c.id, c.name)">Eliminar</button>
              </td>
            </tr>
          </tbody>
        </table>

        <div v-else class="empty">No hay conflictos para mostrar.</div>
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
import { ref, onMounted, computed } from 'vue'
import SearchBar from '../components/SearchBar.vue'
import ConfirmModal from '../components/ConfirmModal.vue'
import api from '../api/axios'
import { useToast } from '../components/Toast.vue'
import heroImg from '../assets/hero.png'
import { useRouter } from 'vue-router'
import { getFlagUrl } from '../utils/flags'

const router = useRouter()

const toast = useToast()
const items = ref([])
const q = ref('')
const statusFilter = ref('')

const confirmVisible = ref(false)
const deleteId = ref(null)
const deleteName = ref('')


const confirmMessage = computed(() => {
  if (deleteName.value) return `Eliminar '${deleteName.value}' (#${deleteId.value})?`
  if (deleteId.value !== null) return `Eliminar conflicto #${deleteId.value}?`
  return '¿Eliminar conflicto?'
})

async function load(){
  try {
    const res = await api.get('/api/v1/conflicts')
    items.value = Array.isArray(res.data) ? res.data : (res.data?.content || [])
  } catch (e) {
    console.error('Error cargando conflictos', e)
    toast.error('Error cargando conflictos')
    items.value = []
  }
}

onMounted(load)

const total = computed(()=> (items.value || []).length)
const activeCount = computed(()=> (items.value || []).filter(x => x.status === 'ACTIVE').length)

const filtered = computed(()=> {
  const term = (q.value || '').trim().toLowerCase()
  return (items.value || []).filter(c => {
    if (statusFilter.value && c.status !== statusFilter.value) return false
    if (!term) return true
    if ((c.name || '').toLowerCase().includes(term)) return true
    if ((c.description || '').toLowerCase().includes(term)) return true
    if ((c.countryCodes || []).join(',').toLowerCase().includes(term)) return true
    return false
  })
})

function formatDate(d){
  if (!d) return '—'
  try { return new Date(d).toLocaleDateString() } catch { return d }
}

function excerpt(c){
  const t = c.description || c.summary || ''
  return t.length > 80 ? t.slice(0,78) + '…' : t
}

function edit(c) {
  if (!c || c.id === undefined || c.id === null) {
    console.warn('edit: conflicto sin id', c)
    return
  }
  router.push({ name: 'conflictEdit', params: { id: c.id } })
}


function requestDelete(id, name){
  deleteId.value = id
  deleteName.value = name || ''
  confirmVisible.value = true
}

async function doDelete(){
  try {
    await api.delete(`/api/v1/conflicts/${deleteId.value}`)
    toast.success('Conflicto eliminado')
    confirmVisible.value = false
    deleteId.value = null
    deleteName.value = ''
    await load()
  } catch (e) {
    console.error('delete error', e)
    toast.error('Error eliminando')
    confirmVisible.value = false
  }
}

function closeConfirm(){
  confirmVisible.value = false
  deleteId.value = null
  deleteName.value = ''
}


function imageFor(c){
  const hero = heroImg
  if (!c) return hero
  const idx = c.imageIndex
  if (idx && [1,2,3].includes(Number(idx))) return `/images/war${Number(idx)}.jpg`
  const id = c.id
  if (id !== undefined && id !== null) {
    const n = (Math.abs(Number(id)) % 3) + 1
    return `/images/war${n}.jpg`
  }
  return hero
}
</script>

<style scoped>

.panel{ background:var(--bg-card); border-radius:12px; padding:18px; border:1px solid rgba(15,23,42,0.04); box-shadow:0 8px 24px rgba(12,15,30,0.03) }
.panel-head{ display:flex; justify-content:space-between; align-items:center; gap:12px; margin-bottom:12px }
.panel-head h2{ margin:0; font-size:18px; color:var(--text) }
.head-actions{ display:flex; gap:12px; align-items:center }

.conflict-table{ width:100%; border-collapse:collapse; font-size:14px }
.conflict-table thead th{ text-align:left; padding:12px 10px; color:var(--muted); font-weight:700; border-bottom:1px solid rgba(15,23,42,0.04) }
.conflict-table tbody td{ padding:12px 10px; border-bottom:1px dashed rgba(15,23,42,0.03); vertical-align:middle }


.thumb{
  width:120px;
  height:80px;
  display:flex;
  align-items:center;
  justify-content:center;
  background:#f8fafc;
  border-radius:8px;
  padding:6px;
  border:1px solid rgba(2,6,23,0.03);
}
.row-flag{
  max-width:100%;
  max-height:100%;
  object-fit:contain;
  display:block;
  border-radius:4px;
}

.row-title{ font-weight:700; color:var(--text) }
.row-excerpt{ color:var(--muted); font-size:13px; margin-top:4px }
.mono{ font-family:ui-monospace, SFMono-Regular, Menlo, Monaco, "Roboto Mono", monospace }
.status{ padding:6px 8px; border-radius:8px; font-weight:700; display:inline-block }
.status[data-status="ACTIVE"]{ background:#e6fffa; color:#065f46 }
.status[data-status="ENDED"]{ background:#ffefef; color:#7f1d1d }

.actions-col{ width:220px; text-align:right }
.btn{ padding:6px 10px; border-radius:8px; border:1px solid rgba(15,23,42,0.06); background:transparent; cursor:pointer; margin-left:8px }
.btn.danger{ border-color:#fca5a5; color:#b91c1c }
.link{ color:var(--accent); text-decoration:underline; font-weight:700 }
.empty{ padding:48px 18px; color:var(--muted); text-align:center }
.counts{ color:var(--muted); font-weight:600 }

/* new styles for flags */
.countries{ display:flex; gap:8px; flex-wrap:wrap; align-items:center }
.country-item{ display:inline-flex; align-items:center; gap:8px; margin-right:6px }
.country-flag{ display:inline-block; border-radius:2px; object-fit:cover }
.country-code{ font-weight:600; color:var(--text); font-size:13px }
.no-countries{ color:var(--muted) }
</style>