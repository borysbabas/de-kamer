<script setup lang="ts">

</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Beiruti:wght@200..900&display=swap" rel="stylesheet">
  <section class="first main">
    <h1>
      Meest verkozen partijen
    </h1>
    <div class="parties">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
      <img class="party" alt="party" src="../assets/img/Dummy%20200x200%20temp.jpg">
    </div>
  </section>
  <section class="main">

  </section>
</template>

<style scoped>
.main {
  height: calc(100vh - 200px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-secondary);
  padding-left: 128px;
  padding-right: 128px;
}

.first {
  padding-top: 200px;
  border-bottom: 1px solid var(--light-secondary);
}

.parties {
  display: grid;
  gap: 50px 50px;
  grid-template-areas:
    "party party party party party party"
    "party party party party party party";
  max-width: fit-content;
}

.party {
  width: 200px;
  height: 200px;
  border-radius: 20px;
  object-fit: cover;
}

h1 {
  font-size: 3rem;
  line-height: 1.2;
  font-weight: normal;
  margin: 0;
  font-family: Beiruti, sans-serif;
  padding-bottom: 50px;
}

@media (min-width: 768px) {
  h1 {
    font-size: 5rem;
  }
}
</style>