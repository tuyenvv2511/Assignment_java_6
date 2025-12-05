<script setup>
import { RouterLink, useRoute } from 'vue-router'
import { computed } from 'vue'

const route = useRoute()

const menuItems = [
  { name: 'Dashboard', path: '/admin', icon: '📊' },
  { name: 'Products', path: '/admin/products', icon: '📦' },
  { name: 'Invoices', path: '/admin/invoices', icon: '📄' },
  { name: 'Customers', path: '/admin/customers', icon: '👥' },
  { name: 'Employees', path: '/admin/employees', icon: '👔' },
]

const isActive = (path) => {
  if (path === '/admin') return route.path === '/admin'
  return route.path.startsWith(path)
}
</script>

<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h2 class="brand">Shop Admin</h2>
    </div>
    
    <nav class="sidebar-nav">
      <RouterLink 
        v-for="item in menuItems" 
        :key="item.path" 
        :to="item.path"
        class="nav-item"
        :class="{ active: isActive(item.path) }"
      >
        <span class="nav-icon">{{ item.icon }}</span>
        <span class="nav-text">{{ item.name }}</span>
      </RouterLink>
    </nav>
    
    <div class="sidebar-footer">
      <p class="version">v1.0.0</p>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 260px;
  background: white;
  border-right: 1px solid var(--color-border);
  display: flex;
  flex-direction: column;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 100;
}

.sidebar-header {
  padding: var(--spacing-lg);
  border-bottom: 1px solid var(--color-border);
}

.brand {
  font-size: var(--font-size-xl);
  font-weight: 700;
  color: var(--color-primary);
  letter-spacing: -0.025em;
}

.sidebar-nav {
  flex: 1;
  padding: var(--spacing-md);
  display: flex;
  flex-direction: column;
  gap: var(--spacing-xs);
  overflow-y: auto;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-md);
  color: var(--color-text-muted);
  font-weight: 500;
  transition: all var(--transition-fast);
}

.nav-item:hover {
  background: var(--color-surface-hover);
  color: var(--color-text-main);
}

.nav-item.active {
  background: var(--color-primary);
  color: white;
}

.nav-icon {
  margin-right: var(--spacing-sm);
  font-size: 1.25rem;
}

.sidebar-footer {
  padding: var(--spacing-md);
  border-top: 1px solid var(--color-border);
  text-align: center;
}

.version {
  font-size: var(--font-size-sm);
  color: var(--color-text-light);
}
</style>
