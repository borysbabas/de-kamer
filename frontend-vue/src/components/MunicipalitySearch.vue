<template>
  <div class="searchbar">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
      <path fill="#000" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
    </svg>
    <input type="text" placeholder="Amsterdam" @input="onSearchInput">
  </div>
  <div id="search-result">
    <p v-for="(_, index) in 10" :key="index">{{municipalityList[index]}}</p>
  </div>
</template>

<script setup lang="ts">
import {useMunicipalities} from "@/composables/useMunicipalities";

const {municipalityList} = useMunicipalities();

let timer;
function onSearchInput(e: Event) {
  if (timer) {
    clearTimeout(timer);
  }
  timer = setTimeout(() => {
    console.log((e.target as HTMLInputElement).value);
  }, 500);
}



</script>

<style scoped>
  .searchbar {
    display: flex;
    gap: 10px;
    background-color: #fff;
    padding: 8px 10px;
    border-radius: 8px;
  }

  .searchbar svg {
    width: 25px;
  }

  .searchbar input {
    border: none;
    outline: none;
    background-color: transparent;

  }

</style>