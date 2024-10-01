<script setup lang="ts">

</script>

<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Beiruti:wght@200..900&display=swap" rel="stylesheet">
    <section class="main">
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
</template>

<style scoped>
.main {
  background: #212D39;
  color: #ECECEC;
}
.parties {
  display: grid;
  gap: 50px 50px;
  grid-template-areas:
    "party party party party party party"
    "party party party party party party";
  padding: 0 128px;
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
  padding: 0 128px 50px 128px;
}

@media (min-width: 768px) {
  h1 {
    font-size: 5rem;
  }
}
</style>