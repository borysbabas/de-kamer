<script setup lang="ts">
import {onMounted} from 'vue';
import {usePosts} from '@/composables/usePosts';
import PostList from './PostList.vue';
import Pagination from './Pagination.vue';

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
      <PostList :posts="posts"/>
    </div>
    <Pagination :currentPage="currentPage" :totalPages="totalPages" :goToPage="goToPage"/>
  </section>
</template>

<style scoped lang="scss">
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 0;
}

.forum-title {
  margin-top: 0;
  padding-bottom: 20px !important;
}

.top-posts-title {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>