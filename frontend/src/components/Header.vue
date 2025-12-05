<script setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<template>
  <header class="top-header">
    <div class="header-content">
      <h3 class="page-title">Dashboard</h3>
      
      <div class="user-menu">
        <div class="user-info">
          <span class="user-name">{{ authStore.userName }}</span>
          <span class="user-role">{{ authStore.userRole }}</span>
        </div>
        <button @click="handleLogout" class="btn btn-secondary btn-sm">
          Logout
        </button>
      </div>
    </div>
  </header>
</template>

<style scoped>
.top-header {
  height: 64px;
  background: white;
  border-bottom: 1px solid var(--color-border);
  display: flex;
  align-items: center;
  padding: 0 var(--spacing-xl);
  position: sticky;
  top: 0;
  z-index: 90;
}

.header-content {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title {
  font-size: var(--font-size-lg);
  font-weight: 600;
  color: var(--color-text-main);
}

.user-menu {
  display: flex;
  align-items: center;
  gap: var(--spacing-lg);
}

.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.user-name {
  font-weight: 600;
  font-size: var(--font-size-sm);
  color: var(--color-text-main);
}

.user-role {
  font-size: 0.75rem;
  color: var(--color-text-muted);
}

.btn-sm {
  padding: 0.25rem 0.75rem;
  font-size: 0.875rem;
}
</style>
