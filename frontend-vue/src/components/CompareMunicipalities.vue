<script setup lang="ts">

import MunicipalitySearch from "@/components/MunicipalitySearch.vue";
import {useMunicipalities} from "@/composables/useMunicipalities";

const {municipalityList} = useMunicipalities();
console.log(municipalityList);

</script>

<template>
  <h2>Gemeentes vergelijk-tool</h2>
  <municipality-search></municipality-search>
</template>

<style scoped>
  h2 {
    margin-bottom: 8px;
  }
  ul {
    display: flex;
    flex-direction: column;
  }
</style>