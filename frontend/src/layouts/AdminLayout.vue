<script setup>
import { RouterView } from 'vue-router'
import Sidebar from '../components/Sidebar.vue'
import Header from '../components/Header.vue'
</script>

<template>
  <div class="admin-layout">
    <Sidebar />
    <div class="main-content">
      <Header />
      <main class="page-content">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: var(--color-background);
}

.main-content {
  flex: 1;
  margin-left: 260px; /* Sidebar width */
  display: flex;
  flex-direction: column;
}

.page-content {
  padding: var(--spacing-xl);
  flex: 1;
  overflow-x: hidden;
}
</style>
