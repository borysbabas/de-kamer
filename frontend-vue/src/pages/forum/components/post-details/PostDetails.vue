<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {usePostDeletion, usePostDetails} from "@/composables/usePostDetails";
import {computed} from "vue";
import {marked} from "marked";
import {useJWToken} from "@/composables/useJWToken";

const route = useRoute();
const router = useRouter();

const {isLoading, loadedPost} = usePostDetails(route.params.postId);
const payload = useJWToken();

/**
 * Renders the description of the post as markdown
 */
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

/**
 * Deletes the post
 */
async function deletePost() {
  if (confirm("Weet je zeker dat je deze post wilt verwijderen?")) {
    if (!loadedPost.value) {
      return;
    }
    const {deletePost} = usePostDeletion(loadedPost.value.id);
    await deletePost();
    await router.push("/forum");
  }
}

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
        <span>Geplaatst op: {{new Date(loadedPost.dateCreated).toLocaleString()}}</span>
        <span>Geplaatst door: {{ loadedPost.account.userName }}</span>
      </div>
    </div>
    <div class="postContent">
      <div class="postBody">
        <div v-html="renderedDescription" class="post-description markdown-content"></div>
      </div>
    </div>
    <div v-if="payload && loadedPost.account.id === parseInt(payload.jti)" class="delete-btn">
      <button @click="deletePost">Verwijder</button>
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