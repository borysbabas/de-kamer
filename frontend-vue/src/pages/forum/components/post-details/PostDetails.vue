<script setup lang="ts">
import {useRoute} from "vue-router";
import {usePostDetails} from "@/composables/usePostDetails";

const route = useRoute();

const {isLoading, loadedPost} = usePostDetails(route.params.postId);

</script>

<template>
  <div v-if="isLoading" class="center">
    <div class="loader"></div>
  </div>
  <div v-else-if="loadedPost">
    <h1>{{loadedPost.title}}</h1>
  </div>
  <div v-else>
    <h1>Post kon niet worden gevonden</h1>
    <router-link to="/forum"><button>te</button></router-link>
  </div>
</template>

<style scoped>

</style>