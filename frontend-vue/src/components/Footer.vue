<script setup lang="ts">

</script>

<template>
  <footer>
    <div class="footer-container">
      <p>© 2024 - All rights reserved</p>
      <p><a>Algemene voorwaarden</a></p>
      <p><a>Privacyverklaring</a></p>
      <p><a>Sitemap</a></p>
      <p>Made with love by Team 3</p>
    </div>
  </footer>
</template>


<style scoped>

/** Styling for the footer section */
footer {
  background-color: var(--button);
  padding: 20px;
  text-align: center;
  bottom: 0;
  object-fit: cover;
}

.footer-container {
  display: flex;
  justify-content: center;
}

p:first-child {
  padding-right: 10px;
  border-right: var(--light-primary) 1px solid;
}

a {
  position: relative;
  border-right: var(--light-primary) 1px solid;
  padding-right: 10px;
  padding-left: 10px;
}

a::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 1px;
  background-color: var(--light-primary);
  width: 0;
  transition: width var(--animation-duration);
}

a:hover::after {
  width: 100%;
}

a:hover {
  color: var(--light-primary);
}

p:last-child {
  padding-left: 10px;
}
</style>
