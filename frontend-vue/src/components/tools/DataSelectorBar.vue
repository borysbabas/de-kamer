<template>
  <div class="background">
    <div class="border">
      <div class="layer">
        <div v-for="(option, index) in optionsList" :key="index" class="selectContainer">
          <!-- Display the label of the option -->
          <p class="label-text">{{ option?.label }}</p>
          <!-- Custom selection element component -->
          <selection-element :options="option" @update:selected="setSelectedValue(index, $event)"></selection-element>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted} from 'vue';
import SelectionElement from "@/components/Selection-Element.vue";
import {fetchChartData, fetchOptions, optionsList, setSelectOption} from "@/composables/useChartDataSelector";

/**
 * Sets the selected value for a given option.
 * @param {number} index - The index of the option in the optionsList.
 * @param {string} selectedValue - The selected value to set.
 */
function setSelectedValue(index: number, selectedValue: string) {
  setSelectOption(index, selectedValue);
}

// Fetch options and chart data when the component is mounted
onMounted(() => {
  fetchOptions();
  fetchChartData();
});
</script>

<style scoped>
/* Background styling */
.background {
  background-color: var(--dark-primary);
  padding: 30px;
}

/* Layer container styling */
.layer {
  display: flex;
  justify-content: left;
  margin: 10px;
}

/* Border container styling */
.border {
  padding: 10px;
  border: 10px solid var(--light-primary);
  border-radius: 10px;
  background: var(--light-primary);
}

/* Label text styling */
.label-text {
  color: var(--dark-primary);
  font-size: 20px;
  margin-right: 10px;
}

</style>