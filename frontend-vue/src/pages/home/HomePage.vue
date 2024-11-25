<script setup lang="ts">
import Hero from "@/pages/home/components/Hero.vue";
import Navbar from "@/pages/navbar/components/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import AboutUs from "@/pages/home/components/AboutUs.vue";
import Graph from "@/pages/home/components/Graph.vue";
import {onMounted} from "vue";
import {fetchCandidateVotes, fetchParties, fetchPartyImages} from "@/composables/useFetch";

onMounted(() => {
  fetchParties()
  fetchCandidateVotes();
  fetchPartyImages();
});
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Beiruti:wght@200..900&display=swap" rel="stylesheet">

  <Navbar></Navbar>
  <Hero></Hero>
  <section class="main">
    <AboutUs></AboutUs>
  </section>
  <section class="last main">
    <Graph></Graph>
  </section>
  <Footer></Footer>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  border-bottom: var(--light-primary) 1px solid;
  z-index: 0;
}

.last {
  padding-bottom: 50px;
  border-bottom: none;
}
</style>