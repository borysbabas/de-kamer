<script setup lang="ts">
import {onMounted, ref} from "vue";
import ForumHero from "@/pages/forum/components/Hero.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";

const posts = ref<any[]>([]);

// async function fetchPosts() {
//   // Replace with your API endpoint
//   const response = await fetch(import.meta.env.VITE_API_URL + "/forum/posts");
//   posts.value = await response.json();
// }

onMounted(() => {
  // fetchPosts();
});
</script>

<template>
  <Navbar></Navbar>
  <section>
    <ForumHero></ForumHero>
  </section>
  <Footer></Footer>
</template>

<style scoped>

</style>