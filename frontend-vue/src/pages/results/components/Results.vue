<template>
  <section>
    <div class="introduction">
      <h1>Uitslagen</h1>
      <p>Op deze pagina staat informatie over de uitslagen van de Tweede Kamerverkiezingen 2021. </p>

    </div>
    <compare-municipalities></compare-municipalities>
  </section>
  <section>
    <votes-table></votes-table>
  </section>
  <section>
    <leaflet-template></leaflet-template>
  </section>
  <section>
    <data-selector-bar></data-selector-bar>
    <ApexChartTemplate :interface="true"></ApexChartTemplate>
  </section>
</template>

<script setup lang="ts">

import VotesTable from "@/pages/results/components/VotesTable.vue";
import CompareMunicipalities from "@/pages/compare-municipalities/CompareMunicipalities.vue";
import DataSelectorBar from "@/components/tools/DataSelectorBar.vue";
import LeafletTemplate from "@/components/tools/LeafletTemplate.vue";
import ApexChartTemplate from "@/components/tools/ApexChartTemplate.vue";
</script>

<style scoped>
h1 {
  padding: 0;
  margin: 0;
}

.introduction {
  margin-bottom: 30px;
}
</style>