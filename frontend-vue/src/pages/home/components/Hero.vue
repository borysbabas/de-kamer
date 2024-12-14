<script setup lang="ts">
import Dropdown from "@/pages/home/components/Dropdown.vue";
import Parties from "@/pages/home/components/Parties.vue";
import LoadMore from "@/pages/home/components/LoadMore.vue";
import LoadLess from "@/pages/home/components/LoadLess.vue";

</script>

<template>
  <section>
    <Dropdown></Dropdown>
    <Parties></Parties>
    <LoadMore></LoadMore>
    <LoadLess></LoadLess>
  </section>
</template>

<style scoped>

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