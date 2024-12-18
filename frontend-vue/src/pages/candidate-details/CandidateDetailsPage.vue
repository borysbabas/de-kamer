<template>
  <Navbar></Navbar>
  <section>
    <div class="container">
      <div class="wrapper">
        <h1>{{ fetchedData.firstName }} <span v-if="fetchedData.prefix"> {{ fetchedData.prefix }}</span>
          {{ fetchedData.lastName }}</h1>
        <h2 v-if="fetchedData.gender !== ``">Geslacht: {{ genderToDutch(fetchedData.gender) }}</h2>
        <h2>Totaal aantal stemmen op {{ fetchedData.name }}: {{ fetchedData.votes }}</h2>
        <h2>Partij: {{ fetchedData.affiliationName }}</h2>
        <div
            id="chart1"
            class="apex-chart"
        >
        </div>
        <div
            id="chart2"
            class="apex-chart"
        >
        </div>
      </div>
    </div>
  </section>
  <Footer></Footer>
</template>

<script setup lang="ts">
import Navbar from "@/pages/navbar/Navbar.vue";
import Footer from "@/pages/footer/Footer.vue"
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import ApexCharts from "apexcharts";

function genderToDutch(gender: string) {
  switch (gender) {
    case "male":
      return "Man";
    case "female":
      return "Vrouw";
    default:
      return "n.v.t.";
  }
}

const props = defineProps({
  candidateId: {
    type: String,
    required: false
  }
});

const route = useRoute();
const candidateId = ref(null);
let chart1: ApexCharts;
let chart2: ApexCharts;
let chartData = ref([]);

window.ApexCharts = ApexCharts;

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

const chartOptions1 = ref({
  series: [
    {
      name: "Data",
      type: "bar",
      data: [{x: "test", y: 1}, {x: "test2", y: 2}]
    }
  ],
  chart: {
    id: "chart1",
    width: "100%",
    height: 400,
    type: "line",
    toolbar: {
      show: false,
    },
    zoom: {
      show: false,
    },
  },
  xaxis: {
    labels: {
      show: true,
      style: {
        colors: "var(--light-primary)",
      }
    }
  },
  colors: ["#00E396"],
  dataLabels: {
    enabled: false
  },
  legend: {
    show: false,
    position: "top"
  },
  tooltip: {
    enabled: true,
    theme: "dark",
    x: {
      show: true
    },
    y: {
      formatter: function (val) {
        return val;
      }
    },
  }
});

const chartOptions2 = ref({
  series: [
    {
      name: "Data",
      // type: "bar",
      data: [{x: "test", y: 1}, {x: "test2", y: 2}]
    }
  ],
  chart: {
    id: "chart2",
    width: "100%",
    height: 200,
    type: "line",
    brush: {
      enabled: true,
      target: "chart1"
    },
    toolbar: {
      show: false,
    },
    zoom: {
      show: false,
    },
    selection: {
      enabled: true,
      type: "x",
      xaxis: {
        min: 0,
        max: 20.
      }
    }
  },
  xaxis: {
    labels: {
      show: false
    }
  },
  colors: ["#00E396"],
  dataLabels: {
    enabled: false
  },
  legend: {
    show: false,
    position: "top"
  },
  tooltip: {
    enabled: true,
    theme: "dark",
    x: {
      show: true
    },
    y: {
      formatter: function (val) {
        return val;
      }
    }
  }
});

async function fetchCandidate() {
  candidateId.value = route.params.candidateId;
  if (candidateId.value) {
    const response = await fetch(
        import.meta.env.VITE_API_URL + `/candidates/${candidateId.value}`
    );
    fetchedData.value = await response.json();
  } // TODO: error handeling
}

async function fetchMunicipalityData() {
  candidateId.value = route.params.candidateId;
  if (candidateId.value) {
    const response = await fetch(
        import.meta.env.VITE_API_URL + `/get-municipality-votes-by-candidateId/${candidateId.value}`
    );
    const json = await response.json();
    let municipalityData = json.map((item) => {
      return {
        x: item.municipalityName,
        y: item.totalVotes
      };
    });
    chartData.value = [{name: "Stemmen", data: municipalityData}];
  }
}

const updateChartData = () => {
  chart1.updateSeries(chartData.value);
  chart2.updateSeries(chartData.value);
};

const initChart = () => {
  const chart1Element = document.querySelector("#chart1");
  const chart2Element = document.querySelector("#chart2");

  if (chart1Element && chart2Element) {
    chart1 = new ApexCharts(chart1Element, chartOptions1.value); // Initialize chart1 first
    chart1.render();


    chart2 = new ApexCharts(chart2Element, chartOptions2.value); // Then chart2
    chart2.render();

    chart2.addEventListener('brushScrolled', (event) => {
      const {xaxis} = event.detail
      chartSelection(xaxis);
    })
  } else {
    console.error("Chart elements not found");
  }
};

onMounted(async () => {
  await fetchCandidate();
  await fetchMunicipalityData();
  initChart();
  updateChartData();
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


<style scoped>

</style>

// https://apexcharts.com/vue-chart-demos/line-charts/brush-chart/
