<script setup lang="ts">
import {options, selectedOption, triggerDropdown} from "@/composables/useDropdown";
</script>

<template>
  <div class="dropdown">
    <select v-model="selectedOption" class="dropdown-select" ref="dropdownSelect">
      <option v-for="option in options" :key="option.value" :value="option.value" v-html="option.label"></option>
    </select>
    <svg xmlns="http://www.w3.org/2000/svg" class="angle-down-icon" viewBox="0 0 448 512" @click="triggerDropdown">
      <path
          d="M201.4 374.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 306.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"/>
    </svg>
  </div>
</template>

<style scoped>
.dropdown {
  display: flex;
  align-items: center;
  position: relative;
  width: fit-content;
  margin-bottom: 30px;
}

.dropdown-select {
  padding: 0;
  font-size: 38px;
  appearance: none;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-family: inherit;
}

.dropdown-select:focus {
  outline: none;
}
</style>