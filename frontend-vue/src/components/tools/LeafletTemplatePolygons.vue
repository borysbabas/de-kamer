<template>
  <div id="map-container">
    <div id="map"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

let map = ref(null)

async function getPolygonData() {
  const response = await fetch(import.meta.env.VITE_API_URL + "/leaflet/get-polygons")
  const data = await response.json()
  console.log(data);
  data.data.forEach((item: any) => {
    console.log(item.coordinates[0]);
    L.polygon(item.coordinates[0], { color: 'red' }).addTo(map.value)
  })
}


onMounted(async () => {
  map.value = L.map('map', { minZoom: 7 }).setView([51.89548721167905, 5.380389376357199], 7)
  // Mount the map
  L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map.value)
  await getPolygonData()
})
</script>

<style scoped>
#map-container
{
  width: 80vh;
  height: 500px;
}

#map {
  width: 100%;
  height: 100%;
}
</style>