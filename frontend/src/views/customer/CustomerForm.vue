<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()
const isEdit = ref(false)
const loading = ref(false)

const customer = ref({
  ma: '',
  ten: '',
  tenDem: '',
  ho: '',
  ngaySinh: '',
  sdt: '',
  diaChi: '',
  thanhPho: '',
  quocGia: '',
  matKhau: '',
  trangThai: 1
})

const fetchCustomer = async (id) => {
  try {
    const response = await request.get(`/khach-hang/${id}`)
    customer.value = {
      ...response,
      matKhau: '' // Don't populate password
    }
  } catch (error) {
    console.error('Failed to fetch customer:', error)
  }
}

onMounted(async () => {
  if (route.params.id) {
    isEdit.value = true
    await fetchCustomer(route.params.id)
  }
})

const saveCustomer = async () => {
  loading.value = true
  try {
    if (isEdit.value) {
      await request.put(`/khach-hang/${route.params.id}`, customer.value)
    } else {
      await request.post('/khach-hang', customer.value)
    }
    router.push('/admin/customers')
  } catch (error) {
    console.error('Failed to save customer:', error)
    alert('Failed to save customer')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="customer-form fade-in">
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Edit Customer' : 'Add New Customer' }}</h1>
      <button @click="router.back()" class="btn btn-secondary">Back</button>
    </div>

    <div class="card">
      <form @submit.prevent="saveCustomer">
        <div class="form-section">
          <h2 class="section-title">Personal Information</h2>
          <div class="grid-3">
            <div class="input-group">
              <label class="input-label">Last Name</label>
              <input v-model="customer.ho" type="text" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Middle Name</label>
              <input v-model="customer.tenDem" type="text" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">First Name</label>
              <input v-model="customer.ten" type="text" class="form-control" required>
            </div>
          </div>

          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">Customer Code</label>
              <input v-model="customer.ma" type="text" class="form-control" required>
            </div>
            <div class="input-group">
              <label class="input-label">Date of Birth</label>
              <input v-model="customer.ngaySinh" type="date" class="form-control">
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2 class="section-title">Contact Information</h2>
          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">Phone Number</label>
              <input v-model="customer.sdt" type="tel" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Password</label>
              <input v-model="customer.matKhau" type="password" class="form-control" :required="!isEdit">
            </div>
          </div>

          <div class="input-group">
            <label class="input-label">Address</label>
            <input v-model="customer.diaChi" type="text" class="form-control">
          </div>

          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">City</label>
              <input v-model="customer.thanhPho" type="text" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Country</label>
              <input v-model="customer.quocGia" type="text" class="form-control">
            </div>
          </div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? 'Saving...' : 'Save Customer' }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-lg);
}

.page-title {
  font-size: var(--font-size-2xl);
  font-weight: 700;
}

.form-section {
  margin-bottom: var(--spacing-xl);
}

.section-title {
  font-size: var(--font-size-lg);
  font-weight: 600;
  color: var(--color-text-main);
  border-bottom: 1px solid var(--color-border);
  padding-bottom: var(--spacing-sm);
  margin-bottom: var(--spacing-md);
}

.grid-3 {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: var(--spacing-md);
}

.grid-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--spacing-md);
}

.form-actions {
  margin-top: var(--spacing-lg);
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid var(--color-border);
  padding-top: var(--spacing-md);
}
</style>
