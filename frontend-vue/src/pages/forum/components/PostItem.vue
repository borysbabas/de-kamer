<script setup lang="ts">
import {computed, defineProps, type PropType} from "vue";
import {useRouter} from 'vue-router';
import {marked} from "marked";
import hljs from "highlight.js";
import type {Post} from "@/types/Post";

const router = useRouter();
const props = defineProps({
  post: {
    type: Object as PropType<Post>,
    required: true
  }
});

const renderedDescription = computed(() => {
  return marked(props.post.description, {
    renderer: new marked.Renderer(),
    highlight: function (code, lang) {
      if (lang && hljs.getLanguage(lang)) {
        return hljs.highlight(code, {language: lang}).value;
      }
      return hljs.highlightAuto(code).value;
    },
    langPrefix: "hljs language-",
    pedantic: false,
    gfm: true,
    breaks: true,
    sanitize: false,
    smartLists: true,
    smartypants: false,
    xhtml: false,
  });
});

function goToPost() {
  router.push({name: 'PostDetails', params: {postId: props.post.id}});
}
</script>

<template>
  <li class="post" @click="goToPost">
    <div class="post-title-container">
      <h3 class="post-title">{{ post.title }}</h3>
    </div>
    <div class="post-container">
      <div v-html="renderedDescription" class="post-description markdown-content"></div>
      <div class="post-info">
        <p class="post-user">Gepost door: {{ post.account.userName }}</p>
        <p class="post-date">op {{ post.dateCreated }}</p>
      </div>
    </div>
  </li>
</template>

<style scoped>
@import "@/assets/markdown-content.css";

.post {
  border: 1px solid var(--light-primary);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0;
  height: fit-content;
  transition: transform 0.2s, box-shadow 0.2s;
  overflow: hidden;
  cursor: pointer;
}

.post:hover {
  transform: scale(1.015);
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
  justify-content: space-between;
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
  line-clamp: 3;
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
</style>