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
    candidateId: number;
    firstName: string;
    prefix: string;
    lastName: string;
    affiliationName: string;
    votes: number;
  }[];
}

const partyDetails = ref<PartyDetails | null>(null);
const sortOrder = ref('desc');

const sortCandidates = () => {
  if (partyDetails.value && partyDetails.value.candidates) {
    partyDetails.value.candidates.sort((a, b) => {
      return sortOrder.value === 'desc' ? b.votes - a.votes : a.votes - b.votes;
    });
  }
};

onMounted(async () => {
  const details = await fetchPartyDetails(partyId);
  if (details) {
    partyDetails.value = details;
    sortCandidates();
  }
});
</script>

<template>
  <Navbar></Navbar>
  <section>
    <div v-if="partyDetails">
      <h1>{{ partyDetails.affiliationName }}</h1>
      <p>Votes: {{ partyDetails.affiliationVotes }}</p>
      <div class="dropdown">
        <h2>Candidates</h2>
        <select v-model="sortOrder" @change="sortCandidates" class="dropdown-select">
          <option value="desc">Highest to Lowest</option>
          <option value="asc">Lowest to Highest</option>
        </select>
      </div>
      <div class="candidates">
        <div v-for="candidate in partyDetails.candidates" :key="candidate.candidateId" class="candidate">
          <div>{{ candidate.firstName }}
            {{ candidate.prefix }} {{ candidate.lastName }}
          </div>
          Votes: {{ candidate.votes }}
        </div>
      </div>
    </div>
    <div v-else>
      <p>Loading...</p>
    </div>
  </section>
  <Footer></Footer>
</template>

<style scoped>
.main {
  border: none;
}

h1 {
  margin: 0;
  padding-bottom: 20px;
}

p {
  margin-bottom: 20px;
}

h2 {
  margin-bottom: 20px;
}

.dropdown {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 30px;
}

.dropdown-select {
  font-size: 26px;
  padding: 5px;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: var(--light-primary) 1px solid;
  border-radius: 5px;
  cursor: pointer;
  font-family: inherit;
}

.dropdown-select:focus {
  outline: none;
}

.candidates {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.candidate {
  border: 1px solid var(--dark-primary);
  padding: 10px;
  border-radius: 10px;
  background-color: var(--button);
  display: flex;
  justify-content: space-between;
  gap: 10px;
  transition-duration: var(--animation-duration);
}

.candidate:hover {
  background-color: var(--button-hover);
}

.candidate:last-child {
  margin-bottom: 20px;
}
</style>