<script setup lang="ts">

</script>

<template>
<div class="intro-item-container">
    <h1>Intro Heatmap</h1>
    <a class="intro-text">Een snelle overzichtelijke manier om stemmen te bekijken</a>
    <div>
      <img class="intro-img" alt="desc" src="../../../assets/img/intro/heatmap.PNG">
    </div>
  <a class="intro-text">Met deze heatmap kun je snel en gemakkelijk zien waar een partij veel stemmen heeft gekregen. Gebruik de selector linksboven om een partij te kiezen.
    De heatmap biedt ook de mogelijkheid om in en uit te zoomen. Ben je Nederland kwijtgeraakt? Klik op "Reset" en de kaart zal zichzelf weer centreren.</a>
</div>
</template>

<style scoped>
.intro-img {
  width: 100%;
  height: 100%;
  object-fit: contain; /* Ensures the image covers the entire box while maintaining aspect ratio */
  border-radius: 10px;
  border: 2px solid var(--dark-secondary);
  opacity: 0.7;
}
.intro-text {
  font-size: 14px;
}
</style>