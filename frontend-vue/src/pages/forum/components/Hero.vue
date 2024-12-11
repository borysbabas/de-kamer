<script setup lang="ts">
import {onMounted, ref} from 'vue';

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
          <div class="post-title-container">
            <h3 class="post-title">{{ post.title }}</h3>
          </div>
          <div class="post-container">
            <p class="post-description">{{ post.description }}</p>
            <div class="post-info">
              <p class="post-user">Posted by: {{ post.userName }} </p>
              <p class="post-date">{{ post.dateCreated }}</p></div>
          </div>
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
  padding: 20px var(--page-padding);
  position: relative;
  z-index: 0;
}

.top-posts-title {
  margin-top: 20px;
  margin-bottom: 20px;
}

.post-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  list-style: none;
  padding: 0 !important;
  margin-bottom: 50px;
  align-items: start;
}

.post {
  border: 1px solid var(--light-primary);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0;
  transition: transform 0.2s, box-shadow 0.2s;
}

.post:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
}

.post-title-container {
  background-color: var(--dark-secondary);
  padding: 13px 20px;
  border-bottom: 1px solid var(--light-primary);
  min-height: 36px;
  border-radius: 10px 10px 0 0;
}

.post-title {
  font-size: 1.5rem;
  margin: 0;
}

.post-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
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