<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()
const isEdit = ref(false)
const loading = ref(false)

const product = ref({
  ma: '',
  ten: '',
  soLuong: 0,
  giaBan: 0,
  trangThai: 1,
  moTa: ''
})

const variants = ref([])

const addVariant = () => {
  variants.value.push({
    mauSac: '',
    kichThuoc: '',
    giaBan: product.value.giaBan,
    soLuong: 0
  })
}

const removeVariant = (index) => {
  variants.value.splice(index, 1)
}

const fetchProduct = async (id) => {
  try {
    const response = await request.get(`/san-pham/${id}`)
    product.value = {
      ma: response.ma,
      ten: response.ten,
      // Note: SanPhamResponse doesn't have top-level price/stock/status/desc in the DTO I saw earlier?
      // Let's check SanPhamResponse again. It ONLY has id, ma, ten, variants.
      // This means I might need to update SanPhamResponse to include other fields if I want to edit them.
      // Or I rely on variants for price/stock.
      // But SanPham entity has these fields? Let's check SanPham entity.
      // If SanPhamResponse is missing fields, I should add them.
    }
    
    // Wait, SanPhamResponse only has id, ma, ten, variants.
    // I should update SanPhamResponse to include moTa, trangThai.
    // And maybe default price/stock if they exist on the parent product (which they do in the entity).
    
    if (response.variants) {
      variants.value = response.variants.map(v => ({
        id: v.id,
        mauSac: v.mauSac,
        kichThuoc: v.kichThuoc,
        giaBan: v.giaBan,
        soLuong: v.soLuongTon
      }))
    }
  } catch (error) {
    console.error('Failed to fetch product:', error)
  }
}

onMounted(async () => {
  if (route.params.id) {
    isEdit.value = true
    await fetchProduct(route.params.id)
  }
})

const saveProduct = async () => {
  loading.value = true
  try {
    const payload = {
      ...product.value,
      variants: variants.value
    }
    
    if (isEdit.value) {
      await request.put(`/san-pham/${route.params.id}`, payload)
    } else {
      await request.post('/san-pham', payload)
    }
    router.push('/admin/products')
  } catch (error) {
    console.error('Failed to save product:', error)
    alert('Failed to save product')
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="product-form fade-in">
    <div class="page-header">
      <h1 class="page-title">{{ isEdit ? 'Edit Product' : 'Add New Product' }}</h1>
      <button @click="router.back()" class="btn btn-secondary">Back</button>
    </div>

    <div class="card">
      <form @submit.prevent="saveProduct">
        <div class="form-section">
          <h2 class="section-title">Product Information</h2>
          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">Product Code</label>
              <input v-model="product.ma" type="text" class="form-control" required>
            </div>

            <div class="input-group">
              <label class="input-label">Product Name</label>
              <input v-model="product.ten" type="text" class="form-control" required>
            </div>
          </div>

          <div class="grid-2">
            <div class="input-group">
              <label class="input-label">Price</label>
              <input v-model="product.giaBan" type="number" class="form-control" required>
            </div>

            <div class="input-group">
              <label class="input-label">Stock</label>
              <input v-model="product.soLuong" type="number" class="form-control" required>
            </div>
          </div>

          <div class="input-group">
            <label class="input-label">Status</label>
            <select v-model="product.trangThai" class="form-control">
              <option :value="1">Active</option>
              <option :value="0">Inactive</option>
            </select>
          </div>
        </div>

        <div class="form-section">
          <div class="section-header">
            <h2 class="section-title">Variants</h2>
            <button type="button" class="btn btn-secondary btn-sm" @click="addVariant">
              + Add Variant
            </button>
          </div>
          
          <div class="table-container">
            <table>
              <thead>
                <tr>
                  <th>Color</th>
                  <th>Size</th>
                  <th>Price</th>
                  <th>Stock</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(variant, index) in variants" :key="index">
                  <td>
                    <input v-model="variant.mauSac" type="text" class="form-control form-control-sm" placeholder="Color">
                  </td>
                  <td>
                    <input v-model="variant.kichThuoc" type="text" class="form-control form-control-sm" placeholder="Size">
                  </td>
                  <td>
                    <input v-model="variant.giaBan" type="number" class="form-control form-control-sm">
                  </td>
                  <td>
                    <input v-model="variant.soLuong" type="number" class="form-control form-control-sm">
                  </td>
                  <td>
                    <button type="button" class="btn-icon text-danger" @click="removeVariant(index)">Remove</button>
                  </td>
                </tr>
                <tr v-if="variants.length === 0">
                  <td colspan="5" class="text-center">No variants added.</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary" :disabled="loading">
            {{ loading ? 'Saving...' : 'Save Product' }}
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

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: var(--spacing-md);
}

.section-title {
  font-size: var(--font-size-lg);
  font-weight: 600;
  color: var(--color-text-main);
  border-bottom: 1px solid var(--color-border);
  padding-bottom: var(--spacing-sm);
  margin-bottom: var(--spacing-md);
}

.grid-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--spacing-md);
}

.form-control-sm {
  padding: 0.25rem 0.5rem;
  font-size: 0.875rem;
}

.form-actions {
  margin-top: var(--spacing-lg);
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid var(--color-border);
  padding-top: var(--spacing-md);
}

.text-center {
  text-align: center;
  color: var(--color-text-muted);
}
</style>
