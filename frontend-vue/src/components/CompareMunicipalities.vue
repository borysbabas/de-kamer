<template>
  <h2>Gemeentes vergelijk-tool</h2>
  <municipality-search></municipality-search>
  <div class="selected-municipalities">
    <div v-for="municipality in selectedMunicipalities" :key="municipality.municipalityId">
      <div>{{municipality.name}}</div>
      <img @click="removeMunicipality(municipality)" src="@/assets/svg/red-cross.svg" alt="red cross" />
    </div>
  </div>
  <button>vergelijk</button>
</template>

<script setup lang="ts">

import MunicipalitySearch from "@/components/MunicipalitySearch.vue";
import {getSelectedMunicipalities, removeMunicipality} from "@/composables/useMunicipalities";

const selectedMunicipalities = getSelectedMunicipalities();

function
</script>

<style scoped>
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
</style>