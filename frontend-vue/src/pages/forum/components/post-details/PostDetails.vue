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

console.log(loadedPost);

</script>

<template>
  <div v-if="isLoading" class="center">
    <div class="loader"></div>
  </div>
  <div v-else-if="loadedPost">
    <h1>{{loadedPost.title}}</h1>
    <div v-html="renderedDescription" class="post-description markdown-content"></div>
  </div>
  <div v-else>
    <h1>Post kon niet worden gevonden</h1>
    <router-link to="/forum"><button>Bekijk gemaakte posts</button></router-link>
  </div>
</template>

<style scoped>
@import "@/assets/markdown-content.css";
</style>