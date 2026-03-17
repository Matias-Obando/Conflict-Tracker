import { defineStore } from 'pinia'
import api from '../api/axios'

function normalizeConflict(c) {
  return {
    id: c.id,
    title: c.name || c.title || '',
    summary: c.description || c.summary || '',
    country: (Array.isArray(c.countryCodes) && c.countryCodes.join(', ')) || c.country || '',
    startDate: c.startDate || c.createdAt || '',
    active: (c.status === 'ACTIVE') || !!c.active,
    raw: c
  }
}

export const useConflictsStore = defineStore('conflicts', {
  state: () => ({
    conflicts: [],
    loading: false,
    error: null,
    selected: null
  }),
  actions: {
    async fetchConflicts() {
      this.loading = true
      this.error = null
      try {
        const res = await api.get('/conflicts')
        this.conflicts = Array.isArray(res.data) ? res.data.map(normalizeConflict) : []
        console.log('Fetched and normalized conflicts:', this.conflicts)
      } catch (err) {
        this.error = err
        console.error('Error fetching conflicts:', err)
      } finally {
        this.loading = false
      }
    },
    async fetchConflict(id) {
      this.loading = true
      this.error = null
      try {
        const res = await api.get(`/conflicts/${id}`)
        const normalized = normalizeConflict(res.data)
        this.selected = normalized
        return normalized
      } catch (err) {
        this.error = err
        return null
      } finally {
        this.loading = false
      }
    },
    async createConflict(payload) {
      try {
        const res = await api.post('/conflicts', payload)
        const created = normalizeConflict(res.data)
        this.conflicts.push(created)
        return created
      } catch (err) {
        this.error = err
        throw err
      }
    },
    async updateConflict(id, payload) {
      try {
        const res = await api.put(`/conflicts/${id}`, payload)
        const updated = normalizeConflict(res.data)
        const idx = this.conflicts.findIndex(c => c.id === id)
        if (idx !== -1) this.conflicts.splice(idx, 1, updated)
        if (this.selected?.id === id) this.selected = updated
        return updated
      } catch (err) {
        this.error = err
        throw err
      }
    },
    async deleteConflict(id) {
      try {
        await api.delete(`/conflicts/${id}`)
        this.conflicts = this.conflicts.filter(c => c.id !== id)
        if (this.selected?.id === id) this.selected = null
      } catch (err) {
        this.error = err
        throw err
      }
    }
  },
  getters: {
    countActive: (state) => state.conflicts.filter(c => c.active).length
  }
})