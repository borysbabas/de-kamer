<script setup lang="ts">
import {inject, onMounted, ref, watch} from "vue";
import {marked} from "marked";
import hljs from "highlight.js";
import MarkdownMenu from "@/pages/forum/components/add-post/MarkdownMenu.vue";
import {useRouter} from "vue-router";

const sessionService: any = inject("sessionService");

const postTitle = ref("");
const postDescription = ref("");
const renderedDescription = ref("");

const router = useRouter();

const addPost = async () => {
  try {
    const postData = {
      title: postTitle.value,
      description: postDescription.value,
    };
    const response = await fetch(import.meta.env.VITE_API_URL + "/account/posts/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postData),
    });
    if (response.ok) {
      postTitle.value = "";
      postDescription.value = "";
      renderedDescription.value = "";
      alert("Post added successfully!");
      router.push("/forum");
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

const updateDescription = (newDescription) => {
  postDescription.value = newDescription;
};
onMounted(() => {
  const token = sessionService.getTokenFromBrowserStorage();
  if (!token) {
    alert("You need to be logged in");
    router.push("/forum/log-in");
  }
});
</script>

<template>
  <section>
    <h1>Nieuwe post</h1>
    <form @submit.prevent="addPost">
      <input v-model="postTitle" placeholder="Titel" required/>
      <textarea
          v-model="postDescription"
          placeholder="Beschrijving"
          required
      ></textarea>
      <div v-html="renderedDescription" class="markdown-content"></div>
      <button type="submit">Versturen</button>
    </form>
    <MarkdownMenu @updateDescription="updateDescription"></MarkdownMenu>
  </section>
</template>

<style scoped>
@import "@/assets/markdown-content.css";

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
</style>
