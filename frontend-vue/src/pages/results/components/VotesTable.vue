<template>
  <div class="table-container">
    <h2>
      Voorkeursstemmen tabel
    </h2>
    <select v-model="selectedConstituency" @change="handleConstituencyChange" class="table-select">
      <option selected disabled value="">Selecteer een kieskring</option>
      <option v-for="constituency in constituencies" :key="constituency.constituencyId"
              :value="constituency.constituencyId">
        {{ constituency.constituencyName }}
      </option>
    </select>
    <select v-model="selectedMunicipality" v-if="municipalities.length > 0" @change="handleMunicipalityChange"
            class="table-select">
      <option selected value="0">Geen gemeente gekozen</option>
      <option v-for="municipality in municipalities" :key="municipality.municipalityId"
              :value="municipality.municipalityId">
        {{ municipality.municipalityName }}
      </option>
    </select>
    <select v-model="selectedPollingStation" v-if="pollingStations.length > 0" @change="handlePollingStationChange"
            class="table-select">
      <option selected value="0">Geen stembureau gekozen</option>
      <option v-for="pollingStation in pollingStations" :key="pollingStation.pollingStationId"
              :value="pollingStation.pollingStationId">
        {{ pollingStation.pollingStationName }}
      </option>
    </select>
    <label class="pagination-label" for="pagination">Rijen per pagina: </label>
    <input class="pagination-input" v-model="pagination" id="pagination" type="number" min="5" max="50"
           @change="handlePaginationChange">
    <table v-if="selectedConstituency || selectedMunicipality || selectedPollingStation" class="votes-table">
      <thead>
      <tr>
        <th>Kandidaat</th>
        <th>Partij</th>
        <th>Aantal stemmen</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="candidateVote in votesTableData" :key="candidateVote.candidateId">
        <td>
          {{ candidateVote.candidateName }}
        </td>
        <td>
          {{ candidateVote.affiliationName }}
        </td>
        <td>
          {{ candidateVote.totalVotes }}
        </td>
      </tr>
      </tbody>
      <tfoot>
      <tr>
        <td class="page-selector right-align">
          <p @click="handlePageChange(1)" :class="['page-button', {'greyed-out': currentPage === 1 }]">
            <<
          </p>

          <p @click="handlePageChange(currentPage - 1)" :class="['page-button', {'greyed-out': currentPage === 1 }]">
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
          <p @click="handlePageChange(currentPage + 1)"
             :class="['page-button', {'greyed-out': currentPage === Math.ceil(tableLength / pagination) }]">
            >
          </p>

          <p @click="handlePageChange(Math.ceil(tableLength / pagination))"
             :class="['page-button', {'greyed-out': currentPage === Math.ceil(tableLength / pagination) }]">
            >>
          </p>
        </td>
      </tr>
      </tfoot>
    </table>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted} from "vue";
import type {Constituency} from "@/types/Constituency";
import type {ConstituencyVotes} from "@/types/ConstituencyVotes";
import type {Municipality} from "@/types/Municipality";
import type {MunicipalityVotes} from "@/types/MunicipalityVotes";
import type {PollingStation} from "@/types/PollingStation";
import type {PollingStationVotes} from "@/types/PollingStationVotes";

// Variables
const constituencies = ref<Constituency[]>([]);
const municipalities = ref<Municipality[]>([]);
const pollingStations = ref<PollingStation[]>([]);

const votesTableData = ref<ConstituencyVotes[] | MunicipalityVotes[] | PollingStationVotes[]>([]);

const selectedConstituency = ref<number>();
const selectedMunicipality = ref<number>();
const selectedPollingStation = ref<number>();

const currentPage = ref<number>(1);
const pagination = ref<number>(20);
const tableLength = ref<number>();

// Fetch functions
async function fetchConstituencies() {
  try {
    const result = await fetch(import.meta.env.VITE_API_URL + "/constituencies");

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituencies");
    }

    constituencies.value = await result.json();
  } catch (error) {
    // TODO: error handling
    console.log(error);
  }
}

