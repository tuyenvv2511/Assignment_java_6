<script setup>
import { ref, onMounted, watch } from 'vue'
import request from '@/utils/request'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import Pagination from '@/components/Pagination.vue'
import SearchBar from '@/components/SearchBar.vue'

const router = useRouter()
const authStore = useAuthStore()
const products = ref([])
const loading = ref(false)
const searchQuery = ref('')
const currentPage = ref(0)
const totalPages = ref(0)

const fetchProducts = async () => {
  loading.value = true
  try {
    const response = await request.get('/san-pham', {
      params: {
        page: currentPage.value,
        size: 5,
        keyword: searchQuery.value || undefined
      }
    })
    products.value = response.content || []
    totalPages.value = response.totalPages || 0
    currentPage.value = response.number || 0
  } catch (error) {
    console.error('Failed to fetch products:', error)
    products.value = []
  } finally {
    loading.value = false
  }
}

const handleSearch = (query) => {
  searchQuery.value = query
  currentPage.value = 0
  fetchProducts()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchProducts()
}

const handleDelete = async (id) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await request.delete(`/san-pham/${id}`)
    fetchProducts()
  } catch (error) {
    console.error('Failed to delete product:', error)
    alert('Failed to delete product')
  }
}

onMounted(() => {
  fetchProducts()
})
</script>

<template>
  <div class="product-list">
    <div class="page-header">
      <h2>Product Management</h2>
      <button 
        v-if="authStore.isAdmin" 
        class="btn btn-primary" 
        @click="router.push('/admin/products/add')"
      >
        + Add New Product
      </button>
    </div>

    <SearchBar 
      v-model="searchQuery"
      placeholder="Search by code or name..."
      @search="handleSearch"
    />

    <div class="card">
      <div v-if="loading" class="loading">Loading...</div>
      
      <table v-else class="data-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>CODE</th>
            <th>NAME</th>
            <th>VARIANTS</th>
            <th v-if="authStore.isAdmin">ACTIONS</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="products.length === 0">
            <td :colspan="authStore.isAdmin ? 5 : 4" class="text-center">
              No products found
            </td>
          </tr>
          <tr v-for="product in products" :key="product.id">
            <td>{{ product.id }}</td>
            <td>{{ product.ma }}</td>
            <td>{{ product.ten }}</td>
            <td>
              <span class="badge" v-for="(variant, index) in product.variants" :key="index">
                {{ variant.mauSac }} - ${{ variant.giaBan }}
              </span>
            </td>
            <td v-if="authStore.isAdmin">
              <button 
                class="btn btn-sm btn-secondary" 
                @click="router.push(`/admin/products/edit/${product.id}`)"
              >
                Edit
              </button>
              <button 
                class="btn btn-sm btn-danger" 
                @click="handleDelete(product.id)"
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
.product-list {
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
  margin-right: 0.25rem;
  background: var(--color-primary);
  color: white;
  border-radius: var(--radius-sm);
  font-size: 0.75rem;
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
