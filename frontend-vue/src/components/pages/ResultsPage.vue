<template>
  <Navbar></Navbar>
  <Results></Results>
  <data-selector-bar></data-selector-bar>
  <ApexChartTemplate :chart-data="result" :interface="true" :chart-type="donut"></ApexChartTemplate>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";
import Results from "@/components/Results.vue";
import ApexChartTemplate from "@/components/tools/ApexChartTemplate.vue";
import {onMounted, ref} from "vue";
import DataSelectorBar from "@/components/tools/DataSelectorBar.vue";


let result = ref<any[]>([])

// Temporary data source
async function getChartData() {
  return await fetch(import.meta.env.VITE_API_URL + "/affiliation/members/count")
      .then((response) => response.json())
      .then((data) => {
        return data;
      });
}

async function getChartData2() {
  return await fetch(import.meta.env.VITE_API_URL + "/votes/affiliations")
      .then((response) => response.json())
      .then((data) => {
        return data;
      });
}
onMounted(async() => {
  result.value = {
    members: await getChartData(),
    votes: await getChartData2()
  }
})

</script>

<style scoped>
</style>