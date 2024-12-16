<script setup lang="ts">
import {inject, onMounted, ref, watch} from "vue";
import { marked } from "marked";
import hljs from "highlight.js";
import MarkdownMenu from "@/pages/forum/components/add-post/MarkdownMenu.vue";
const sessionService: any = inject("sessionService");

const postTitle = ref("");
const postDescription = ref("");
const renderedDescription = ref("");

const addPost = async () => {
  try {
    const postData = {
      title: postTitle.value,
      description: postDescription.value,
    };
    const response = await fetch(import.meta.env.VITE_API_URL + "/posts", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${sessionService.getTokenFromBrowserStorage()}`,
      },
      body: JSON.stringify(postData),
    });
    if (response.ok) {
      postTitle.value = "";
      postDescription.value = "";
      renderedDescription.value = "";
      alert("Post added successfully!");
      window.location.href = "/forum";
    } else if (response.status === 403) {
      alert("You need to be logged in");
    } else {
      console.error("Error adding post");
    }
  } catch (error) {
    console.error("Error adding post:", error);
  }
};

watch(postDescription, (newDescription) => {
  renderedDescription.value = marked(newDescription, {
    renderer: new marked.Renderer(),
    highlight: function (code, lang) {
      if (lang && hljs.getLanguage(lang)) {
        return hljs.highlight(code, { language: lang }).value;
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

const updateDescription = (newDescription) => {
  postDescription.value = newDescription;
};
onMounted(() => {
  const token = sessionService.getTokenFromBrowserStorage();
  if (!token) {
    alert("You need to be logged in");
    window.location.href = "/forum/login";
  }
});
</script>

<template>
  <section class="main">
    <h1>Add a new post</h1>
    <form @submit.prevent="addPost">
      <input v-model="postTitle" placeholder="Title" required />
      <textarea
        v-model="postDescription"
        placeholder="Description"
        required
      ></textarea>
      <div v-html="renderedDescription" class="markdown-content"></div>
      <button type="submit">Add Post</button>
    </form>
    <MarkdownMenu @updateDescription="updateDescription"></MarkdownMenu>
  </section>
</template>

<style scoped>
.main {
  min-height: calc(100vh - (128px + 56px));
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: var(--page-padding);
  padding-bottom: 0;
  position: relative;
  z-index: 0;
  border-bottom: none !important;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

input,
textarea {
  padding: 10px;
  border: 1px solid var(--light-primary);
  border-radius: 5px;
  background-color: var(--light-primary);
  color: var(--dark-primary);
}

textarea {
  min-height: 100px;
  resize: vertical;
}

button {
  padding: 10px;
  background-color: var(--button-important);
  color: var(--light-primary);
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-bottom: 50px;
}

button:hover {
  background-color: var(--button-important-hover);
}

/* Add styles for markdown content */
::v-deep .markdown-content {
  background: var(--light-primary);
  border-radius: 10px;
  color: var(--dark-primary);
  padding: 20px;
}

::v-deep .markdown-content h1 {
  font-size: 2em;
  margin: 1rem 0;
  padding: 0;
}

::v-deep .markdown-content h2 {
  font-size: 1.5em;
  margin: 0.75em 0;
}

::v-deep .markdown-content h3 {
  font-size: 1.17em;
  margin: 0.83em 0;
}

::v-deep .markdown-content h4 {
  font-size: 1em;
  margin: 1.12em 0;
}

::v-deep .markdown-content h5 {
  font-size: 0.83em;
  margin: 1.5em 0;
}

::v-deep .markdown-content h6 {
  font-size: 0.75em;
  margin: 1.67em 0;
}

::v-deep .markdown-content em {
  margin: 1em 0;
  font-style: italic;
}

::v-deep .markdown-content p {
  margin: 1em 0;
}

::v-deep .markdown-content a {
  color: var(--link-color);
  text-decoration: none;
}

::v-deep .markdown-content a:hover {
  text-decoration: underline;
}

::v-deep .markdown-content blockquote {
  margin: 1em 0;
  padding: 0.5em 10px;
  border-left: 5px solid var(--blockquote-border);
  background-color: var(--blockquote-bg);
  color: var(--light-primary);
}

::v-deep .markdown-content ul {
  list-style-type: disc;
  padding-left: 40px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
}

::v-deep .markdown-content ul li {
  margin: 0;
  padding: 0;
}

::v-deep .markdown-content ol {
  list-style-type: decimal;
  margin: 1em 0;
  padding-left: 40px;
}

::v-deep .markdown-content blockquote {
  margin: 1em 0;
  padding: 0.5em 10px;
  border-left: 5px solid var(--blockquote-border);
  background-color: var(--blockquote-bg);
}

::v-deep .markdown-content code {
  background-color: var(--code-bg);
  color: var(--code-color);
  padding: 2px 4px;
  border-radius: 4px;
}

::v-deep .markdown-content pre {
  background-color: var(--pre-bg);
  padding: 10px;
  border-radius: 4px;
  overflow-x: auto;
}

::v-deep .markdown-content table {
  width: 100%;
  border-collapse: collapse;
  margin: 1em 0;
}

::v-deep .markdown-content th,
::v-deep .markdown-content td {
  border: 1px solid var(--table-border);
  padding: 8px;
  text-align: left;
}

::v-deep .markdown-content th {
  background-color: var(--table-header-bg);
}

::v-deep .markdown-content img {
  max-width: 100%;
  height: auto;
}
</style>
