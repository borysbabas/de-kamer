<template>
  <div class="selection-box">
    <select id="options" v-model="selectedOption" @change="selected" class="selectOptions">
      <option v-for="option in options.options" :key="option" :value="option">{{ option }}</option>
    </select>
  </div>
</template>

<script setup lang="ts">
import {ref, defineProps, defineEmits, onMounted} from 'vue';

const props = defineProps({
  options: {
    type: Object as () => string[],
    required: true
  }
});

const emits = defineEmits(['update:selected']);

const selectedOption = ref<string | null>(props.options.selected);

function selected(event: Event) {
  const target = event.target as HTMLSelectElement;
  selectedOption.value = target.value;
  emits('update:selected', target.value);
}

onMounted(() => {
  selectedOption.value = props.options.selected;
});
</script>

<style scoped>
.selection-box {
  display: flex;
  flex-direction: column;
  margin: 10px;
}

select {
  padding: 5px;
  border-radius: 5px;
  font-size: 16px;
}
.selectOptions {
  background-color: var(--button-important);
  color: var(--light-primary);
  border: 10px solid var(--button-important);
  padding: 2px;
  width: auto;
  border-radius: 10px;
  font-size: 20px;
  cursor: pointer;
  font-family: inherit;
  overflow: auto;
  max-height: 200px;
  margin-right: 10px;
}
</style>