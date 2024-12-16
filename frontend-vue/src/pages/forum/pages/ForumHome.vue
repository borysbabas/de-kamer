<script setup lang="ts">
import ForumHero from "@/pages/forum/components/hero/Hero.vue";
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";
import RecentPosts from "@/pages/forum/components/recent-posts/RecentPosts.vue";
import AddPostButton from "@/pages/forum/components/add-post/AddPostButton.vue";
</script>

<template>
  <Navbar></Navbar>
  <ForumHero></ForumHero>
  <RecentPosts></RecentPosts>
  <AddPostButton></AddPostButton>
  <Footer></Footer>
</template>

<style scoped>
</style>