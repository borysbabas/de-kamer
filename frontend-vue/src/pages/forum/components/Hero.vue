<script setup lang="ts">
import {onMounted, ref} from 'vue';

const topPosts = ref([]);

const fetchTopPosts = async () => {
  const response = await fetch('http://localhost:3001/forum/posts');
  topPosts.value = await response.json();
};

onMounted(() => {
  fetchTopPosts();
});
</script>

<template>
  <section class="first main">
    <h1>Forum</h1>
    <p>Discuss politics with other users</p>
    <div class="top-posts">
      <h2>Top Posts</h2>
      <ul>
        <li v-for="post in topPosts" :key="post.id">
          <h3>{{ post.title }}</h3>
          <p>{{ post.excerpt }}</p>
        </li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
</style>