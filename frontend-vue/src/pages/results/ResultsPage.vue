<template>
  <Navbar></Navbar>
  <Results></Results>
  <leaflet-template-polygons></leaflet-template-polygons>
  <data-selector-bar></data-selector-bar>
  <ApexChartTemplate :interface="true"></ApexChartTemplate>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import Results from "@/pages/results/components/Results.vue";
import ApexChartTemplate from "@/components/tools/ApexChartTemplate.vue";
import DataSelectorBar from "@/components/tools/DataSelectorBar.vue";
import LeafletTemplatePolygons from "@/components/tools/LeafletTemplatePolygons.vue";
</script>

<style scoped>
</style>