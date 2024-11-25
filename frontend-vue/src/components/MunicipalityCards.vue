<script setup lang="ts">
import type {Municipality} from "@/types/Municipality";
import type {PropType} from "vue";

defineProps({
  municipalities: {
    type: Object as PropType<Municipality[]>,
    required: true
  }
})
</script>

<template>
  <div class="cards">
    <div class="card" v-for="municipality in municipalities" :key="municipality">
      <h3>{{municipality.name}}</h3>
      <hr>
      <div class="data">
        <div>Totaal aantal stemmen</div>
        <div>b</div>
        <div>Meest gestemde partij</div>
        <div>b</div>
        <div>Meest gestemde kandidaat</div>
        <div>b</div>

      </div>
    </div>
  </div>
</template>

<style scoped>
  .cards {
    display: flex;
    width: 100%;
    gap: 10px;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .card {
    max-width: 300px;
    width: 100%;
    background-color: var(--light-primary);
    color: #000;
    padding: 10px;
    border-radius: 4px;
  }

  .card h3 {
    font-size: 1.2rem;
    font-weight: 500;
  }

  .data {
    display: grid;
    grid-template-columns: 1fr 2fr;
    gap: 10px;
  }
</style>