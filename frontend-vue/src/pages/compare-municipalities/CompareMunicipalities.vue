<template>
  <div class="municipality-comparison">
    <div class="title">
      <h2>Gemeentes vergelijk-tool</h2>
      <img src="@/assets/svg/info.svg" alt="info icon, hover to show additional information" title='Zoek en klik twee of meer gemeentes aan die je met elkaar wilt vergelijken. Druk daarna op de "vergelijk" knop om het resultaat te zien.'>
    </div>
    <div class="municipality-selection">
      <municipality-search></municipality-search>
      <div class="selected-municipalities">
        <div v-for="municipality in selectedMunicipalities" :key="municipality.municipalityId">
          <div>{{municipality.name}}</div>
          <img @click="removeMunicipality(municipality)" src="../../assets/svg/red-cross.svg" alt="red cross" />
        </div>
      </div>
      <button @click="renderMunicipalityCards">vergelijk</button>
    </div>
    <div class="municipality-cards">
      <MunicipalityCards :key="cardKey" :municipalities=toRaw(selectedMunicipalities)></MunicipalityCards>
    </div>
  </div>
</template>

<script setup lang="ts">

import MunicipalitySearch from "@/pages/compare-municipalities/components/MunicipalitySearch.vue";
import {getSelectedMunicipalities, removeMunicipality} from "@/composables/useMunicipalities";
import MunicipalityCards from "@/pages/compare-municipalities/components/MunicipalityCards.vue";
import {ref, toRaw} from "vue";

const selectedMunicipalities = getSelectedMunicipalities();
const cardKey = ref(0);

/**
 * Update the key of the MunicipalityCards component to force a re-render
 */
function renderMunicipalityCards() {
  cardKey.value++;
  document.querySelector(".search-result")?.classList.add("hidden");
}

</script>

<style scoped>
  .municipality-comparison {
    border: 2px solid var(--light-primary);
    border-radius: 4px;
    overflow: hidden;
  }

  .municipality-selection {
    padding: 32px 40px;
    border-bottom: 2px solid var(--light-primary);
  }

  h2 {
    margin-bottom: 8px;
  }
  ul {
    display: flex;
    flex-direction: column;
  }

  .selected-municipalities {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    margin: 10px 0;
  }

  .municipality-cards {
    padding: 32px 40px;
  }

  .selected-municipalities > div {
    color: #fff;
    border: 1px solid #fff;
    border-radius: 2px;
    padding: 4px 10px;
    font-size: 1.05rem;
    display: flex;
    gap: 5px;
    justify-content: center;
    align-items: center;
  }

  .selected-municipalities img {
    cursor: pointer;
  }

  .selected-municipalities img:active {
    transform: scale(0.95);
  }

  button {
    background-color: transparent;
    color: #fff;
    border: 1px solid var(--light-primary);
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    transition-duration: 100ms;
  }

  button:hover {
    color: var(--button-important);
  }

  button:active {
    transform: scale(0.97);
  }

  .title {
    background-color: var(--dark-secondary);
    padding: 13px 40px;
    border-bottom: 2px solid var(--light-primary);
    display: flex;
    justify-content: space-between;
  }

  .title img {
    width: 20px;
  }
</style>