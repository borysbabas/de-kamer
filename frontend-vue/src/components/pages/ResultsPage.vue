<template>
  <Navbar></Navbar>
  <Results></Results>
  <ChartTemplate :chart-data="result"></ChartTemplate>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";
import Results from "@/components/Results.vue";
import ChartTemplate from "@/components/tools/Chart-Template.vue";
import {onMounted, ref} from "vue";


let result = ref<any[]>([])


async function getChartData() {
  const data = await fetch("http://localhost:3001/affiliation/members/count")
      .then((response) => response.json())
      .then((data) => {
        return data;
      });
  return data;
}
onMounted(async() => {
  result.value = await getChartData()
  console.log(result)
})

</script>

<style scoped>

</style>