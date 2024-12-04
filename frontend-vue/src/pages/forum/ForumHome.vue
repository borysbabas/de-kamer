<script setup lang="ts">
import ForumHero from "@/pages/forum/components/Hero.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
</script>

<template>
  <Navbar></Navbar>
  <ForumHero></ForumHero>
  <section class="main">
    <h1>Recent posts</h1>
    <p>The recent posts from our community</p>
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
  z-index: 0;
}
</style>