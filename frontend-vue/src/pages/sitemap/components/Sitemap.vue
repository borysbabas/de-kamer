<script setup lang="ts">

</script>

<template>
  <section>
    <h1>
      De Kamer Sitemap
    </h1>
    <div class="sitemap">
      <div class="sitemap-section">
        <h2>De Kamer</h2>
        <a href="/">Landingspagina</a>
        <a href="/uitslagen">Uitslagen</a>
        <a href="/info">Info</a>
        <a href="/ontdekken">Ontdekken</a>
      </div>

      <div class="sitemap-section">
        <h2>Forum</h2>
        <a href="/forum">Forum home</a>
        <a href="/forum/log-in">Inloggen</a>
        <a href="/forum/registreer">Registreren</a>
      </div>

      <div class="sitemap-section">
        <h2>Juridische informatie</h2>
        <a href="/algemene-voorwaarden">Algemene voorwaarden</a>
        <a href="/privacyverklaring">Privacyverklaring</a>
      </div>
    </div>
  </section>
</template>

<style scoped>
.sitemap {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0 20% 0 0;
}

.sitemap a {
  font-size: x-large;
  margin-bottom: 20px;
  width: fit-content;
}

.sitemap a::after {
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

.sitemap a:hover::after {
  width: 100%;
}

.sitemap h2 {
  margin-bottom: 15px;
  font-size: xx-large;
}

.sitemap-section {
  display: flex;
  flex-direction: column;
}
</style>