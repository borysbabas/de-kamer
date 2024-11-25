<template>
  <div class="background">
    <div class="border">
      <div class="layer">
        <button
            @click="sort"
            class="sortbutton"
        >Sort items</button>
      </div>
      <div class="chartContainer">
        <div
            id="chart"
            class="apex-chart"
        ></div>
        <apex-chart-interface
            class="apex-interface"
            v-if="interface"
            @hide-hightlight="hideHightlight"
            @select="select"
      ></apex-chart-interface>
      </div>


    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import ApexCharts from 'apexcharts'
import ApexChartInterface from "@/components/tools/chartConfig/Apex-Chart-Interface.vue";
import {chartData, setChartData, sortData} from "@/composables/useChartConfig";
import {curretDataSet} from "@/composables/useChartDataSelector";

const props = defineProps<{
  interface: boolean,
  chartType?: string,
  chartTypeInfo?: number,
}>()

let chart: ApexCharts;
let sorted = ref(false);
let selectedChart = ref('');


// Chart configuration
var options = {
  series: [
    {
      name: 'Data',
      data: [1, 2],
    }
  ],
  chart: {
    width: '100%',
    type: "bar",
  },
  plotOptions: {
    bar: {
      horizontal: true,
    },
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
  setChartData();
  watch(curretDataSet, () => {
    updateChart();
  });
});

function updateChart() {
  chart.updateSeries(chartData.value);
  sorted = false;
}

function sort() {
  if (sorted) {
    chartData.value = sortData('asc');
    chart.updateSeries(chartData.value);
    sorted = false;
    return;
  }
  sorted = true;
  chartData.value = sortData('desc');
  chart.updateSeries(chartData.value);
}

function hideHightlight(id: number) {
  dataset = apex.hideHighlight(dataset, id);
  chart.updateSeries(dataset);
  sorted = !sorted;
  sort();
}
function select(id: number) {
  const selectedItem = chartData.value[0].data.find(item => item.id === id);
  console.log("Selected Item:", selectedItem);
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
.chartContainer {
  display: flex;
  justify-content: center;
  align-content: flex-start;
  margin: 10px;
}
.apex-interface {
  width: 25%;
}
.apex-chart {
  width: 75%;
  margin-right: 50px;
}

</style>