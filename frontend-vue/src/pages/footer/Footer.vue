<script setup lang="ts">

</script>

<template>
  <footer>
    <div class="footer-container">
      <p>© 2024 - Alle rechten voorbehouden</p>
      <p><router-link class="footer-text" to="/algemene-voorwaarden">Algemene voorwaarden</router-link></p>
      <p><router-link class="footer-text" to="/privacyverklaring">Privacyverklaring</router-link></p>
      <p><router-link class="footer-text" to="/sitemap">Sitemap</router-link></p>
      <p>Gemaakt met liefde door Team 3</p>
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

.footer-text {
  position: relative;
  border-right: var(--light-primary) 1px solid;
  padding-right: 10px;
  padding-left: 10px;
}

.footer-text::after {
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

.footer-text:hover::after {
  width: 100%;
}

.footer-text:hover {
  cursor: pointer;
}

p:last-child {
  padding-left: 10px;
}
</style>
