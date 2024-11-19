<template>
  <h2>Gemeentes vergelijk-tool</h2>
  <municipality-search></municipality-search>
  <ul>
    <li v-for="municipality in selectedMunicipalities" :key="municipality.municipalityId">
      {{municipality.name}}
    </li>
  </ul>
</template>

<script setup lang="ts">

import MunicipalitySearch from "@/components/MunicipalitySearch.vue";
import {getSelectedMunicipalities} from "@/composables/useMunicipalities";

const selectedMunicipalities = getSelectedMunicipalities();


console.log(selectedMunicipalities);



</script>

<style scoped>
  h2 {
    margin-bottom: 8px;
  }
  ul {
    display: flex;
    flex-direction: column;
  }
</style>