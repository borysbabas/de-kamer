<script setup lang="ts">
import PostItem from '../PostItem.vue';

defineProps({
  posts: {
    type: Array,
    required: true
  }
});
</script>

<template>
  <ul class="post-list">
    <PostItem v-for="post in posts" :key="post.postId" :post="post"/>
  </ul>
</template>

<style scoped>
.post-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  list-style: none;
  padding: 0 !important;
  margin-bottom: 50px;
  align-items: start;
}
</style>