<script lang="ts">
import {onMounted, ref} from "vue";

let constituencies = ref<any[]>([]);

async function fetchConstituencies() {
  try {
    constituencies.value = await fetch("http://localhost:3001/constituencies").then(response => response.json().then(
        (data) => {
          console.log(data);
          return data;
        }
    ));
  } catch (error) {
    console.log(error);
  }
}

function test(constituency) {
  console.log(constituency.constituencyName);
}

const result = await fetchConstituencies();
constituencies.value.forEach(test)
</script>

<template>
  <div>
    <select>
      <option>-- Kies een kieskring --</option>
      <option v-for="constituency in constituencies" :key="constituency.constituencyId" :value="constituency.constituencyId">
        {{ constituency.constituencyName }}
      </option>
    </select>
  </div>
</template>

<style scoped>
</style>