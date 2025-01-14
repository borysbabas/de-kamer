<script setup lang="ts">

import IntroComptool from "@/pages/discover/Components/IntroComptool.vue";
import IntroHeatmap from "@/pages/discover/Components/IntroHeatmap.vue";
import IntroGraph from "@/pages/discover/Components/IntroGraph.vue";
import IntroListing from "@/pages/discover/Components/IntroListing.vue";
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
  display: grid;
  grid-template-columns: 50% 50%;
  grid-template-rows: 50% 50%;
  justify-content: space-around;
  margin-top: 50px;
}

.intro-item-container {
  padding: 10px;
  margin: 20px;
  box-sizing: border-box;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  border: 3px solid var(--dark-secondary);
  border-radius: 10px;
  flex-grow: 1;
}



@media (max-width: 768px) {
  .intro-container {
    display: flex;
    flex-direction: column;
  }
  .intro-item-container {
    margin: 10px;
  }

}
</style>