<script setup lang="ts">

</script>

<template>
  <div class="graph">
    <h1 id="graph-title">
      Stemmenverdeling Tweede Kamer
    </h1>
    <h2 id="graph-subtitle">
      Laatste update: 12-09-2021
    </h2>
    <div class="graph-img-container">
      <img class="graph-img" alt="graph" src="../../../assets/img/zetelverdeling-tk.png">
    </div>
  </div>
</template>

<style scoped>
.graph {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 20px;
  padding-top: 50px;
}

#graph-title {
  padding-bottom: 20px;
}

#graph-subtitle {
  padding-bottom: 50px;
}

.graph-img-container {
  display: flex;
  justify-content: center;
  width: 100%;
}

.graph-img {
  max-height: 500px;
  max-width: 1000px;
  width: 100%;
  border-radius: 20px;
}
</style>