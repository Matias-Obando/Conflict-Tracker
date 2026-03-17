<template>
  <form class="form-panel" @submit.prevent="submit">
    <h2 class="panel-title">Crear nuevo conflicto</h2>

    <label>Título</label>
    <input v-model="form.title" placeholder="Nombre del conflicto" required />

    <label>Países</label>
    <div class="chips-input">
      <ul class="chips">
        <li v-for="(c, i) in countryCodes" :key="c" class="chip">
          {{ c }} <button type="button" @click="removeCode(i)">✕</button>
        </li>
      </ul>
      <input v-model="countryInput" @keydown.enter.prevent="addFromInput" placeholder="Añadir código y Enter" />
    </div>
    <div class="hint">Sugeridos: <strong v-if="allowedCodes.length">{{ allowedCodes.join(', ') }}</strong><span v-else> (cargando...)</span></div>

    <label>Inicio</label>
    <input type="date" v-model="form.startDate" />

    <label>Resumen</label>
    <textarea v-model="form.summary" rows="4" placeholder="Breve descripción"></textarea>

    <div class="actions">
      <button class="btn primary" type="submit">Crear conflicto</button>
      <button class="btn ghost" type="button" @click="reset">Limpiar</button>
    </div>
  </form>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useConflictsStore } from '../stores/conflicts'
import api from '../api/axios'
import { useToast } from './Toast.vue'

const store = useConflictsStore()
const toast = useToast()

const form = reactive({ title:'', startDate:'', summary:'', active:true })
const countryCodes = ref([])
const countryInput = ref('')
const allowedCodes = ref([])

async function loadAllowed(){
  try{
    const res = await api.get('/countries')
    if (Array.isArray(res.data)) allowedCodes.value = res.data.map(c=> (c.code||'').toUpperCase()).filter(Boolean)
  }catch(e){
    
  }
}
onMounted(loadAllowed)

function addFromInput(){
  const parts = (countryInput.value || '').split(/[,\s;:.]+/).map(s=>s.trim().toUpperCase()).filter(Boolean)
  countryInput.value = ''
  parts.forEach(p => {
    if (!/^[A-Z]{3}$/.test(p)) { toast.warn('Formato inválido: ' + p); return }
    if (countryCodes.value.includes(p)) { toast.info(p + ' ya añadido'); return }
    if (allowedCodes.value.length && !allowedCodes.value.includes(p)) { toast.warn('Código no reconocido: ' + p); return }
    countryCodes.value.push(p)
  })
}

function removeCode(i){ countryCodes.value.splice(i,1) }
function reset(){ form.title=''; form.startDate=''; form.summary=''; countryCodes.value=[] }

async function submit(){
  if (!form.title) { toast.error('Título requerido'); return }
  const payload = { name: form.title, description: form.summary, startDate: form.startDate || undefined, countryCodes: countryCodes.value, status: form.active ? 'ACTIVE' : 'ENDED' }
  try{
    const created = await store.createConflict(payload)
    toast.success('Conflicto creado')
    reset()
  }catch(e){
    const serverMsg = e?.response?.data || e?.message
    toast.error(typeof serverMsg === 'string' ? serverMsg : 'Error creando conflicto')
  }
}
</script>

<style scoped>
.form-panel{ max-width:720px; margin:18px auto; padding:18px; background:linear-gradient(180deg,#fff,#fbfbff); border-radius:12px; border:1px solid #e6e6f0 }
.panel-title{ margin:0 0 14px; text-align:center; color:#111; font-weight:700 }
label{ display:block; margin-top:12px; font-weight:700; color:#374151 }
input, textarea{ width:100%; padding:10px; border-radius:8px; border:1px solid #e6e6f0; margin-top:6px; box-sizing:border-box }
.chips-input{ display:flex; gap:10px; align-items:center; margin-top:8px; flex-wrap:wrap }
.chips{ display:flex; gap:8px; padding:0; margin:0; list-style:none; flex-wrap:wrap }
.chip{ background:#0ea5e9; color:white; padding:6px 8px; border-radius:999px; display:inline-flex; align-items:center; gap:8px; font-weight:700 }
.chip button{ background:transparent; border:0; color:rgba(255,255,255,0.9); cursor:pointer }
.hint{ color:#6b7280; font-size:13px; margin-top:6px }
.actions{ margin-top:16px; display:flex; gap:10px }
.btn{ padding:8px 12px; border-radius:8px; border:1px solid #e6e6f0; background:white; cursor:pointer }
.btn.primary{ background:#2563eb; color:white; border-color:#2563eb }
.btn.ghost{ background:transparent }
</style>