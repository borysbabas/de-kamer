<script setup lang="ts">
import Navbar from "@/pages/navbar/components/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue"
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import {Chart, registerables} from "chart.js";

Chart.register(...registerables);

const route = useRoute();
const candidateId = ref(null);
const municipalityData = ref<any[]>([]);
let isChartResized: boolean = ref(false);

const fetchedData = ref({
  candidateId: "",
  gender: "",
  name: "",
  firstName: "",
  prefix: "",
  lastName: "",
  affiliationName: "",
  votes: ""
});

async function fetchCandidate() {
  candidateId.value = route.params.candidateId;
  if (candidateId.value) {
    const response = await fetch(
        `http://localhost:3001/candidates/${candidateId.value}`
    );
    const json = await response.json();
    fetchedData.value = json;
  } else {
    console.log("error");
  }
}

function createChart() {
  const dataArray = municipalityData.value;
  const labels = dataArray.map((municipality: { municipalityName: string }) => municipality.municipalityName);
  const data = dataArray.map((municipality: { totalVotes: number }) => municipality.totalVotes);
  const ctx = document.getElementById("municipalityChart") as HTMLCanvasElement;

  if (window.myChart instanceof Chart) {
    window.myChart.destroy();
  }

  window.myChart = new Chart(ctx, {
    type: "bar",
    data: {
      labels: labels,
      datasets: [
        {
          label: "Aantal stemmen",
          data: data,
          backgroundColor: "rgba(54, 162, 235, 0.2)",
          borderColor: "rgba(54, 162, 235, 1)",
          borderWidth: 1,
        },
      ],
    },
    options: {
      responsive: true,
      scales: {
        y: {
          beginAtZero: true,
          type: "linear",
        },
      },
    },
  });
}

async function fetchMunicipalityData() {
  candidateId.value = route.params.candidateId;
  if (candidateId.value) {
    const response = await fetch(
        `http://localhost:3001/get-municipality-votes-by-candidateId/${candidateId.value}`
    );
    const json = await response.json();
    if (isChartResized.value === true) {
      municipalityData.value = json.slice(0, 10);
      createChart();
    } else if (isChartResized.value === false) {
      municipalityData.value = json
      createChart()
    }
  }
}

onMounted(() => {
  fetchCandidate();
  fetchMunicipalityData();
});

watch(
    () => route.params.candidateId,
    (changedCandidateId) => {
      candidateId.value = changedCandidateId;
      fetchCandidate();
      fetchMunicipalityData();
    }
);
</script>

<template>
  <Navbar></Navbar>
  <section class="main">
    <div class="container">
      <div class="wrapper">
        <H1>{{ fetchedData.firstName }} <span v-if="fetchedData.prefix"> {{ fetchedData.prefix }}</span>
          {{ fetchedData.lastName }}</H1>
        <H2 v-if="fetchedData.gender !== ``">geslacht: {{ fetchedData.gender }}</H2>
        <H2>Totaal aantal stemmen op {{ fetchedData.name }}: {{ fetchedData.votes }}</H2>
        <H2>Partij: {{ fetchedData.affiliationName }}</H2>
        <canvas id="municipalityChart"></canvas>
        <input type="checkbox" id="checkbox" @click="fetchMunicipalityData" v-model="isChartResized">
        <label for="checkbox">Top 10 gemeentes weergeven</label>
      </div>
    </div>
  </section>
  <Footer></Footer>
</template>

<style scoped>
.main {
  min-height: calc(100vh - 56px);
  object-fit: cover;
  background-color: var(--dark-primary);
  color: var(--light-primary);
  padding: 0 var(--page-padding);
  position: relative;
  z-index: 0;
}

.container {
  padding-top: 160px;
}
</style>
