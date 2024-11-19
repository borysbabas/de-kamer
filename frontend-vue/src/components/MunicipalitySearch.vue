<template>
  <div class="search-wrapper">
    <div class="searchbar">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
        <path fill="#000" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
      </svg>
      <input type="text" placeholder="Amsterdam" @input="onSearchInput">
    </div>
    <div class="search-result" v-if="filteredList.length >= 1">
      <p v-for="municipality in filteredList" :key="municipality.municipalityId">{{municipality.name}}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useMunicipalities} from "@/composables/useMunicipalities";
import {ref, toRaw} from "vue";
import type {Municipality} from "@/components/types/Municipality";

const {municipalityList} = useMunicipalities();
const filteredList = ref<Municipality[]>([]);

let timer;
function onSearchInput(e: Event) {
  const value = (e.target as HTMLInputElement).value;

  if (timer) {
    clearTimeout(timer);
  }

  if (value.trim() === "") {
    filteredList.value = [];
    return;
  }

  timer = setTimeout(() => {
    filteredList.value = [];
    filteredList.value.push(...filterMunicipalities(value));
  }, 300);
}

function filterMunicipalities(filter: string) {
  const rawList = toRaw(municipalityList.value);

  function checkValue(value: Municipality) {
    return value.name.toLowerCase().includes(filter.toLowerCase().trim());
  }

  return rawList.filter(checkValue).splice(0, 10);
}

</script>

<style scoped>
  .searchbar {
    display: flex;
    gap: 10px;
    background-color: #fff;
    padding: 8px 10px;
    border-radius: 8px 8px 0 0;
  }

  .searchbar svg {
    width: 25px;
  }

  .searchbar input {
    border: none;
    outline: none;
    background-color: transparent;
    font-size: 1rem;

  }

  .search-wrapper {
    position: relative;
  }

  .search-result {
    position: absolute;
    left: 0;
    right: 0;
    background-color: #fff;
    border-radius: 0 0 8px 8px;
    padding: 10px 0;
  }

  .search-result p {
    margin: 0;
    border-bottom: 1px solid #ccc;
    cursor: pointer;
    color: #000;
    font-size: 1.1rem;
    padding: 8px 10px;
  }

  .search-result p:hover {
    background-color: #f0f0f0;
  }

</style>