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
      <div class="chartContainer">
        <div
            id="chart"
            class="apex-chart"
        ></div>
        <apex-chart-interface
            class="apex-interface"
            v-if="interface"

            :chartData="dataset[0]"
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
import * as apex from '@/components/tools/chartConfig/ApexChartConfig'
import ApexChartInterface from "@/components/tools/chartConfig/Apex-Chart-Interface.vue";


const props = defineProps<{
  chartData: any[],
  interface: boolean,
  chartType?: string,
  chartTypeInfo?: number,
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
  watch(() => props.chartData, (newChartData) => {
    if (props.chartData.length !== 0) {
      selectedChart.value = Object.keys(props.chartData)[0];
      updateChart();
    }
  }, { immediate: true });
});

function updateChart() {
  dataset = apex.normalizeData(apex.formatData(props.chartData[selectedChart.value]));
  chart.updateSeries(dataset);
  sorted = true;
  sort();
}

function sort() {
  if (sorted) {
    dataset = apex.sortData(dataset, 'asc');
    chart.updateSeries(dataset);
    sorted = false;
    return;
  }
  sorted = true;
  dataset = apex.sortData(dataset, 'desc');
  chart.updateSeries(dataset);
}

function hideHightlight(id: number) {
  dataset = apex.hideHighlight(dataset, id);
  chart.updateSeries(dataset);
  sorted = !sorted;
  sort();
}
function select(id: number) {
  const selectedItem = dataset[0].data.find(item => item.id === id);
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