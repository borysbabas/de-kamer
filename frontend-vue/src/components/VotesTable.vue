<script setup lang="ts">
import {ref} from "vue";

type Constituency = {
  constituencyId: number;
  constituencyName: string;
}

const constituencies = ref<Constituency[]>([]);

async function fetchConstituencies() {
  try {
    const result = await fetch("http://localhost:3001/constituencies");

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituencies");
    }

    constituencies.value = await result.json();
    console.log(constituencies)
  } catch (error) {
    console.log(error);
  }
}

void fetchConstituencies();
</script>

<template>
  <select>
    <option disabled selected>-- Kies een kieskring --</option>
    <option v-for="constituency in constituencies" :key="constituency.constituencyId" :value="constituency.constituencyId">
      {{ constituency.constituencyName }}
    </option>
  </select>
</template>

<style scoped>
</style>