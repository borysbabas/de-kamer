<script setup lang="ts">
import ElectionInfo from "@/pages/info/components/ElectionInfo.vue";
import TeamInfo from "@/pages/info/components/TeamInfo.vue";
import Footer from "@/pages/footer/Footer.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
</script>

<template>
  <Navbar></Navbar>
  <section class="info-section">
    <ElectionInfo></ElectionInfo>
    <TeamInfo></TeamInfo>
  </section>
  <Footer></Footer>
</template>

<style scoped>
.info-section {
  padding: 20px;
  background-color: var(--dark-primary);
  color: var(--light-primary);
}
</style>