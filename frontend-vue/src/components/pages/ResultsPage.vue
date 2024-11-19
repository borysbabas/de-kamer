<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";
import Results from "@/components/Results.vue";
import ChartTemplate from "@/components/tools/Chart-Template.vue";
</script>

<template>
  <Navbar></Navbar>
  <Results></Results>
  <ApexChartTemplate :chart-data="result" :interface="true" :chart-type="donut"></ApexChartTemplate>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";
import Results from "@/components/Results.vue";
import ApexChartTemplate from "@/components/tools/Apex-Chart-Template.vue";
import {onMounted, ref} from "vue";


let result = ref<any[]>([])

// Temporary data source
async function getChartData() {
  return await fetch("http://localhost:3001/affiliation/members/count")
      .then((response) => response.json())
      .then((data) => {
        return data;
      });
}

async function getChartData2() {
  return await fetch("http://localhost:3001/votes/affiliations")
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