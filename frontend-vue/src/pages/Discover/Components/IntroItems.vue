<script setup lang="ts">

import IntroComptool from "@/pages/Discover/Components/IntroComptool.vue";
import IntroHeatmap from "@/pages/Discover/Components/IntroHeatmap.vue";
import IntroGraph from "@/pages/Discover/Components/IntroGraph.vue";
import IntroListing from "@/pages/Discover/Components/IntroListing.vue";
</script>

<template>
<div class="intro-container">
    <IntroComptool></IntroComptool>
    <intro-graph></intro-graph>
    <intro-listing></intro-listing>
    <intro-heatmap></intro-heatmap>
</div>
</template>

<style scoped>
.intro-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 50px;
}
.intro-item-container {
  padding: 10px;
  margin: 20px;
  max-width: 45%;
  box-sizing: border-box;
  height: auto;
  justify-content: center;
  align-items: center;
  border : 3px solid var(--dark-secondary);
  border-radius: 10px;
  flex-grow: 1;
}

</style>