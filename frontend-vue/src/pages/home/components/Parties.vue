<script setup lang="ts">
import {selectedOption} from "@/composables/useDropdown";
import {getShortenedName, sortedItems, visibleBlocks} from "@/composables/useParties";
import {mediaData} from "@/composables/useFetch";
import {useRouter} from 'vue-router';

const router = useRouter();

function goToPartyDetails(partyId: number) {
  router.push({name: 'PartyDetails', params: {partyId}});
}
</script>

<template>
  <div class="parties">
    <div v-for="(party, index) in sortedItems.slice(0, visibleBlocks)" :key="party.affiliationId" class="party">
      <img class="party-img" :alt="mediaData[index]?.title || party.affiliationName"
           @click="goToPartyDetails(party.affiliationId)"
           :src="mediaData[index]?.images[0]?.url || party.imageUrl"
           width="200" height="200">
      <p class="party-votes" v-if="selectedOption === 'most_parties' || selectedOption === 'least_parties'">
        {{ party.affiliationVotes }} stemmen
      </p>
      <p class="party-votes"
         v-else-if="selectedOption === 'most_candidates' || selectedOption === 'least_candidates'">
        {{ party.votes }} stemmen
      </p>
      <p class="party-name" v-if="selectedOption === 'most_parties' || selectedOption === 'least_parties'">
        {{ getShortenedName(party.affiliationName) }}
      </p>
      <p class="party-name" v-else-if="selectedOption === 'most_candidates' || selectedOption === 'least_candidates'">
        {{ party.name }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.parties {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
}

.party-img {
  width: 200px;
  height: 200px;
  border-radius: 20px;
  object-fit: contain;
  cursor: pointer;
  margin-bottom: 10px;
  transition-duration: var(--animation-duration);
}

.party-img:hover {
  transform: scale(1.05);
  filter: brightness(50%);
}

.party-votes, .party-name {
  text-align: center;
}
</style>