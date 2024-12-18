<script setup lang="ts">
defineProps({
  currentPage: {
    type: Number,
    required: true
  },
  totalPages: {
    type: Number,
    required: true
  },
  goToPage: {
    type: Function,
    required: true
  }
});
</script>

<template>
  <div class="pagination">
    <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 1">Vorige</button>
    <span>Pagina {{ currentPage }} van {{ totalPages }}</span>
    <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages">Volgende</button>
  </div>
</template>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
  position: sticky;
  bottom: 0;
  background-color: var(--dark-primary);
  padding: 10px 0;
  border-radius: 10px 10px 0 0;
  border: 1px solid var(--light-primary);
  border-bottom: none;
}

.pagination button {
  padding: 5px 10px;
  cursor: pointer;
  border: none;
  background-color: var(--button-hover);
  color: var(--light-primary);
  border-radius: 5px;
  transition: background-color 0.3s;
}

.pagination button:hover {
  background-color: var(--button);
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
</style>