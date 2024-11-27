<script setup lang="ts">

import {isLessVisible, loadLess} from "@/composables/useParties";
</script>

<template>
  <div v-if="isLessVisible" class="less" @click="loadLess">
    <h2 id="less-title">Laad minder</h2>
    <svg xmlns="http://www.w3.org/2000/svg" class="angle-up-icon" id="less-icon" viewBox="0 0 448 512">
      <path
          d="M246.6 137.4c-12.5-12.5-32.8-12.5-45.3 0l-160 160c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L224 205.3l137.4 137.4c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3l-160-160z"/>
    </svg>
  </div>
</template>

<style scoped>
.less {
  padding-top: 30px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  padding-bottom: 50px;
}

#less-title, #less-icon {
  cursor: pointer;
}
</style>