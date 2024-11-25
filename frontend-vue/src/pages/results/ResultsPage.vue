<template>
  <Navbar></Navbar>
  <Results></Results>
  <data-selector-bar></data-selector-bar>
  <ApexChartTemplate :interface="true" :chart-type="donut"></ApexChartTemplate>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import Results from "@/pages/results/components/Results.vue";
import ApexChartTemplate from "@/components/tools/Apex-Chart-Template.vue";
import DataSelectorBar from "@/components/tools/Data-Selector-Bar.vue";
</script>

<style scoped>
</style>