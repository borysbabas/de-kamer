<script setup lang="ts">
import {watch} from 'vue';
import Dropdown from "@/pages/home/components/Dropdown.vue";
import {selectedOption} from "@/composables/useDropdown";
import Parties from "@/pages/home/components/Parties.vue";
import LoadMore from "@/pages/home/components/LoadMore.vue";
import LoadLess from "@/pages/home/components/LoadLess.vue";
import {fetchPartyImages} from "@/composables/useFetch";

watch(selectedOption, () => {
  fetchPartyImages();
});
</script>

<template>
  <section class="first main">
    <Dropdown></Dropdown>
    <Parties></Parties>
    <LoadMore></LoadMore>
    <LoadLess></LoadLess>
  </section>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 150px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  border-bottom: var(--light-primary) 1px solid;
  z-index: 0;
}

.first {
  padding-top: 150px !important;
  margin: 0 auto;
}

.dropdown-select option {
  padding: 20px;
  border-radius: 0;
}

.dropdown-select option:last-child {
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}

.dropdown-select option:first-child {
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>