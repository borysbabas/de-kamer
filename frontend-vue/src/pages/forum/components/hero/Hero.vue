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
  <section class="top-posts">
    <div class="top-posts">
      <h1 class="top-posts-title">Top Posts</h1>
      <PostList :posts="posts"/>
    </div>
    <Pagination :currentPage="currentPage" :totalPages="totalPages" :goToPage="goToPage"/>
  </section>
</template>

<style scoped lang="scss">
.top-posts {
  margin-bottom: 0;
  padding-bottom: 0;
}

.top-posts-title {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>