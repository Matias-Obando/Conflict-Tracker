<template>
  <transition name="fade">
    <div class="cm-backdrop" v-if="visible" @keydown.esc="onCancel" tabindex="-1" ref="backdrop">
      <div class="cm-dialog" role="dialog" aria-modal="true" :aria-label="title" @click.stop>
        <header class="cm-header">
          <h3 class="cm-title">{{ title }}</h3>
        </header>

        <div class="cm-body">
          <p v-html="message"></p>
        </div>

        <footer class="cm-footer">
          <button class="btn cancel" @click="onCancel">Cancelar</button>
          <button class="btn confirm" @click="onConfirm" :disabled="loading">
            <span v-if="!loading">Aceptar</span>
            <span v-else>Eliminando…</span>
          </button>
        </footer>

        <button class="cm-close" @click="onCancel" aria-label="Cerrar">&times;</button>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, watch, onMounted, nextTick } from 'vue'
const props = defineProps({
  visible: { type: Boolean, default: false },
  title: { type: String, default: 'Confirmar' },
  message: { type: String, default: '¿Estás seguro?' }
})
const emit = defineEmits(['confirm','cancel','update:visible'])

const loading = ref(false)
const backdrop = ref(null)

watch(() => props.visible, async v => {
  if (v) {
    await nextTick()
    if (backdrop.value) backdrop.value.focus()
  } else {
    loading.value = false
  }
})

function onConfirm(){
  loading.value = true
  
  emit('confirm')
}

function onCancel(){
  if (loading.value) return
  emit('cancel')
  emit('update:visible', false)
}
</script>

<style scoped>
.fade-enter-active, .fade-leave-active { transition: opacity .18s ease }
.fade-enter-from, .fade-leave-to { opacity:0 }

.cm-backdrop{
  position:fixed; inset:0; display:flex; align-items:center; justify-content:center;
  background:rgba(8,10,14,0.42); z-index:9999; padding:24px;
  outline: none;
}
.cm-dialog{
  width:520px; max-width:100%; background:var(--bg-card); border-radius:12px; box-shadow:0 12px 38px rgba(2,6,23,0.3);
  padding:18px; position:relative;
}
.cm-header{ margin-bottom:6px }
.cm-title{ margin:0; font-size:18px; color:var(--text) }
.cm-body{ color:var(--muted); margin:12px 0; line-height:1.4 }
.cm-footer{ display:flex; gap:12px; justify-content:flex-end; margin-top:8px }
.btn{ padding:8px 14px; border-radius:10px; border:1px solid rgba(15,23,42,0.06); cursor:pointer; background:white }
.btn.confirm{ background:var(--accent); color:#fff; border-color:var(--accent); }
.btn.cancel{ background:#fff; color:var(--text) }
.cm-close{
  position:absolute; right:10px; top:8px; border:none; background:transparent; font-size:22px; line-height:1; cursor:pointer; color:var(--muted)
}
@media (max-width:560px){ .cm-dialog{ padding:14px } }
</style>