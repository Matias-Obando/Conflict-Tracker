<template>
  <div id="app">
    <header class="site-header">
      <div class="container header-inner">
        <div class="brand">
          <router-link to="/" class="brand-title">Los Conflictos Mundiales</router-link>
          <div class="brand-sub">La situación actual de los conflictos en nuestra web</div>
        </div>

        <nav class="main-nav">
          <router-link to="/" class="nav-link">Inicio</router-link>
          <router-link to="/create" class="nav-link btn-primary">Nuevo Conflicto</router-link>
        </nav>
      </div>
    </header>

    <main class="app-main container">
      <router-view />
    </main>

    <footer class="site-footer">
      <div class="container">
        <div>© {{ new Date().getFullYear() }} Los conflictos Mundiales</div>
        <div class="footer-right">Practica FullStack Developer</div>
      </div>
    </footer>
  </div>
</template>

<script setup>
</script>

<style scoped>
@import './assets/styles.css';

.site-header{ background:var(--bg-card); border-bottom:1px solid rgba(13,18,27,0.04); position:sticky; top:0; z-index:40; }
.header-inner{ display:flex; align-items:center; justify-content:space-between; padding:18px 0; gap:12px }
.brand-title{ font-weight:800; font-size:20px; color:var(--text); text-decoration:none }
.brand-sub{ font-size:12px; color:var(--muted); margin-top:4px }
.main-nav{ display:flex; gap:12px; align-items:center }
.nav-link{ color:var(--accent); text-decoration:none; font-weight:600 }
.btn-primary{ background:var(--accent); color:white; padding:8px 12px; border-radius:10px; text-decoration:none }
.app-main{ padding:28px 0 48px }
.site-footer{ border-top:1px solid rgba(13,18,27,0.04); padding:14px 0; color:var(--muted); font-size:13px; margin-top:32px }
.container{ max-width:1100px; margin:0 auto; padding:0 16px }
.footer-right{ float:right; color:var(--muted) }
.app-main{ padding:48px 0 48px }
</style>