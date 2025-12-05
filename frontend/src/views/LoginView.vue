<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const username = ref('')
const password = ref('')
const loading = ref(false)
const error = ref('')

const handleLogin = async () => {
  loading.value = true
  error.value = ''
  
  try {
    await authStore.login(username.value, password.value)
    router.push('/admin')
  } catch (e) {
    error.value = 'Invalid credentials or server error'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-container">
    <div class="login-card card fade-in">
      <div class="login-header">
        <h1 class="login-title">Welcome Back</h1>
        <p class="login-subtitle">Sign in to your account</p>
      </div>
      
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="input-group">
          <label for="username" class="input-label">Username</label>
          <input 
            id="username"
            v-model="username"
            type="text" 
            class="form-control"
            placeholder="Enter your username"
            required
          >
        </div>
        
        <div class="input-group">
          <label for="password" class="input-label">Password</label>
          <input 
            id="password"
            v-model="password"
            type="password" 
            class="form-control"
            placeholder="Enter your password"
            required
          >
        </div>
        
        <div v-if="error" class="error-message">
          {{ error }}
        </div>
        
        <button type="submit" class="btn btn-primary btn-block" :disabled="loading">
          <span v-if="loading">Signing in...</span>
          <span v-else>Sign In</span>
        </button>
      </form>
    </div>
  </div>
</template>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--color-background) 0%, #E0E7FF 100%);
  padding: var(--spacing-md);
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: var(--spacing-xl);
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.login-header {
  text-align: center;
  margin-bottom: var(--spacing-xl);
}

.login-title {
  font-size: var(--font-size-2xl);
  font-weight: 700;
  color: var(--color-text-main);
  margin-bottom: var(--spacing-xs);
}

.login-subtitle {
  color: var(--color-text-muted);
  font-size: var(--font-size-sm);
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-md);
}

.btn-block {
  width: 100%;
  margin-top: var(--spacing-sm);
  padding: 0.75rem;
}

.error-message {
  color: var(--color-danger);
  font-size: var(--font-size-sm);
  text-align: center;
  padding: var(--spacing-xs);
  background: rgba(239, 68, 68, 0.1);
  border-radius: var(--radius-sm);
}
</style>
