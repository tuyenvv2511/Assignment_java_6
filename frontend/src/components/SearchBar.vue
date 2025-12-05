<script setup>
import { ref } from 'vue'

const props = defineProps({
  placeholder: {
    type: String,
    default: 'Search...'
  },
  modelValue: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['update:modelValue', 'search'])

const localValue = ref(props.modelValue)

const handleInput = (event) => {
  localValue.value = event.target.value
  emit('update:modelValue', localValue.value)
}

const handleSearch = () => {
  emit('search', localValue.value)
}

const handleKeyup = (event) => {
  if (event.key === 'Enter') {
    handleSearch()
  }
}
</script>

<template>
  <div class="search-bar">
    <input 
      type="text"
      class="form-control search-input"
      :placeholder="placeholder"
      :value="localValue"
      @input="handleInput"
      @keyup="handleKeyup"
    />
    <button class="btn btn-primary" @click="handleSearch">
      🔍 Search
    </button>
  </div>
</template>

<style scoped>
.search-bar {
  display: flex;
  gap: var(--spacing-sm);
  margin-bottom: var(--spacing-lg);
}

.search-input {
  flex: 1;
  max-width: 400px;
}

.form-control {
  padding: var(--spacing-sm) var(--spacing-md);
  border: 1px solid var(--color-border);
  border-radius: var(--radius-md);
  font-size: var(--font-size-base);
  transition: all var(--transition-fast);
}

.form-control:focus {
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}
</style>
