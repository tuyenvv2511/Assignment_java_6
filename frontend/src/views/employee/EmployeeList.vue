<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Pagination from '@/components/Pagination.vue'
import SearchBar from '@/components/SearchBar.vue'

const router = useRouter()
const authStore = useAuthStore()
const employees = ref([])
const loading = ref(false)
const searchQuery = ref('')
const currentPage = ref(0)
const totalPages = ref(0)

const fetchEmployees = async () => {
  loading.value = true
  try {
    const response = await request.get('/nhan-vien', {
      params: {
        page: currentPage.value,
        size: 5,
        keyword: searchQuery.value || undefined
      }
    })
    employees.value = response.content || []
    totalPages.value = response.totalPages || 0
    currentPage.value = response.number || 0
  } catch (error) {
    console.error('Failed to fetch employees:', error)
    employees.value = []
  } finally {
    loading.value = false
  }
}

const handleSearch = (query) => {
  searchQuery.value = query
  currentPage.value = 0
  fetchEmployees()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchEmployees()
}

const handleDelete = async (id) => {
  if (!confirm('Are you sure you want to delete this employee?')) return
  
  try {
    await request.delete(`/nhan-vien/${id}`)
    fetchEmployees()
  } catch (error) {
    console.error('Failed to delete employee:', error)
    alert('Failed to delete employee')
  }
}

onMounted(() => {
  fetchEmployees()
})
</script>

<template>
  <div class="employee-list">
    <div class="page-header">
      <h2>Employee Management</h2>
      <button 
        v-if="authStore.isAdmin" 
        class="btn btn-primary" 
        @click="router.push('/admin/employees/create')"
      >
        + Add New Employee
      </button>
    </div>

    <SearchBar 
      v-model="searchQuery"
      placeholder="Search by code, name, or phone..."
      @search="handleSearch"
    />

    <div class="card">
      <div v-if="loading" class="loading">Loading...</div>
      
      <table v-else class="data-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>CODE</th>
            <th>FULL NAME</th>
            <th>PHONE</th>
            <th>ROLE</th>
            <th>STORE</th>
            <th>STATUS</th>
            <th v-if="authStore.isAdmin">ACTIONS</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="employees.length === 0">
            <td :colspan="authStore.isAdmin ? 8 : 7" class="text-center">
              No employees found
            </td>
          </tr>
          <tr v-for="employee in employees" :key="employee.id">
            <td>{{ employee.id }}</td>
            <td>{{ employee.ma }}</td>
            <td>{{ employee.ho }} {{ employee.tenDem }} {{ employee.ten }}</td>
            <td>{{ employee.sdt }}</td>
            <td>{{ employee.tenChucVu }}</td>
            <td>{{ employee.tenCuaHang }}</td>
            <td>
              <span class="badge" :class="employee.trangThai === 1 ? 'status-active' : 'status-inactive'">
                {{ employee.trangThai === 1 ? 'Active' : 'Inactive' }}
              </span>
            </td>
            <td v-if="authStore.isAdmin">
              <button 
                class="btn btn-sm btn-secondary" 
                @click="router.push(`/admin/employees/edit/${employee.id}`)"
              >
                Edit
              </button>
              <button 
                class="btn btn-sm btn-danger" 
                @click="handleDelete(employee.id)"
              >
                Delete
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      <Pagination 
        v-if="totalPages > 0"
        :current-page="currentPage"
        :total-pages="totalPages"
        @page-change="handlePageChange"
      />
    </div>
  </div>
</template>

<style scoped>
.employee-list {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-header h2 {
  font-size: var(--font-size-2xl);
  font-weight: 700;
  color: var(--color-text-main);
}

.loading {
  text-align: center;
  padding: var(--spacing-xl);
  color: var(--color-text-muted);
}

.data-table {
  width: 100%;
  border-collapse: collapse;
}

.data-table thead {
  background: var(--color-surface-hover);
}

.data-table th {
  padding: var(--spacing-md);
  text-align: left;
  font-weight: 600;
  font-size: var(--font-size-sm);
  color: var(--color-text-muted);
  text-transform: uppercase;
  border-bottom: 2px solid var(--color-border);
}

.data-table td {
  padding: var(--spacing-md);
  border-bottom: 1px solid var(--color-border);
}

.data-table tbody tr:hover {
  background: var(--color-surface-hover);
}

.text-center {
  text-align: center;
  color: var(--color-text-muted);
}

.badge {
  display: inline-block;
  padding: 0.25rem 0.5rem;
  border-radius: var(--radius-sm);
  font-size: 0.75rem;
  font-weight: 600;
}

.status-active {
  background: var(--color-success);
  color: white;
}

.status-inactive {
  background: var(--color-text-muted);
  color: white;
}

.btn-sm {
  padding: 0.25rem 0.75rem;
  font-size: var(--font-size-sm);
  margin-right: 0.5rem;
}

.btn-danger {
  background: var(--color-danger);
  color: white;
}

.btn-danger:hover {
  background: #dc2626;
}
</style>
