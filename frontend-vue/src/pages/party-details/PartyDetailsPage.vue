<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import {fetchPartyDetails} from '@/composables/useFetch';
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue";

const route = useRoute();
const partyId = Array.isArray(route.params.partyId) ? route.params.partyId[0] : route.params.partyId;

interface PartyDetails {
  affiliationName: string;
  affiliationVotes: number;
  candidates: {
    name: string;
    votes: number;
  }[];
}

const partyDetails = ref<PartyDetails | null>(null);

onMounted(async () => {
  partyDetails.value = await fetchPartyDetails(partyId);
  console.log(partyDetails.value);
});
</script>

<template>
  <Navbar></Navbar>
  <section class="first main">
    <div v-if="partyDetails">
      <h1>{{ partyDetails.affiliationName }}</h1>
      <p>Votes: {{ partyDetails.affiliationVotes }}</p>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </section>
  <Footer></Footer>
</template>

<style scoped>

</style>