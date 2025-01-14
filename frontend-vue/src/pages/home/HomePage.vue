<script setup lang="ts">
import Hero from "@/pages/home/components/Hero.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import AboutUs from "@/pages/home/components/AboutUs.vue";
import {onMounted} from "vue";
import {fetchCandidateVotes, fetchParties} from "@/composables/useFetch";
import WelcomeBar from "@/pages/home/components/WelcomeBar.vue";

onMounted(() => {
  fetchParties()
  fetchCandidateVotes();
});
</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Beiruti:wght@200..900&display=swap" rel="stylesheet">
  <Navbar></Navbar>
  <Hero></Hero>
  <AboutUs></AboutUs>
  <Footer></Footer>
</template>

<style scoped>

</style>