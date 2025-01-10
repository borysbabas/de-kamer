<script setup lang="ts">
import {useRoute} from "vue-router";
import {usePostDetails} from "@/composables/usePostDetails";
import {computed} from "vue";
import {marked} from "marked";

const route = useRoute();

const {isLoading, loadedPost} = usePostDetails(route.params.postId);

const renderedDescription = computed(() => {
  if (!loadedPost.value) {
    return "";
  }
  return marked(loadedPost.value.description, {
    renderer: new marked.Renderer(),
    pedantic: false,
    gfm: true,
    breaks: true,
  });
});

</script>

<template>
  <div v-if="isLoading" class="center">
    <div class="loader"></div>
  </div>
  <div v-else-if="loadedPost" class="postItem">
    <div class="postHeader">
      <h1 id="postTitle">{{ loadedPost.title }}</h1>
      <hr>
      <div class="postStats">
        <span>Posted on: {{new Date(loadedPost.dateCreated).toLocaleString()}}</span>
        <span>Asked By: {{ loadedPost.account.userName }}</span>
      </div>
    </div>
    <div class="postContent">
      <div class="postBody">
        <div v-html="renderedDescription" class="post-description markdown-content"></div>
      </div>
    </div>
  </div>
  <div v-else>
    <h1>Post kon niet worden gevonden</h1>
    <router-link to="/forum"><button>Bekijk gemaakte posts</button></router-link>
  </div>
</template>

<style scoped>
@import "@/assets/markdown-content.css";

h1 {
  padding: 0;
}

.postHeader {
  margin-bottom: 32px;
}


.postStats {
  display: flex;
  gap: 30px;
}

.postContent {
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.2);
  border: 1px solid var(--light-primary);
}

.postBody {
  margin-bottom: 20px;
}


</style>