<template>
  <section class="recent-posts">
    <h1 class="recent-posts-title">Recente posts</h1>
    <PostList :posts="posts"/>
    <Pagination :go-to-page="goToPage" :total-pages="totalPages" :current-page="currentPage"/>
  </section>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
import PostList from "@/pages/forum/components/hero/PostList.vue";
import Pagination from "@/pages/forum/components/hero/Pagination.vue";

const posts = ref<any[]>([]);
const totalPages = ref<number>(0);
const postsPerPage = 8;
const currentPage = ref<number>(1);

// Fetch functions
async function fetchPostsCount() {
  try {
    const response = await fetch(import.meta.env.VITE_API_URL + "/posts/count");
    totalPages.value = Math.ceil(await response.json() / 8);
  } catch (error) {
    console.log("Error fetching posts count: " + error);
  }
}

async function fetchRecentPosts() {
  try {
    const response = await fetch(import.meta.env.VITE_API_URL + `/posts/recent/${postsPerPage}/${currentPage.value}`);
    const data = await response.json();
    data.forEach((post: any) => {
      post.dateCreated = new Date(post.dateCreated).toLocaleString();
    });
    posts.value = data;
    console.log(posts.value);
  } catch (error) {
    console.log("Error fetching recent posts: " + error)
  }
}

// Handle frontend functions
function goToPage(page: number) {
  if (page <= totalPages.value && page >= 1) {
    currentPage.value = page;
    fetchRecentPosts();
  }
}

onMounted(() => {
  fetchPostsCount();
  fetchRecentPosts();
});
</script>

<style scoped>
.recent-posts {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-side-padding);
  position: relative;
  z-index: 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.recent-posts-title {
  margin-top: 20px;
  padding-bottom: 20px !important;
}
</style>