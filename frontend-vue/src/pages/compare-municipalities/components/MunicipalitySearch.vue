<template>
  <div class="search-wrapper" @focusout="focusOutside" tabindex="0" @keyup="enterPressedOnFocus">
    <div class="searchbar" @focusin="focusIn">
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" @click="searchbarClick">
        <path fill="#000" d="M416 208c0 45.9-14.9 88.3-40 122.7L502.6 457.4c12.5 12.5 12.5 32.8 0 45.3s-32.8 12.5-45.3 0L330.7 376c-34.4 25.2-76.8 40-122.7 40C93.1 416 0 322.9 0 208S93.1 0 208 0S416 93.1 416 208zM208 352a144 144 0 1 0 0-288 144 144 0 1 0 0 288z"/>
      </svg>
      <input type="text" placeholder="Amsterdam" @input="onSearchInput">
    </div>
    <div class="search-result" v-if="filteredList.length >= 1">
      <p v-for="municipality in municipalitiesInFilterList.splice(0, 10)" :key="municipality.municipalityId" @click="addMunicipality(municipality)">{{municipality.name}}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {addMunicipality, getSelectedMunicipalities, useMunicipalities} from "@/composables/useMunicipalities";
import {computed, ref, toRaw} from "vue";
import type {Municipality} from "@/types/Municipality";

const {municipalityList} = useMunicipalities();
const filteredList = ref<Municipality[]>([]);

// Filter out the selected municipalities from the search results
const selectedList: Municipality[] = getSelectedMunicipalities()
const municipalitiesInFilterList = computed(() => {
  const selectedIds = selectedList.value.map((municipality: Municipality) => municipality.municipalityId);
  return filteredList.value.filter((municipality: Municipality) => !selectedIds.includes(municipality.municipalityId));
});

/**
 * Event handler to put value of input field in filter function
 */
function onSearchInput(e: Event) {
  const value = (e.target as HTMLInputElement).value;

  filterInput(value);
}

/**
 * Resets the filtered list and hides result when value is empty
 * @param value The input value to filter the municipalities on
 */
function filterInput(value: string) {
  if (value.trim() === "") {
    filteredList.value = [];
    return;
  }
  filteredList.value = [];
  filteredList.value.push(...filterMunicipalities(value));
}

/**
 * Filters the municipalities based on the input value. The filtered list is based on the name of the municipality and
 * will return all municipalities that contain the input value in alphabetical order.
 * @param filter The input value to filter the municipalities on
 */
function filterMunicipalities(filter: string) {
  const rawList = toRaw(municipalityList.value);

  function checkValue(value: Municipality) {
    return value.name.toLowerCase().includes(filter.toLowerCase().trim());
  }

  return rawList.filter(checkValue);
}

/**
 * Closes the search results when the user clicks outside the search bar
 * e.relatedTarget is not null when clicking on anything inside the search wrapper
 * @param e FocusEvent on the search wrapper
 */
function focusOutside(e: FocusEvent) {
  if (e.relatedTarget === null) {
    filteredList.value = [];
  }
}

/**
 * Re-filters the search results based on the previously inputted value
 * @param e FocusEvent on the input field
 */
function focusIn(e: FocusEvent) {
  const input = e.target as HTMLInputElement;
  filterInput(input.value);
}

/**
 * Focuses the user into the input field when the search icon is clicked
 */
function searchbarClick() {
  document.querySelector("input")?.focus();
}

/**
 * Focuses user into the input field when the wrapper is focused and the enter key is pressed
 * @param e KeyboardEvent to check if the enter key is pressed
 */
function enterPressedOnFocus(e: KeyboardEvent) {
  if (e.key === "Enter") {
    const input = document.querySelector("input") as HTMLInputElement;
    input.focus();
    filterInput(input.value);
  }
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
    cursor: pointer;
  }

  .searchbar input {
    border: none;
    outline: none;
    background-color: transparent;
    font-size: 1rem;
    flex-grow: 1;
  }

  .search-wrapper {
    position: relative;
  }

  .search-result {
    position: relative;
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