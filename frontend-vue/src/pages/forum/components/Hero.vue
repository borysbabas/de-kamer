<script setup lang="ts">
import {onMounted} from 'vue';
import {usePosts} from '@/composables/usePosts';

const {posts, currentPage, totalPages, fetchPosts, goToPage} = usePosts();

onMounted(() => {
  fetchPosts();
});
</script>

<template>
  <section class="first main">
    <h1 class="forum-title">Forum</h1>
    <div class="top-posts">
      <h2 class="top-posts-title">Top Posts</h2>
      <ul class="post-list">
        <li class="post" v-for="post in posts" :key="post.postId">
          <div class="post-title-container">
            <h3 class="post-title">{{ post.title }}</h3>
          </div>
          <div class="post-container">
            <p class="post-description">{{ post.description }}</p>
            <div class="post-info">
              <p class="post-user">Posted by: {{ post.userName }} </p>
              <p class="post-date">{{ post.dateCreated }}</p>
            </div>
          </div>
        </li>
      </ul>
      <div class="pagination">
        <button @click="goToPage(currentPage - 1)" :disabled="currentPage === 1">Previous</button>
        <span>Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="goToPage(currentPage + 1)" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
  </section>
</template>

<style scoped lang="scss">
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 20px var(--page-padding);
  position: relative;
  z-index: 0;
}

.forum-title {
  margin-top: 0;
  padding-bottom: 20px !important;
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
  height: 200px;
  transition: transform 0.2s, box-shadow 0.2s;
  overflow: hidden;
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
  flex-grow: 1;
}

.post-description {
  font-size: 1rem;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  line-clamp: 0;
  -webkit-box-orient: vertical;
}

.post-user {
  font-size: 0.8rem;
  margin: 0;
}

.post-date {
  font-size: 0.8rem;
  margin: 0;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
  position: sticky;
  bottom: 0;
  background-color: var(--dark-primary);
  padding: 10px 0;
  border-radius: 10px 10px 0 0;
  border: 1px solid var(--light-primary);
  border-bottom: none;
  width: 100%
}

.pagination button {
  padding: 5px 10px;
  cursor: pointer;
  border: none;
  background-color: var(--button-hover);
  color: var(--light-primary);
  border-radius: 5px;
  transition: background-color 0.3s;
}

.pagination button:hover {
  background-color: var(--button);
}

.pagination button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}
</style>