<template>
  <div class="heatmap">
    <div>
      <h1>Heatmap</h1>
      <p>Stemmen</p>
      <select v-model="selectedAffiliation" @change="select">
        <option v-for="affiliation in affiliations" :key="affiliation.value" :value="affiliation.value">
          {{ affiliation.label }}
        </option>
      </select>
      <div id="map-container">
        <div id="map"></div>
      </div>
    </div>
    <div id="map-container">
      <div id="map"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import 'leaflet.heat'

let map = ref(null)
let heatData = ref([])   // Format: [latitude, longitude, intensity]
let affiliations = ref([])
let heatLayer = ref([])
let totalVotes = ref(0)

async function getAllAffiliations() {
  const response = await fetch(import.meta.env.VITE_API_URL + "/affiliation/all")
  const data = await response.json()
  affiliations.value = data.map((affiliation: any) => ({ label: affiliation.affiliationName, value: affiliation.affiliationId }))
}

function select(event: Event) {
  const target = event.target as HTMLSelectElement
  getHeatData(parseInt(target.value))
}

async function getHeatData(affiliationId: number) {
  heatData.value = []
  const response = await fetch(import.meta.env.VITE_API_URL + "/leaflet/get-municipality-votes-by-affiliationId/" + affiliationId)
  const data = await response.json()
  totalVotes.value = data.data.reduce((sum: number, item: any) => sum + item.value, 0);
  data.data.forEach((item: any) => {
    heatData.value.push([item.lat, item.lon, (item.value / totalVotes.value) * 30000])
  })
}

onMounted(() => {
  getAllAffiliations()

  map.value = L.map('map', { minZoom: 7 }).setView([51.89548721167905, 5.380389376357199], 7)
  // Mount the map
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map.value)
  watch(heatData, (newHeatData) => {
      map.value.removeLayer(heatLayer.value);
      heatLayer.value = L.heatLayer(newHeatData, { radius: 30 }).addTo(map.value);
      console.log(map.value.getCenter().lng)

    //Timers are a way to make sure the map updates after the data gets loaded from backend
    //The user wont be able to feel any delay
    setTimeout(() => {
      map.value.setView([map.value.getCenter().lat, map.value.getCenter().lng + 0.0001], 7);
    }, 300)
    setTimeout(() => {
      map.value.setView([map.value.getCenter().lat, map.value.getCenter().lng - 0.0001], 7);
    }, 1000)
  });
})


</script>

<style scoped>

.heatmap {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

#map-container
{
  width: 1000px;
  height: 600px;
}

#map {
  width: 100%;
  height: 100%;
}
</style>