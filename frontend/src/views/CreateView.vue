<template>
  <section class="create">
    <div class="panel card-form">
      <h2>Nuevo conflicto</h2>

      <form @submit.prevent="save" class="form-grid">
        <label>
          Título *
          <input v-model="form.name" required />
        </label>

        <label>
          Países (ISO3 o ISO2, separados por coma)
          <input v-model="form.countryCodes" placeholder="USA,UKR" />
        </label>

        <label>
          Fecha de inicio
          <input v-model="form.startDate" type="date" />
        </label>

        <label>
          Imagen (selecciona una de las imágenes predefinidas)
          <select v-model.number="form.imageIndex">
            <option :value="0">(usar predeterminada por id)</option>
            <option :value="1">war1.jpg (opción 1)</option>
            <option :value="2">war2.jpg (opción 2)</option>
            <option :value="3">war3.jpg (opción 3)</option>
          </select>
        </label>

        <label class="full">
          Descripción
          <textarea v-model="form.description" rows="6"></textarea>
        </label>

        <div class="form-actions">
          <button class="btn primary" type="submit" :disabled="submitting">Guardar</button>
          <router-link to="/conflicts" class="link ghost">Cancelar</router-link>
        </div>
      </form>

      <div v-if="lastServerError" class="server-error">
        <h4>Error del servidor ({{ lastServerStatus || '400' }})</h4>
        <pre>{{ lastServerError }}</pre>
      </div>

      <div v-if="debugPayload" class="server-error">
        <h4>Payload enviado (debug)</h4>
        <pre>{{ debugPayload }}</pre>
      </div>
    </div>
  </section>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../api/axios'
import { useToast } from '../components/Toast.vue'
import { useRouter } from 'vue-router'

const toast = useToast()
const router = useRouter()

const submitting = ref(false)
const lastServerError = ref('')
const lastServerStatus = ref(null)
const debugPayload = ref('')

const form = reactive({
  name: '',
  countryCodes: '',
  startDate: '',
  description: '',
  imageIndex: 0
})

function validateClient(payload) {
  if (!payload.name || !payload.name.trim()) {
    toast.error('El título es obligatorio.')
    return false
  }
  if (!payload.startDate) {
    toast.error('La fecha de inicio es obligatoria.')
    return false
  }
  return true
}

async function save() {
  lastServerError.value = ''
  lastServerStatus.value = null
  debugPayload.value = ''

  const payload = {}
  payload.name = form.name?.trim() || ''

  if (form.countryCodes && String(form.countryCodes).trim()) {
    payload.countryCodes = String(form.countryCodes)
      .split(',')
      .map(s => s.trim())
      .filter(Boolean)
  } else {
    payload.countryCodes = []
  }

  if (form.startDate) {
    try {
      const d = new Date(form.startDate)
      payload.startDate = d.toISOString().slice(0, 10)
    } catch (e) {
      payload.startDate = form.startDate
    }
  }

  if (form.description && String(form.description).trim()) payload.description = form.description.trim()

  if (form.imageIndex && [1,2,3].includes(Number(form.imageIndex))) payload.imageIndex = Number(form.imageIndex)

  payload.status = 'ACTIVE'

  debugPayload.value = JSON.stringify(payload, null, 2)

  if (!validateClient(payload)) return

  submitting.value = true
  try {
    const res = await api.post('/api/v1/conflicts', payload)
    console.log('create response', res)
    toast.success('Conflicto creado')
    router.push('/conflicts')
  } catch (err) {
    console.error('create error', err)
    if (err.response) {
      lastServerStatus.value = err.response.status
      try {
        lastServerError.value = JSON.stringify(err.response.data, null, 2)
      } catch {
        lastServerError.value = String(err.response.data || err.response.statusText || err.message)
      }
      toast.error('Error creando conflicto: mira detalles abajo')
    } else {
      lastServerError.value = String(err.message || err)
      toast.error('Error creando conflicto (sin respuesta del servidor).')
    }
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>

.create {
  padding: 12px 0 80px;
}


.form-grid{ display:grid; grid-template-columns:1fr 1fr; gap:12px }

label{ display:flex; flex-direction:column; gap:6px; color:var(--text); font-weight:600 }

input, textarea, select{ padding:10px; border-radius:8px; border:1px solid rgba(15,23,42,0.06); font-size:14px; background: white; color:var(--text) }

label.full{ grid-column:1 / -1 }

.form-actions{ grid-column:1 / -1; display:flex; gap:10px; align-items:center; margin-top:8px }
.btn.primary{ background:var(--accent); color:white; border-color:var(--accent); padding:10px 14px; border-radius:8px; cursor:pointer }
.link.ghost{ color:var(--accent); text-decoration:underline }

.server-error{ margin-top:16px; background:#fff6f6; border:1px solid #ffd2d2; padding:12px; border-radius:8px; color:#7f1d1d }
.server-error pre{ white-space:pre-wrap; max-height:280px; overflow:auto; background:transparent; padding:0; margin:0; color:inherit }

.card-form {
  background: rgba(255,255,255,0.96);
  border-radius: 12px;
  padding: 22px;
  box-shadow: 0 12px 30px rgba(12,15,30,0.08);
  border: 1px solid rgba(15,23,42,0.04);
  backdrop-filter: blur(6px);
  margin-top: -40px;
}

.card-form > h2 {
  margin: 0 0 14px 0;
  font-size: 20px;
  color: var(--text);
}

@media (max-width: 900px) {
  .form-grid{ grid-template-columns: 1fr; }
  .create { padding: 12px 0 48px; }
  .card-form { padding: 16px; margin-top: -20px; }
}
</style>