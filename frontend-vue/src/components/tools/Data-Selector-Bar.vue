<template>
  <div class="background">
    <div class="border">
      <div class="layer">
        <div v-for="(option, index) in optionsList" :key="index" class="selectContainer">
          <p class="label-text">{{option?.label}}</p>
          <selection-element :options="option?.options" @update:selected="setSelectedValue(index, $event)"></selection-element>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted } from 'vue';
import SelectionElement from "@/components/Selection-Element.vue";
import {fetchChartData, fetchOptions, optionsList, setSelectOption} from "@/composables/useChartDataSelector";

function setSelectedValue(index: number, selectedValue: string) {
  setSelectOption(index, selectedValue);
}


onMounted(() => {
  fetchOptions();
  fetchChartData();
});


</script>
<style scoped>
.background {
  background-color: var(--dark-primary);
  padding: 30px;
}
.layer {
  display: flex;
  justify-content: left;
  margin: 10px;
}
.border {
  padding: 10px;
  border: 10px solid var(--light-primary);
  border-radius: 10px;
  background: var(--light-primary);
}
.label-text {
  color: var(--dark-primary);
  font-size: 20px;
  margin-right: 10px;
}

</style>