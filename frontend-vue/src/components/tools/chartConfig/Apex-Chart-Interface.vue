<template>
  <div class="interface-background">
    <div class="interface-layers">
      <div
          v-for="(item, key) in chartData[0]?.data"
          :key="key"
          class="items"
      > {{ item.x || "Unknown" }}
        <p style="display: flex">
          <a class="color-square" :style="{ backgroundColor: item.fillColor }"></a>
<!--          temp disable for review-->
<!--          <button class="item-button" @click="hideHighlight(item.id)">Highlight / Hide</button>-->
<!--          <button class="item-button" @click="select(key, item.x, item.id)">Compare</button>-->
        </p></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineEmits } from 'vue'
import { chartData } from "@/composables/useChartConfig";

const emit = defineEmits({
  // hideHighlight: (index: number, x: string, id: number) => true,
  select: (index: number, x: string, id: number) => true
})

function hideHighlight(id: number) {
  emit("hideHighlight", id)
}

function select(index: number, x: string, id: number) {
  emit("select", index, x, id)
}

</script>

<style scoped>
.interface-background {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 20px;
  padding-left: 30px;
  padding-right: 30px;
  border-radius: 10px;
  margin-top: 10px;
}
.interface-layers {
  background: var(--light-primary);
  padding: 10px;
  height: 100%;
  width: 100%;
  border-radius: 10px;
}
.items {
  color: black;
  border-bottom: black 2px solid;
  padding: 1px;
  display: flex;
  justify-content: space-between;
}
.item-button {
  background-color: var(--button-important);
  color: var(--light-primary);
  border: 1px solid var(--button-important);
  border-radius: 10px;
  font-size: 15px;
  cursor: pointer;
  font-family: inherit;
  padding-left: 10px;
  padding-right: 10px;
  margin-left: 5px;
}
.color-square {
  width: 20px;
  height: 20px;
  border: 1px solid #000; /* Optional: Add a border to make the square more visible */
}
</style>