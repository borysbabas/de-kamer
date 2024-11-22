<script setup lang="ts">
import {ref} from "vue";

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

const constituencies = ref<Constituency[]>([]);

const constituencyVotes = ref<ConstituencyVotes[]>([]);

const selectedConstituency = ref<any>();

const n = ref<number>(0);

const tableLength = ref<number>();

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

async function fetchConstituencyVotes(constituencyId) {
  try {
    const result = await fetch(`http://localhost:3001/votes/constituencies/${constituencyId}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituency votes");
    }

    constituencyVotes.value = await result.json();
    tableLength.value = constituencyVotes.value.length;
    console.log(tableLength.value);
    selectedConstituency.value = constituencyId;
  } catch (error) {
    console.log(error);
  }
}

function handleConstituencyChange(event) {
  fetchConstituencyVotes(event.target.value);
}

function handlePageDown() {
  if (n.value !== 0) {
    n.value -= 1;
  }
}

function handlePageUp() {
  n.value += 1;
}

void fetchConstituencies();
</script>

<template>
  <select @change="handleConstituencyChange">
    <option selected disabled value="">-- Kies een kieskring --</option>
    <option v-for="constituency in constituencies.slice(1, constituencies.length)" :key="constituency.constituencyId" :value="constituency.constituencyId">
      {{ constituency.constituencyName }}
    </option>
  </select>
  <table v-if="selectedConstituency">
    <tr>
      <th>Kandidaat</th>
      <th>Partij</th>
      <th>Aantal stemmen</th>
    </tr>
    <tr v-for="constituencyVote in constituencyVotes.slice(n * 30, n * 30 + 30)" :key="constituencyVote.candidateId">
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
    <p @click="handlePageDown" v-if="n !== 0" class="pageButton">
      <
    </p>
    <p v-if="n === 0" class="greyed-out">
      <
    </p>

    <p v-if="(n * 30 + 30) < tableLength">
      {{ n * 30 + 1 }} - {{ n * 30 + 30 }}
    </p>
    <p v-if="(n * 30 + 30) >= tableLength">
      {{ n * 30 + 1 }} - {{ tableLength }}
    </p>

    <p @click="handlePageUp" class="pageButton" v-if="(n * 30 + 30) < tableLength">
      >
    </p>
    <p v-if="(n * 30 + 30) >= tableLength" class="greyed-out">
      >
    </p>
  </div>
</template>

<style scoped>
</style>