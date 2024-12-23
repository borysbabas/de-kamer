<template>
  <div class="background">
    <div class="border">
      <div class="layer">
        <button
            @click="sort"
            class="sortbutton"
        >Sorteer Grafiek</button>
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
import ApexChartInterface from "@/components/tools/chartConfig/ApexChartInterface.vue";
import {chartData, setChartData, sortData} from "@/composables/useChartConfig";
import {curretDataSet} from "@/composables/useChartDataSelector";

defineProps<{
  interface: boolean,
}>()

let chart: ApexCharts;
let sorted = ref(false);


// Chart configuration
let options = {
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
  extendItemHeight();
  watch(curretDataSet, () => {
    updateChart();
  });
});

function updateChart() {
  chart.updateSeries([chartData.value]);
  sorted = false;
  // extendItemHeight();
}

function extendItemHeight() {
  if (!chartData.value || !chartData.value.data) return;
  chart.updateOptions({
    chart: {
      height: chartData.value.data.length * 20 + 100,
    }
  });
}

function sort() {
  if (sorted) {
    chartData.value = sortData('asc');
    chart.updateSeries([chartData.value]);
    sorted = false;
    return;
  }
  sorted = true;
  chartData.value = sortData('desc');
  chart.updateSeries([chartData.value]);
}
function hideHightlight(id: number) {
  // chartData = apex.hideHighlight(dataset, id);
  // chart.updateSeries(dataset);
  // sorted = !sorted;
  // sort();
  return;
}
function select(id: number) {
  const selectedItem = chartData.value[0].data.find(item => item.id === id);
}


</script>


<style scoped>
.sortbutton {
  background-color: var(--button-important);
  color: var(--light-primary);
  border: 10px solid var(--button-important);
  padding: 2px;
  width: auto;
  border-radius: 10px;
  font-size: 20px;
  cursor: pointer;
  font-family: inherit;
  margin-right: 10px;
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
  min-height: 600px;
  height: 100%;
}
.apex-interface {
  width: 25%;
  height: 100%;
}
.apex-chart {
  width: 75%;
  margin-right: 50px;
}

</style>