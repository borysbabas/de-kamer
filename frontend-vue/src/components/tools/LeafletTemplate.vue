<template>
  <div id="map-container">
    <div id="map"></div>
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted, watch} from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import 'leaflet.heat'

let map = ref(null)
let heatData = ref[null]   // Format: [latitude, longitude, intensity]

onMounted(() => {
  map.value = L.map('map').setView([52.161304, 4.436475], 15)
  // Mount the map
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  }).addTo(map.value)
  watch(heatData, () =>  {
    if (heatData.value) {
      L.heatLayer(heatData.value, { radius: 75 }).addTo(map.value)
    }
  })
})


</script>

<style scoped>
#map-container {
  width: 100%;
  height: 500px;
}

#map {
  width: 100%;
  height: 100%;
}
</style>