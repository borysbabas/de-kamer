<template>
  <section class="recent-posts">
    <h1>Recent posts</h1>
    <p>The recent posts from our community</p>
    <PostList :posts="posts" />
    <Pagination :go-to-page="goToPage" :total-pages="totalPages" :current-page="currentPage" />
  </section>
</template>

<script setup lang="ts">
import {ref} from "vue";
import PostList from "@/pages/forum/components/PostList.vue";
import Pagination from "@/pages/forum/components/Pagination.vue";

const posts = ref<any[]>([]);
const totalPages = ref<number>(0);
const postsPerPage = 8;
const currentPage = ref<number>(1);

// Fetch functions
async function fetchPostsCount() {
  try {
    const response = await fetch("http://localhost:3001/posts/count");
    totalPages.value = Math.ceil(await response.json() / 8);
  } catch (error) {
    console.log("Error fetching posts count: " + error);
  }
}

async function fetchRecentPosts() {
  try {
    const response = await fetch(`http://localhost:3001/posts/recent/${postsPerPage}/${currentPage.value}`);
    const data = await response.json();
    data.forEach((post: any) => {
      post.dateCreated = new Date(post.dateCreated).toLocaleString();
    });
    posts.value = data;
  } catch (error) {
    console.log("Error fetching recent posts: " + error)
  }
}

// Handle frontend functions
function goToPage (page: number) {
  if (page <= totalPages.value && page > 1) {
    currentPage.value = page;
    fetchRecentPosts();
  }
}

fetchPostsCount();
fetchRecentPosts();
</script>

<style scoped>
.recent-posts {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
}
</style>