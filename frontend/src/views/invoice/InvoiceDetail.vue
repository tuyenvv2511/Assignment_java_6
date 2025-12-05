<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()
const loading = ref(false)

const invoice = ref({
  ma: '',
  khachHang: '',
  ngayTao: '',
  tongTien: 0,
  trangThai: 1,
  items: []
})

const fetchInvoice = async () => {
  loading.value = true
  try {
    const response = await request.get(`/hoa-don/${route.params.id}`)
    invoice.value = response
  } catch (error) {
    console.error('Failed to fetch invoice:', error)
    alert('Failed to fetch invoice details')
    router.push('/admin/invoices')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (route.params.id) {
    fetchInvoice()
  }
})

const formatCurrency = (value) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value)
}
</script>

<template>
  <div class="invoice-detail fade-in">
    <div class="page-header">
      <div class="header-left">
        <button @click="router.back()" class="btn btn-secondary btn-sm mb-2">Back</button>
        <h1 class="page-title">Invoice {{ invoice.ma }}</h1>
      </div>
      <div class="header-right">
        <button class="btn btn-primary" onclick="window.print()">Print Invoice</button>
      </div>
    </div>

    <div v-if="loading" class="text-center">Loading...</div>

    <div v-else class="grid-layout">
      <div class="card info-card">
        <h2 class="section-title">General Information</h2>
        <div class="info-row">
          <span class="label">Customer:</span>
          <span class="value">{{ invoice.khachHang || 'N/A' }}</span>
        </div>
        <div class="info-row">
          <span class="label">Employee:</span>
          <span class="value">{{ invoice.nhanVien || 'N/A' }}</span>
        </div>
        <div class="info-row">
          <span class="label">Date:</span>
          <span class="value">{{ invoice.ngayTao }}</span>
        </div>
        <div class="info-row">
          <span class="label">Status:</span>
          <span class="value badge" :class="invoice.tinhTrang === 1 ? 'badge-success' : (invoice.tinhTrang === 0 ? 'badge-warning' : 'badge-danger')">
            {{ invoice.tinhTrang === 1 ? 'Paid' : (invoice.tinhTrang === 0 ? 'Pending' : 'Cancelled') }}
          </span>
        </div>
      </div>

      <div class="card items-card">
        <h2 class="section-title">Order Items</h2>
        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th>Product</th>
                <th>Quantity</th>
                <th>Unit Price</th>
                <th>Total</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in invoice.items" :key="item.idChiTietSP">
                <td>{{ item.tenSP }}</td>
                <td>{{ item.soLuong }}</td>
                <td>{{ formatCurrency(item.donGia) }}</td>
                <td>{{ formatCurrency(item.thanhTien) }}</td>
              </tr>
            </tbody>
            <tfoot>
              <tr class="total-row">
                <td colspan="3" class="text-right">Total Amount:</td>
                <td class="total-value">{{ formatCurrency(invoice.tongTien) }}</td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: var(--spacing-lg);
}

.mb-2 { margin-bottom: var(--spacing-sm); }

.page-title {
  font-size: var(--font-size-2xl);
  font-weight: 700;
}

.grid-layout {
  display: grid;
  gap: var(--spacing-lg);
}

.section-title {
  font-size: var(--font-size-lg);
  font-weight: 600;
  margin-bottom: var(--spacing-md);
  border-bottom: 1px solid var(--color-border);
  padding-bottom: var(--spacing-sm);
}

.info-row {
  display: flex;
  justify-content: space-between;
  padding: var(--spacing-sm) 0;
  border-bottom: 1px dashed var(--color-border);
}

.info-row:last-child {
  border-bottom: none;
}

.label {
  color: var(--color-text-muted);
  font-weight: 500;
}

.value {
  font-weight: 600;
}

.total-row td {
  font-weight: 700;
  font-size: var(--font-size-lg);
  border-top: 2px solid var(--color-border);
}

.text-right {
  text-align: right;
}

.total-value {
  color: var(--color-primary);
}
</style>
