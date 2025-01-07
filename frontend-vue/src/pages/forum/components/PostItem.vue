<script setup lang="ts">
import {computed, defineProps} from "vue";
import {useRouter} from 'vue-router';
import {marked} from "marked";
import hljs from "highlight.js";

const router = useRouter();

const props = defineProps({
  post: {
    type: Object,
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
        <p class="post-user">Gepost door: {{ post.userName }}</p>
        <p class="post-date">op {{ post.dateCreated }}</p>
      </div>
    </div>
  </li>
</template>

<style scoped>
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

/* Styles for markdown content */

:deep(.markdown-content h1) {
  font-size: 2em;
  margin: 1rem 0;
  padding: 0;
}

:deep(.markdown-content h2) {
  font-size: 1.5em;
  margin: 0.75em 0;
}

:deep(.markdown-content h3) {
  font-size: 1.17em;
  margin: 0.83em 0;
}

:deep(.markdown-content h4) {
  font-size: 1em;
  margin: 1.12em 0;
}

:deep(.markdown-content h5) {
  font-size: 0.83em;
  margin: 1.5em 0;
}

:deep(.markdown-content h6) {
  font-size: 0.75em;
  margin: 1.67em 0;
}

:deep(.markdown-content em) {
  margin: 1em 0;
  font-style: italic;
}

:deep(.markdown-content p) {
  margin: 1em 0;
}

:deep(.markdown-content a) {
  color: var(--link-color);
  text-decoration: none;
}

:deep(.markdown-content a:hover) {
  text-decoration: underline;
}

:deep(.markdown-content blockquote) {
  margin: 1em 0;
  padding: 0.5em 10px;
  border-left: 5px solid var(--blockquote-border);
  background-color: var(--blockquote-bg);
  color: var(--light-primary);
}

:deep(.markdown-content ul) {
  list-style-type: disc;
  padding-left: 40px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}

:deep(.markdown-content ul li) {
  margin: 0;
  padding: 0;
}

:deep(.markdown-content ol) {
  list-style-type: decimal;
  margin: 1em 0;
  padding-left: 40px;
}

:deep(.markdown-content blockquote) {
  margin: 1em 0;
  padding: 0.5em 10px;
  border-left: 5px solid var(--blockquote-border);
  background-color: var(--blockquote-bg);
}

:deep(.markdown-content code) {
  background-color: var(--code-bg);
  color: var(--code-color);
  padding: 2px 4px;
  border-radius: 4px;
}

:deep(.markdown-content pre) {
  background-color: var(--pre-bg);
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}

:deep(.markdown-content table) {
  width: 100%;
  border-collapse: collapse;
  margin: 1em 0;
}

:deep(.markdown-content th),
:deep(.markdown-content td) {
  border: 1px solid var(--table-border);
  padding: 8px;
  text-align: left;
}

:deep(.markdown-content th) {
  background-color: var(--table-header-bg);
}

:deep(.markdown-content img) {
  max-width: 100%;
  height: auto;
}
</style>