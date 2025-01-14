<template>
  <Navbar></Navbar>
  <Results></Results>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import Results from "@/pages/results/components/Results.vue";
import ApexChartTemplate from "@/components/tools/ApexChartTemplate.vue";
import DataSelectorBar from "@/components/tools/DataSelectorBar.vue";
import LeafletTemplate from "@/components/tools/LeafletTemplate.vue";
</script>

<style scoped>
</style>