<script setup lang="ts">

import {isMoreVisible, loadMore} from "@/composables/useParties";
</script>

<template>
  <div v-if="isMoreVisible" class="more" @click="loadMore">
    <h2 id="more-title">Laad meer</h2>
    <svg xmlns="http://www.w3.org/2000/svg" class="angle-down-icon" id="more-icon" viewBox="0 0 448 512">
      <path
          d="M201.4 374.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 306.7 86.6 169.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"/>
    </svg>
  </div>
</template>

<style scoped>
.more {
  padding-top: 30px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}

#more-title, #more-icon {
  cursor: pointer;
}
</style>