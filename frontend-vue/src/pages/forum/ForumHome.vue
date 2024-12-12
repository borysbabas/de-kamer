<script setup lang="ts">
import {onMounted, ref} from 'vue';
import ForumHero from "@/pages/forum/components/Hero.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";

const posts = ref<any[]>([]);

// async function fetchPosts() {
//   // Replace with your API endpoint
//   const response = await fetch('http://localhost:3001/forum/posts');
//   posts.value = await response.json();
// }

onMounted(() => {
  // fetchPosts();
});
</script>

<template>
  <Navbar></Navbar>
  <section class="first main">
    <ForumHero></ForumHero>
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

.first {
  padding-top: 150px !important;
  margin: 0 auto;
}
</style>