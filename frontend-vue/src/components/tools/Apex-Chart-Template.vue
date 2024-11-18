<template>
  <div class="background">
    <div class="border">
      <div class="layer">
        <button
            @click="sort"
            class="sortbutton"
        >Sort items</button>
        <select
            v-model="selectedChart"
            @change="updateChart"
            class="selectOptions"
        >
          <option
              v-for="key in Object.keys(chartData)"
              :key="key"
          >{{ key }}</option>
        </select>
      </div>
      <div id="chart"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import ApexCharts from 'apexcharts'
import * as apex from '@/components/tools/chartConfig/ApexChartConfig'
import {c} from "vite/dist/node/types.d-aGj9QkWt";


const props = defineProps<{
  chartData: any[],
}>()

let chart: ApexCharts;
let dataset: any[] = [];
let sorted = ref(false);
let selectedChart = ref('');


// Chart configuration
var options = {
  series: [
    {
      name: 'Data',
      data: [],
    }
  ],
  chart: {
    height: 800,
    width: '80%',
    type: 'bar',
  },
  plotOptions: {
    bar: {
      horizontal: true,
    }
  },
  colors: ['#00E396'],
  dataLabels: {
    enabled: false
  },
  legend: {
    show: false,
    position: 'top',
  },
  tooltip: {
    enabled: true,
    theme: 'dark',
    x: {
      show: true
    }
  },
};


onMounted(() => {
  chart = new ApexCharts(document.querySelector("#chart"), options);
  chart.render();

  watch(() => props.chartData, (newChartData) => {
    console.log(props.chartData.length, "test");
    if (props.chartData.length !== 0) {
      console.log("No data");
      selectedChart.value = Object.keys(props.chartData)[0];
    const chartinfo = apex.formatData(props.chartData[selectedChart.value]);
      dataset = apex.normalizeData(chartinfo);
      chart.updateSeries(dataset);
      sort();
    }
  }, { immediate: true });
});

function updateChart() {
  const chartinfo = apex.formatData(props.chartData[selectedChart.value]);
  dataset = apex.normalizeData(chartinfo);
  chart.updateSeries(dataset);
  sorted = true;
  sort();
}

function sort() {
  if (sorted) {
    sorted = false;
    const sortedData = apex.sortData(dataset, 'asc');
    chart.updateSeries(sortedData);
    return;
  }
  sorted = true;
  const sortedData = apex.sortData(dataset, 'desc');
  chart.updateSeries(sortedData);
}


</script>


<style scoped>
.sortbutton {
  background-color: var(--button-important);
  color: var(--light-primary);
  border: 10px solid var(--button-important);
  padding: 2px;
  width: 120px;
  border-radius: 10px;
  font-size: 20px;
  cursor: pointer;
  font-family: inherit;
  margin-right: 10px;
}
.selectOptions {
  background-color: var(--button-important);
  color: var(--light-primary);
  border: 10px solid var(--button-important);
  padding: 2px;
  width: 120px;
  border-radius: 10px;
  font-size: 20px;
  cursor: pointer;
  font-family: inherit;
  overflow: auto; /* Add overflow to handle the items */
  max-height: 200px; /* Set a max height to allow scrolling */
}

.selectOptions option  {
  padding: 30px;
  font-size: 20px;
  cursor: pointer;
}

.background {
  background-color: var(--dark-primary);
  padding: 30px;

}
.border {
  padding: 10px;
  border: 10px solid var(--light-primary);
  border-radius: 10px;
  background: var(--light-primary);
}
.layer {
  display: flex;
  justify-content: left;
  margin: 10px;
}

</style>