<template>
<div class="button-container">
<p class="title-text"> Ontdek de verkiezingsresultaten van 2021</p>
  <router-link to="/ontdekken"><button>Ontdek</button></router-link>
</div>
</template>

<script setup lang="ts">

</script>


<style scoped>
.title-text {
  font-family: var(--font), sans-serif;
  font-size: 24px;
}
.button-container {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 20px;
}

.button-container p {
  margin-bottom: 32px;
}
</style>