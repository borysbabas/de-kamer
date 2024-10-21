<script setup lang="ts">

</script>

<template>
<section class="first main">
  <h1>Results</h1>
  <p>Results page content</p>
</section>

</template>

<style scoped>

.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  border-bottom: var(--light-primary) 1px solid;
  z-index: 0;
}

.first {
  padding-top: 150px;
  margin: 0 auto;
}
</style>