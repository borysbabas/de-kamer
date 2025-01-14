<template>
  <div class="heatmap">
    <div>
      <div class="tootbar">
        <div>
          <label for="affiliation">Partij:</label>
          <select v-model="selectedAffiliation" @change="select" id="affiliation">
            <option v-for="affiliation in affiliations" :key="affiliation.value" :value="affiliation.value">
              {{ affiliation.label }}
            </option>
          </select>
        </div>
        <p style="margin-left: 10px">Totale Stemmen: {{ totalVotes }}</p>
        <img src="@/assets/svg/info.svg" alt="info icon, hover to show additional information" title="Hitte is gebaseerd op totaal aantal stemmen per partij" style="width: 16px; height: 16px; margin-left: 20px" />
      </div>
      <div id="map-container">
        <div id="map"></div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import L from 'leaflet' //Error import is normal, it will work
import 'leaflet/dist/leaflet.css'
import 'leaflet.heat'

let map = ref(null)
let heatData = ref([])   // Format: [latitude, longitude, intensity]
let affiliations = ref([])
let heatLayer = ref([])
let totalVotes = ref(0)
let selectedAffiliation = ref(1)

/**
 * Fetches all affiliations from the backend and sets the affiliations state.
 */
async function getAllAffiliations() {
  const response = await fetch(import.meta.env.VITE_API_URL + "/affiliation/all")
  const data = await response.json()
  affiliations.value = data.map((affiliation: any) => ({ label: affiliation.affiliationName, value: affiliation.affiliationId }))
}

/**
 * Handles the selection of an affiliation from the dropdown.
 * @param {Event} event - The change event from the dropdown.
 */
function select(event: Event) {
  const target = event.target as HTMLSelectElement
  getHeatData(parseInt(target.value))
}

/**
 * Fetches heat data for the selected affiliation from the backend and sets the heatData state.
 * @param {number} affiliationId - The ID of the selected affiliation.
 */
async function getHeatData(affiliationId: number) {
  heatData.value = []
  const response = await fetch(import.meta.env.VITE_API_URL + "/leaflet/get-municipality-votes-by-affiliationId/" + affiliationId)
  const data = await response.json()
  totalVotes.value = data.data.reduce((sum: number, item: any) => sum + item.value, 0);
  data.data.forEach((item: any) => {
    heatData.value.push([item.lat, item.lon, (item.value / totalVotes.value) * 30000])
  })
}

/**
 * Loads the heat data onto the map.
 * @param {Array} heatData - The heat data to be loaded onto the map.
 */
async function loadHeatData(heatData: any) {
  map.value.removeLayer(heatLayer.value);
  heatLayer.value = L.heatLayer(heatData, { radius: 40 }).addTo(map.value);
  //Timers are a way to make sure the map updates after the data gets loaded from backend
  //The user wont be able to feel any delay
  setTimeout(() => {
    map.value.setView([map.value.getCenter().lat, map.value.getCenter().lng + 0.0001]);
  }, 300)
  setTimeout(() => {
    map.value.setView([map.value.getCenter().lat, map.value.getCenter().lng - 0.0001]);
  }, 1000)
}

/**
 * Lifecycle hook that is called when the component is mounted.
 * Initializes the map and fetches initial data.
 */
onMounted(() => {
  getAllAffiliations()

  map.value = L.map('map', { minZoom: 7 }).setView([51.89548721167905, 5.380389376357199], 7)
  // Mount the map
  L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map.value)
  getHeatData(1)
  loadHeatData(heatData.value)

  watch(heatData, (newHeatData) => {
    loadHeatData(newHeatData)
  });
})

</script>

<style scoped>
/* Styles for the select dropdown */
select {
  padding: 10px;
  border-radius: 8px;
  font-size: 18px;
  background-color: var(--button-important);
  color: white;
  border: 2px solid var(--dark-secondary);
  cursor: pointer;
  width: 400px;
  font-family: Arial, sans-serif;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease, color 0.3s ease;
}

/* Hover state for the select dropdown */
select:hover {
  color: var(--light-primary);
}

/* Focus state for the select dropdown */
select:focus {
  outline: none;
  border-color: var(--button-important);
  box-shadow: 0 0 5px var(--button-important);
}

/* Styles for the toolbar */
.tootbar {
  display: flex;
  background: var(--dark-secondary);
  padding: 10px;
  align-items: baseline;
  justify-content: left;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* Styles for the heatmap container */
.heatmap {
  z-index: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border: 10px solid var(--dark-secondary);
  border-radius: 5px;
  width: 80vh;
  height: 100%;
}

/* Styles for the map container */
#map-container {
  width: 80vh;
  height: 500px;
}

/* Styles for the map */
#map {
  width: 100%;
  height: 100%;
}
</style>