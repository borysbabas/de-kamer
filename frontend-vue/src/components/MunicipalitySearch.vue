<template>
  <div class="searchbar">
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
      <path fill="#000" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
    </svg>
    <input type="text" placeholder="Amsterdam" @input="onSearchInput">
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from "vue";
  import type {Municipality} from "@/components/types/Municipality";

  const municipalityList = ref([])
  const BACKEND = "http://localhost:3001";

  function onSearchInput(e: Event) {
    const inputValue: string = (e.currentTarget as HTMLInputElement).value;
    console.log(inputValue);
  }

  async function fetchMunicipalities(): Promise<void> {
    try {
      const result = await fetch(BACKEND + "/municipality/all", {
        method: "GET"
      });

      if (!result.ok) {
        return;
      }

      const municipalities: Municipality[] = await result.json();
      if (municipalities.length > 0) {
        municipalities.forEach((municipality: Municipality) => {
          municipalityList.value.push(municipality);
        });
      }
    } catch (e) {
      console.log(e);
    }
  }

  onMounted(() => {
    void fetchMunicipalities();
  })
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