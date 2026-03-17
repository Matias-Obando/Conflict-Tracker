<template>
  <div class="toast-wrapper" v-if="toasts.length">
    <div v-for="t in toasts" :key="t.id" :class="['toast', t.type]">
      <strong v-if="t.title">{{ t.title }}</strong>
      <div>{{ t.message }}</div>
      <button class="close" @click="remove(t.id)">✕</button>
    </div>
  </div>
</template>

<script>
import { reactive } from 'vue'
const state = reactive({ toasts: [] })
let id = 1

export function useToast() {
  function add(message, type='info', title='') {
    const t = { id: id++, message, type, title }
    state.toasts.push(t)
    setTimeout(()=> remove(t.id), 4000)
  }
  function remove(id) { const i = state.toasts.findIndex(x=>x.id===id); if(i>=0) state.toasts.splice(i,1) }
  return {
    toasts: state.toasts,
    success: (m,t='') => add(m,'success',t),
    error: (m,t='') => add(m,'error',t),
    warn: (m,t='') => add(m,'warn',t),
    info: (m,t='') => add(m,'info',t),
    remove
  }
}

export default {
  name:'Toast',
  setup(){ return { toasts: state.toasts, remove: (id)=>{ const i = state.toasts.findIndex(x=>x.id===id); if(i>=0) state.toasts.splice(i,1) } } }
}
</script>

<style scoped>
.toast-wrapper{ position:fixed; right:18px; top:18px; display:flex; flex-direction:column; gap:8px; z-index:9999 }
.toast{ min-width:200px; padding:10px 12px; border-radius:8px; box-shadow:0 6px 18px rgba(0,0,0,0.08); color:#111; position:relative }
.toast.success{ background:#e6ffef; border-left:4px solid #16a34a }
.toast.error{ background:#fff0f0; border-left:4px solid #ef4444 }
.toast.warn{ background:#fff7e6; border-left:4px solid #f59e0b }
.toast.info{ background:#eef2ff; border-left:4px solid #2563eb }
.toast .close{ position:absolute; right:8px; top:6px; background:transparent; border:0; cursor:pointer }
</style>