<script setup lang="ts">
import {useRouter} from 'vue-router';

const router = useRouter();

const navigateToAddPost = () => {
  router.push({name: 'AddPost'});
};
</script>

<template>
  <button class="add-post-button" @click="navigateToAddPost">Nieuwe post</button>
</template>

<style scoped>
button {
  background: var(--button-important);
  color: var(--light-primary);
  border: none;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
  position: fixed;
  bottom: 10px;
  right: 10px;
}
</style>