async function fetchConstituencyVotesCount(constituencyId) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/constituencies/${constituencyId}/count`);

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
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/constituencies/${constituencyId}/${pagination}/${page}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch constituency votes");
    }

    votesTableData.value = await result.json();
    selectedConstituency.value = constituencyId;
  } catch (error) {
    console.log(error);
  }
}

async function fetchMunicipalities(constituencyId) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/municipalities/constituency/${constituencyId}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch municipalities");
    }

    municipalities.value = await result.json();
  } catch (error) {
    console.log(error);
  }
}

async function fetchMunicipalityVotesCount(municipalityId) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/municipalities/${municipalityId}/count`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch municipality votes count");
    }

    tableLength.value = await result.json();
  } catch (error) {
    console.log(error);
  }
}

async function fetchMunicipalityVotes(municipalityId, pagination, page) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/municipalities/${municipalityId}/${pagination}/${page}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch municipality votes");
    }

    votesTableData.value = await result.json();
    selectedMunicipality.value = municipalityId;
  } catch (error) {
    console.log(error);
  }
}

async function fetchPollingStations(municipalityId) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/polling-station/municipality/${municipalityId}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch polling stations");
    }

    pollingStations.value = await result.json();
  } catch (error) {
    console.log(error);
  }
}

async function fetchPollingStationVotesCount(pollingStationId) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/polling-stations/${pollingStationId}/count`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch polling station votes count");
    }

    tableLength.value = await result.json();
  } catch (error) {
    console.log(error);
  }
}

async function fetchPollingStationVotes(pollingStationId, pagination, page) {
  try {
    const result = await fetch(`${import.meta.env.VITE_API_URL}/votes/polling-stations/${pollingStationId}/${pagination}/${page}`);

    if (result.status !== 200) {
      throw new Error("Failed to fetch polling station votes");
    }

    votesTableData.value = await result.json();
    selectedPollingStation.value = pollingStationId;
  } catch (error) {
    console.log(error);
  }
}

// Handle functions
function handleConstituencyChange() {
  currentPage.value = 1;
  selectedMunicipality.value = 0;
  municipalities.value = [];
  selectedPollingStation.value = 0;
  pollingStations.value = [];
  fetchConstituencyVotesCount(selectedConstituency.value);
  fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  fetchMunicipalities(selectedConstituency.value);
}

function handleMunicipalityChange(event) {
  currentPage.value = 1;
  selectedPollingStation.value = 0;
  pollingStations.value = [];
  if (event.target.value > 0) {
    fetchMunicipalityVotesCount(event.target.value);
    fetchMunicipalityVotes(event.target.value, pagination.value, currentPage.value);
    fetchPollingStations(event.target.value);
  } else {
    selectedMunicipality.value = event.target.value;
    fetchConstituencyVotesCount(selectedConstituency.value);
    fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
  }
}

function handlePollingStationChange(event) {
  currentPage.value = 1;
  if (event.target.value > 0) {
    fetchPollingStationVotesCount(event.target.value);
    fetchPollingStationVotes(event.target.value, pagination.value, currentPage.value);
  } else {
    selectedPollingStation.value = event.target.value;
    fetchMunicipalityVotesCount(selectedMunicipality.value);
    fetchMunicipalityVotes(selectedMunicipality.value, pagination.value, currentPage.value);
  }
}

function handlePageChange(selectedPage: number) {
  if (selectedPage > 0 && selectedPage <= Math.ceil(tableLength.value / pagination.value)) {
    currentPage.value = selectedPage;
    if (selectedPollingStation.value > 0) {
      fetchPollingStationVotes(selectedPollingStation.value, pagination.value, currentPage.value);
    } else if (selectedMunicipality.value > 0) {
      fetchMunicipalityVotes(selectedMunicipality.value, pagination.value, currentPage.value);
    } else {
      fetchConstituencyVotes(selectedConstituency.value, pagination.value, currentPage.value);
    }
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

// On initial load of the component it pulls the list of constituencies and loads the table with data of the first constituency
onMounted(() => {
  fetchConstituencies();
  selectedConstituency.value = 1;
  handleConstituencyChange();
});
</script>

<style scoped>
.table-container {
  padding: 10px 0 10px 0;
}

.table-select {
  background-color: var(--dark-primary);
  color: var(--light-primary);
  font-family: Beiruti, sans-serif;
  font-size: larger;
  border: none;
}

.table-select option:hover {
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
  appearance: textfield;
  -webkit-appearance: textfield;
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
  cursor: not-allowed;
}
</style>