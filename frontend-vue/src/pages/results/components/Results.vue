<template>
  <section>
    <h1>Uitslagen</h1>
    <compare-municipalities></compare-municipalities>
  </section>
  <section>
    <votes-table></votes-table>
  </section>
</template>

<script setup lang="ts">

import VotesTable from "@/pages/results/components/VotesTable.vue";
import CompareMunicipalities from "@/pages/compare-municipalities/CompareMunicipalities.vue";
</script>

<style scoped>
</style>