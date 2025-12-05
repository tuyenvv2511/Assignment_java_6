<script setup>
import { ref, onMounted } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Pagination from '@/components/Pagination.vue'
import SearchBar from '@/components/SearchBar.vue'

const router = useRouter()
const authStore = useAuthStore()
const invoices = ref([])
const loading = ref(false)
const searchQuery = ref('')
const currentPage = ref(0)
const totalPages = ref(0)

const fetchInvoices = async () => {
  loading.value = true
  try {
    const response = await request.get('/hoa-don', {
      params: {
        page: currentPage.value,
        size: 5,
        keyword: searchQuery.value || undefined
      }
    })
    invoices.value = response.content || []
    totalPages.value = response.totalPages || 0
    currentPage.value = response.number || 0
  } catch (error) {
    console.error('Failed to fetch invoices:', error)
    invoices.value = []
  } finally {
    loading.value = false
  }
}

const handleSearch = (query) => {
  searchQuery.value = query
  currentPage.value = 0
  fetchInvoices()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchInvoices()
}

const viewDetails = (id) => {
  router.push(`/admin/invoices/${id}`)
}

onMounted(() => {
  fetchInvoices()
})
</script>

<template>
  <div class="invoice-list">
    <div class="page-header">
      <h2>Invoice Management</h2>
    </div>

    <SearchBar 
      v-model="searchQuery"
      placeholder="Search by code, recipient name, or phone..."
      @search="handleSearch"
    />

    <div class="card">
      <div v-if="loading" class="loading">Loading...</div>
      
      <table v-else class="data-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>CODE</th>
            <th>CUSTOMER</th>
            <th>EMPLOYEE</th>
            <th>DATE</th>
            <th>STATUS</th>
            <th>TOTAL</th>
            <th>ACTIONS</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="invoices.length === 0">
            <td colspan="8" class="text-center">No invoices found</td>
          </tr>
          <tr v-for="invoice in invoices" :key="invoice.id">
            <td>{{ invoice.id }}</td>
            <td>{{ invoice.ma }}</td>
            <td>{{ invoice.tenKhachHang || 'N/A' }}</td>
            <td>{{ invoice.tenNhanVien || 'N/A' }}</td>
            <td>{{ invoice.ngayTao }}</td>
            <td>
              <span class="badge" :class="'status-' + invoice.tinhTrang">
                {{ invoice.tinhTrang === 0 ? 'Pending' : 'Completed' }}
              </span>
            </td>
            <td>${{ invoice.tongTien }}</td>
            <td>
              <button 
                class="btn btn-sm btn-secondary" 
                @click="viewDetails(invoice.id)"
              >
                View Details
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
.invoice-list {
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

.status-0 {
  background: var(--color-warning);
  color: white;
}

.status-1 {
  background: var(--color-success);
  color: white;
}

.btn-sm {
  padding: 0.25rem 0.75rem;
  font-size: var(--font-size-sm);
}
</style>
