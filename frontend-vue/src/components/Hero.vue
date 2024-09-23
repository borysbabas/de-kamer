<script setup lang="ts">

</script>

<template>
  <div class="wrapper">
<h1>Dit is de hero van de webpagina</h1>
    <div class="btn-container">
    <button class="learn-more">Learn More</button>
    </div>
  </div>
</template>

<style scoped>
.learn-more {
  position: absolute; /* Absoluut gepositioneerd binnen de hero */
  right: 20px; /* Afstand van de rechterkant */
  bottom: 20px; /* Afstand van de onderkant */
  background-color: #90ee90; /* Achtergrondkleur van de knop */
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 1rem;
  font-weight: bold;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.learn-more:hover {
  background-color: #008000; /* Kleurverandering bij hover */
}
.wrapper {
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
  background-color: #f5f5f5;
  margin-top: 60px;
  text-align: center;
  padding: 20px;
}

h1 {
  font-size: 3rem;
  color: #333;
  font-weight: bold;
  line-height: 1.2;
  margin: 0;
}

@media (min-width: 768px) {
  h1 {
    font-size: 5rem;
  }
}
</style>