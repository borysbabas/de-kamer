<script setup lang="ts">
import { useRouter } from "vue-router";
const router = useRouter();


const goToChart = () => {
  router.push({ path: '/uitslagen', hash: '#chart-box' });
};

</script>


<template>
  <div class="intro-item-container">
    <h1>Intro Grafiek</h1>
    <p class="intro-text">Met de Grafiek tool kan je makelijk verschilende waardes te vergelijken.</p>
    <div>
      <img class="intro-img" alt="desc" src="../../../assets/img/intro/selector.PNG">
    </div>
    <p class="intro-text">
      Met de bovenstaande interface kunt u aangeven wat er in de grafiek wordt weergegeven.
      Naarmate u opties selecteert,
      worden er meer keuzemogelijkheden beschikbaar.
    </p>
    <div>
      <img class="intro-img" alt="desc" src="../../../assets/img/intro/chart.PNG">
    </div>
    <p class="intro-text">
      Nadat u de gewenste gegevens hebt geselecteerd, wordt de grafiek automatisch bijgewerkt.
      Wanneer u met de muis over een waarde beweegt, verschijnt de exacte waarde.
      Wilt u de grafiek downloaden of exporteren? Klik dan rechtsboven in de grafiek.
      Exporteren is mogelijk in de formaten SVG, PNG en CSV.
    </p>
    <button @click="goToChart">Naar Grafiek</button>
  </div>
</template>

<style scoped>
.intro-img {
  width: 100%;
  height: 100%;
  object-fit: contain;
  border-radius: 10px;
  opacity: 0.7;
  margin-top: 10px;
  margin-bottom: 10px;
  cursor: not-allowed;
}
</style>