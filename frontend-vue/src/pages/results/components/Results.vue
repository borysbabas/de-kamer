<template>
  <section class="first main">
    <h1>Uitslagen</h1>
    <compare-municipalities></compare-municipalities>
    <votes-table></votes-table>
  </section>
</template>

<script setup lang="ts">

import VotesTable from "@/components/VotesTable.vue";
import CompareMunicipalities from "@/pages/compare-municipalities/CompareMunicipalities.vue";
</script>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
}

.first {
  padding-top: 150px;
  margin: 0 auto;
}
</style>