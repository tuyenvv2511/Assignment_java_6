<script setup>
import { computed } from 'vue'

const props = defineProps({
  currentPage: {
    type: Number,
    required: true
  },
  totalPages: {
    type: Number,
    required: true
  }
})

const emit = defineEmits(['page-change'])

const hasPrevious = computed(() => props.currentPage > 0)
const hasNext = computed(() => props.currentPage < props.totalPages - 1)

const goToPage = (page) => {
  if (page >= 0 && page < props.totalPages) {
    emit('page-change', page)
  }
}
</script>

<template>
  <div class="pagination">
    <button 
      class="btn btn-secondary btn-sm" 
      :disabled="!hasPrevious"
      @click="goToPage(currentPage - 1)"
    >
      Previous
    </button>
    
    <span class="page-info">
      Page {{ currentPage + 1 }} of {{ totalPages }}
    </span>
    
    <button 
      class="btn btn-secondary btn-sm" 
      :disabled="!hasNext"
      @click="goToPage(currentPage + 1)"
    >
      Next
    </button>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-md);
  padding: var(--spacing-lg) 0;
}

.page-info {
  font-size: var(--font-size-sm);
  color: var(--color-text-muted);
  min-width: 120px;
  text-align: center;
}

.btn-sm {
  padding: 0.5rem 1rem;
  font-size: var(--font-size-sm);
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
