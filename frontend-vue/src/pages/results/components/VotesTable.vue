<template>
  <div class="table-container">
    <h2>
      Voorkeursstemmen tabel
    </h2>
    <select @change="handleConstituencyChange" class="constituency-select">
      <option selected disabled value="">Selecteer een kieskring </option>
      <option v-for="constituency in constituencies" :key="constituency.constituencyId" :value="constituency.constituencyId">
        {{ constituency.constituencyName }}
      </option>
    </select>
    <label class="pagination-label" for="pagination">Rijen per pagina: </label>
    <input class="pagination-input" v-model="pagination" id="pagination" type="number" min="5" max="50" @change="handlePaginationChange">
    <table v-if="selectedConstituency" class="votes-table">
      <thead>
        <tr>
          <th>Kandidaat</th>
          <th>Partij</th>
          <th>Aantal stemmen</th>
        </tr>
      </thead>
      <tbody>
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
      </tbody>
      <tfoot>
        <tr>
          <td class="page-selector right-align">
            <p @click="handleFirstPage" :class="['page-button', {'greyed-out': currentPage === 1 }]">
              <<
            </p>

            <p @click="handlePageDown" :class="['page-button', {'greyed-out': currentPage === 1 }]">
              <
            </p>
          </td>

          <td>
            <div class="center-align">
              <p>
                {{ currentPage }} / {{ Math.ceil(tableLength / pagination) }}
              </p>
            </div>
          </td>

          <td class="page-selector left-align">
            <p @click="handlePageUp" :class="['page-button', {'greyed-out': currentPage === Math.ceil(tableLength / pagination) }]">
              >
            </p>

            <p @click="handleLastPage" :class="['page-button', {'greyed-out': currentPage === Math.ceil(tableLength / pagination) }]">
              >>
            </p>
          </td>
        </tr>
      </tfoot>
    </table>
  </div>
</template>

<script setup lang="ts">
import {ref} from "vue";
import type {Constituency} from "@/types/Constituency";
import type {ConstituencyVotes} from "@/types/ConstituencyVotes";

// Variables
const constituencies = ref<Constituency[]>([]);

let constituencyVotes = ref<ConstituencyVotes[]>([]);

let selectedConstituency = ref<number>();

let n = ref<number>(0);

let currentPage = ref<number>(1);

let pagination = ref<number>(20);

let tableLength = ref<number>();

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

async function fetchConstituencyVotes(constituencyId, pagination, page) {
  try {
    const result = await fetch(`http://localhost:3001/votes/constituencies/${constituencyId}/${pagination}/${page}`);

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
  fetchConstituencyVotes(event.target.value, pagination.value, currentPage.value);
}

function handleFirstPage() {
  if (currentPage.value !== 1) {
    currentPage.value = 1;
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  }
}

function handlePageDown() {
  if (currentPage.value !== 1) {
    currentPage.value -= 1;
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  }
}

function handlePageUp() {
  if (currentPage.value < Math.ceil(tableLength.value / pagination.value)) {
    currentPage.value += 1;
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  }
}

function handleLastPage() {
  if (currentPage.value < Math.ceil(tableLength.value / pagination.value)) {
    currentPage.value = Math.ceil(tableLength.value / pagination.value);
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  }
}

function handlePaginationChange() {
  if (pagination.value > 50) {
    pagination.value = 50;
  } else if (pagination.value < 5) {
    pagination.value = 5;
  }
  currentPage.value = 1;
  if (selectedConstituency.value) {
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value)
  }
}

void fetchConstituencies();
</script>

<style scoped>
.table-container {
  padding: 10px 0 10px 0;
}

.constituency-select {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  font-family: Beiruti, sans-serif;
  font-size: larger;
  border: none;
}
.constituency-select option:hover {
  background-color: var(--button);
}

.votes-table thead th, tbody td {
  border: solid var(--light-primary) 2px;
  border-radius: 5px;
  padding: 5px;
  min-width: 275px;
}
.votes-table thead {
  background-color: var(--button);
}
.votes-table tfoot td {
  padding: 3px;
}

.center-align {
  display: flex;
  justify-content: center;
}
.pagination-input {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  border: none;
  border-bottom: solid var(--light-primary) 1px;
  max-width: 20px;
  text-align: center;
}
.pagination-label {
  margin-left: 20px;
}
input[type=number]::-webkit-inner-spin-button {
  -webkit-appearance: none;
}

.page-selector {
  display: flex;
}
.page-selector p {
  margin: 0 10px;
}
.right-align {
  justify-content: right;
}
.left-align {
  justify-content: left;
}
.page-button {
  cursor: pointer;
}
.greyed-out {
  color: #555555;
  cursor: default;
}
</style>