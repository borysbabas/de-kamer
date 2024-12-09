<script setup lang="ts">
import { onMounted, ref } from 'vue';

const topPosts = ref<any[]>([]);

async function fetchTopPosts() {
  const response = await fetch('http://localhost:3001/posts');
  const data = await response.json();
  data.forEach((post: any) => {
    post.dateCreated = new Date(post.dateCreated).toLocaleString();
  });
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
      <h2 class="top-posts-title">Top Posts</h2>
      <ul class="post-list">
        <li class="post" v-for="post in topPosts" :key="post.postId">
          <h3 class="post-title">{{ post.title }}</h3>
          <p class="post-description">{{ post.description }}</p>
          <div class="post-info">
          <p class="post-user">Posted by User ID: {{ post.userId }} </p>
            <p class="post-date">{{ post.dateCreated }}</p></div>
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

.top-posts-title {
  margin-top: 20px;
  margin-bottom: 20px;
}

.post-list {
  display: flex;
  flex-wrap: wrap;
  list-style: none;
  padding: 0;
}

.post {
  border: 1px solid var(--light-primary);
  border-radius: 10px;
  width: 200px;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.post-title {
  font-size: 1.5rem;
  margin: 0;
}

.post-description {
  font-size: 1rem;
  margin: 0;
}

.post-user {
  font-size: 0.8rem;
  margin: 0;
}

.post-date {
  font-size: 0.8rem;
  margin: 0;
}
</style>