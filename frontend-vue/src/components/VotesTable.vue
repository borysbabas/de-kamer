<template>
  <select @change="handleConstituencyChange">
    <option selected disabled value="">-- Kies een kieskring --</option>
    <option v-for="constituency in constituencies" :key="constituency.constituencyId" :value="constituency.constituencyId">
      {{ constituency.constituencyName }}
    </option>
  </select>
  <table v-if="selectedConstituency">
    <tr>
      <th>Kandidaat</th>
      <th>Partij</th>
      <th>Aantal stemmen</th>
    </tr>
    <tr v-for="constituencyVote in constituencyVotes" :key="constituencyVote.candidateId">
      <td>
        {{ constituencyVote.candidateName }}
      </td>
      <td>
        {{ constituencyVote.affiliationName }}
      </td>
      <td>
        {{ constituencyVote.constituencyVotes }}
      </td>
    </tr>
  </table>
  <div class="pageSelector" v-if="selectedConstituency">
    <p @click="handleFirstPage" :class="['pageButton', {'greyed-out': currentPage === 1 }]">
      <<
    </p>

    <p @click="handlePageDown" :class="['pageButton', {'greyed-out': currentPage === 1 }]">
      <
    </p>

    <p>
      {{ currentPage }} / {{ Math.ceil(tableLength / PAGINATION) }}
    </p>

    <p @click="handlePageUp" :class="['pageButton', {'greyed-out': currentPage === Math.ceil(tableLength / PAGINATION) }]">
      >
    </p>

    <p @click="handleLastPage" :class="['pageButton', {'greyed-out': currentPage === Math.ceil(tableLength / PAGINATION) }]">
      >>
    </p>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";

// Types
type Constituency = {
  constituencyId: number;
  constituencyName: string;
}

type ConstituencyVotes = {
  constituencyName: string;
  affiliationName: string;
  candidateName: string;
  constituencyVotes: number;
  constituencyId: number;
  affiliationId: number;
  candidateId: number;
}

// Variables
const constituencies = ref<Constituency[]>([]);

const constituencyVotes = ref<ConstituencyVotes[]>([]);

const selectedConstituency = ref<number>();

const n = ref<number>(0);

const currentPage = ref<number>(1);

const PAGINATION = ref<number>(30);

const tableLength = ref<number>();

// Fetch functions
async function fetchConstituencies() {
  try {
    const result = await fetch("http://localhost:3001/constituencies");

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituencies");
    }

    constituencies.value = await result.json();
  } catch (error) {
    console.log(error);
  }
}

async function fetchConstituencyVotesCount(constituencyId) {
  try {
    const result = await fetch(`http://localhost:3001/votes/constituencies/${constituencyId}/count`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituency votes count");
    }

    tableLength.value = await result.json();
  } catch (error) {
    console.log(error)
  }
}

async function fetchConstituencyVotes(constituencyId, page) {
  try {
    const result = await fetch(`http://localhost:3001/votes/constituencies/${constituencyId}/${page}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituency votes");
    }

    constituencyVotes.value = await result.json();
    selectedConstituency.value = constituencyId;
  } catch (error) {
    console.log(error);
  }
}

// Handle functions
function handleConstituencyChange(event) {
  currentPage.value = 1;
  fetchConstituencyVotesCount(event.target.value);
  fetchConstituencyVotes(event.target.value, currentPage.value);
}

function handleFirstPage() {
  if (currentPage.value !== 1) {
    currentPage.value = 1;
    fetchConstituencyVotes(selectedConstituency.value, currentPage.value);
  }
}

function handlePageDown() {
  if (currentPage.value !== 1) {
    currentPage.value -= 1;
    fetchConstituencyVotes(selectedConstituency.value, currentPage.value);
  }
}

function handlePageUp() {
  if (currentPage.value < Math.ceil(tableLength.value / PAGINATION.value)) {
    currentPage.value += 1;
    fetchConstituencyVotes(selectedConstituency.value, currentPage.value);
  }
}

function handleLastPage() {
  if (currentPage.value < Math.ceil(tableLength.value / PAGINATION.value)) {
    currentPage.value = Math.ceil(tableLength.value / PAGINATION.value);
    fetchConstituencyVotes(selectedConstituency.value, currentPage.value);
  }
}

void fetchConstituencies();
</script>

<style scoped>
.pageSelector {
  display: flex;
}
.pageSelector p {
  margin: 0 10px;
}
.pageButton {
  cursor: pointer;
}
.greyed-out {
  color: #555555;
  cursor: default;
}
</style>