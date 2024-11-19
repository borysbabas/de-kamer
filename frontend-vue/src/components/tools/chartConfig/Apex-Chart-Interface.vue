<template>
  <div class="interface-background">
    <div class="interface-layers">
      <p
          v-for="(item, key) of chartData?.data"
          :key="chartData.data.indexOf(item)"
          class="items"
      > {{ item.x || "Unknown" }}
        <div>
          <button class="item-button" @click="hideHightlight(item.id)">Hightlight / Hide</button>
          <button class="item-button" @click="select(key, item.x, item.id)">Compare</button>
        </div></p>
    </div>

  </div>

</template>
<script setup lang="ts">
import { ref, onMounted, watch, defineEmits } from 'vue'

const props = defineProps<{
  chartData: any[{}],
  interface: boolean
}>()

const emit = defineEmits({
  hideHightlight: (Index: number, x: string, id: number) => true,
  select: (Index: number, x: string, id: number) => true
})

function hideHightlight(id: number) {
  emit("hideHightlight", id)
}
function select(id: number) {
  emit("select", id)
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
</style>