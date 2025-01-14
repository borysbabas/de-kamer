<template>
<navbar></navbar>
  <section>
    <h1 class="intro-title">Ontdekken</h1>
    <intro-items></intro-items>
  </section>
  <Footer></Footer>
</template>


<script setup lang="ts">

import Navbar from "@/pages/navbar/Navbar.vue";
import IntroItems from "@/pages/discover/Components/IntroItems.vue";
import Footer from "@/pages/footer/Footer.vue";
</script>

<style scoped>
.intro-title {
  margin: 0;
  padding: 0;
}
</style>