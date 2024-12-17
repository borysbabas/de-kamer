<script setup lang="ts">
import {selectedOption} from "@/composables/useDropdown";
import {getShortenedName, sortedItems, visibleBlocks} from "@/composables/useParties";
import {useRouter} from 'vue-router';

const router = useRouter();

function goToPartyDetails(partyId: number) {
  router.push({name: 'PartyDetails', params: {partyId}});
}

function findLogoPath(affiliationName: string) {
  if (affiliationName) {
    return `/affiliation-logos/${affiliationName.toLowerCase()}.png`;
  } else {
    return `/affiliation-logos/lijst30.png`;
  }
}
</script>

<template>
  <div class="parties">
    <div v-for="(party) in sortedItems.slice(0, visibleBlocks)" :key="party.affiliationId" class="party">
      <div class="party-img">
        <img :alt="party.affiliationName"
             @click="goToPartyDetails(party.affiliationId)"
             :src="findLogoPath(party.affiliationName)"
             width="200" height="200">
      </div>
      <div>
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
  </div>
</template>

<style scoped>
.parties {
  display: flex;
  gap: 40px;
  flex-wrap: wrap;
  justify-content: space-between;
}

.party {
  max-width: 200px;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  align-items: center;
  border-radius: 12px;
  border: 2px solid var(--light-primary);
  overflow: hidden;
  padding-bottom: 10px;
}

.party-img {
  width: 100%;
  aspect-ratio: 1/1;
  overflow: hidden;
  cursor: pointer;
  background-color: var(--light-primary);
  padding: 10px;
  box-sizing: border-box;
  transition-duration: var(--animation-duration);
}

.party-img img {
  object-fit: contain;
  width: 100%;
  height: 100%;
}

.party-img:hover {
  transform: scale(1.05);
  filter: brightness(50%);
}

.party-votes, .party-name {
  text-align: center;
}

@media only screen and (max-width: 480px) {
  .party {
    max-width: 100%;
  }
}
</style>