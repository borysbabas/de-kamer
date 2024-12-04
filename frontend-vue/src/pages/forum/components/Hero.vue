<script setup lang="ts">
import {onMounted, ref} from 'vue';

const topPosts = ref<any[]>([]);

async function fetchTopPosts() {
  const response = await fetch('http://localhost:3001/posts');
  const data = await response.json();
  console.log(data);
  topPosts.value = data;
}

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
        <li v-for="post in topPosts" :key="post.postId">
          <h3>{{ post.title }}</h3>
          <p>{{ post.description }}</p>
          <p>Posted by User ID: {{ post.userId }} on {{ post.dateCreated }}</p>
        </li>
      </ul>
    </div>
  </section>
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