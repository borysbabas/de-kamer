<template>
  <div class="interface-background">
    <div class="interface-layers">
      <!-- Loop through chartData items and create a div for each item -->
      <div
          v-for="(item, key) in chartData?.data"
          :key="key"
          class="items"
      > {{ item.x || "Unknown" }}
        <p style="display: flex">
          <!-- Color square representing the item's fill color -->
          <a class="color-square" :style="{ backgroundColor: item.fillColor }"></a>
          <!-- Buttons for highlighting/hiding and comparing items (temporarily disabled) -->
          <!-- <button class="item-button" @click="hideHighlight(item.id)">Highlight / Hide</button> -->
          <!-- <button class="item-button" @click="select(key, item.x, item.id)">Compare</button> -->
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineEmits } from 'vue'
import { chartData } from "@/composables/useChartConfig";

// Define the emit events for the component
const emit = defineEmits({
  // hideHighlight: (index: number, x: string, id: number) => true,
  select: (index: number, x: string, id: number) => true
})

/**
 * Emits the hideHighlight event with the given item ID.
 * @param {number} id - The ID of the item to hide the highlight for.
 */
function hideHighlight(id: number) {
  emit("hideHighlight", id)
}

/**
 * Emits the select event with the given item details.
 * @param {number} index - The index of the item.
 * @param {string} x - The x value of the item.
 * @param {number} id - The ID of the item.
 */
function select(index: number, x: string, id: number) {
  emit("select", index, x, id)
}

</script>

<style scoped>
/* Styling for the interface background */
.interface-background {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 20px 30px;
  border-radius: 10px;
  margin-top: 10px;
}

/* Styling for the interface layers container */
.interface-layers {
  background: var(--light-primary);
  padding: 10px;
  width: 100%;
  border-radius: 10px;
  max-height: 80vh;
  overflow-y: auto;
}

/* Styling for each item in the interface */
.items {
  color: black;
  border-bottom: black 2px solid;
  padding: 1px;
  display: flex;
  justify-content: space-between;
}

/* Styling for the item buttons */
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

/* Styling for the color square */
.color-square {
  width: 20px;
  height: 20px;
  border: 1px solid #000;
}
</style>