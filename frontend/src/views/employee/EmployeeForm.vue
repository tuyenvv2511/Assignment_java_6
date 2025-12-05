<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()
const isEdit = ref(false)
const loading = ref(false)

const employee = ref({
  ma: '',
  ten: '',
  tenDem: '',
  ho: '',
  gioiTinh: 'Nam',
  ngaySinh: '',
  diaChi: '',
  sdt: '',
  matKhau: '',
  idCH: null,
  idCV: null,
  idGuiBC: null,
  trangThai: 1
})

const stores = ref([])
const roles = ref([])
const managers = ref([])

const fetchData = async () => {
  try {
    const [storesRes, rolesRes, employeesRes] = await Promise.all([
      request.get('/cua-hang'),
      request.get('/chuc-vu'),
      request.get('/nhan-vien') // Assuming this returns a page, might need adjustment if it's paginated
    ])
    stores.value = storesRes
    roles.value = rolesRes
    // For managers, we might need a non-paginated endpoint or just use the first page for now
    // If /nhan-vien returns a page, we need .content
    managers.value = employeesRes.content || employeesRes
  } catch (error) {
    console.error('Failed to fetch reference data:', error)
  }
}

const fetchEmployee = async (id) => {
  try {
    const response = await request.get(`/nhan-vien/${id}`)
    employee.value = {
      ...response,
      matKhau: '' // Don't populate password
    }
  } catch (error) {
    console.error('Failed to fetch employee:', error)
  }
}

onMounted(async () => {
  await fetchData()
  if (route.params.id) {
    isEdit.value = true
    await fetchEmployee(route.params.id)
  }
})

const saveEmployee = async () => {
  loading.value = true
  try {
    if (isEdit.value) {
      await request.put(`/nhan-vien/${route.params.id}`, employee.value)
    } else {
      await request.post('/nhan-vien', employee.value)
    }
    router.push('/admin/employees')
  } catch (error) {
    console.error('Failed to save employee:', error)
    alert('Failed to save employee')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="employee-form fade-in">
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Edit Employee' : 'Add New Employee' }}</h1>
      <button @click="router.back()" class="btn btn-secondary">Back</button>
    </div>

    <div class="card">
      <form @submit.prevent="saveEmployee">
        <div class="form-section">
          <h2 class="section-title">Personal Information</h2>
          <div class="grid-3">
            <div class="input-group">
              <label class="input-label">Last Name</label>
              <input v-model="employee.ho" type="text" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Middle Name</label>
              <input v-model="employee.tenDem" type="text" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">First Name</label>
              <input v-model="employee.ten" type="text" class="form-control" required>
            </div>
          </div>

          <div class="grid-3">
            <div class="input-group">
              <label class="input-label">Employee Code</label>
              <input v-model="employee.ma" type="text" class="form-control" required>
            </div>
            <div class="input-group">
              <label class="input-label">Date of Birth</label>
              <input v-model="employee.ngaySinh" type="date" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Gender</label>
              <select v-model="employee.gioiTinh" class="form-control">
                <option value="Nam">Male</option>
                <option value="Nu">Female</option>
                <option value="Khac">Other</option>
              </select>
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2 class="section-title">Contact & Account</h2>
          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">Phone Number</label>
              <input v-model="employee.sdt" type="tel" class="form-control">
            </div>
            <div class="input-group">
              <label class="input-label">Password</label>
              <input v-model="employee.matKhau" type="password" class="form-control" :required="!isEdit">
            </div>
          </div>

          <div class="input-group">
            <label class="input-label">Address</label>
            <input v-model="employee.diaChi" type="text" class="form-control">
          </div>
        </div>

        <div class="form-section">
          <h2 class="section-title">Job Details</h2>
          <div class="grid-3">
            <div class="input-group">
              <label class="input-label">Store</label>
              <select v-model="employee.idCH" class="form-control">
                <option :value="null">Select Store</option>
                <option v-for="store in stores" :key="store.id" :value="store.id">
                  {{ store.ten }}
                </option>
              </select>
            </div>
            <div class="input-group">
              <label class="input-label">Role</label>
              <select v-model="employee.idCV" class="form-control">
                <option :value="null">Select Role</option>
                <option v-for="role in roles" :key="role.id" :value="role.id">
                  {{ role.ten }}
                </option>
              </select>
            </div>
            <div class="input-group">
              <label class="input-label">Manager</label>
              <select v-model="employee.idGuiBC" class="form-control">
                <option :value="null">Select Manager</option>
                <option v-for="manager in managers" :key="manager.id" :value="manager.id">
                  {{ manager.ten }} ({{ manager.ma }})
                </option>
              </select>
            </div>
          </div>
          
          <div class="input-group mt-4">
            <label class="checkbox-label">
              <input v-model="employee.trangThai" type="checkbox" :true-value="1" :false-value="0">
              Active Status
            </label>
          </div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? 'Saving...' : 'Save Employee' }}
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

.mt-4 {
  margin-top: var(--spacing-md);
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: var(--spacing-sm);
  cursor: pointer;
  font-weight: 500;
}

.form-actions {
  margin-top: var(--spacing-lg);
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid var(--color-border);
  padding-top: var(--spacing-md);
}
</style>
