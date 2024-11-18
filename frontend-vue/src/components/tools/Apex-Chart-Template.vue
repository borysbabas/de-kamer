<template>
  <div>
    <div>
      <button @click="sort">Sort</button>
    </div>
    <div id="chart"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import ApexCharts from 'apexcharts'
import * as apex from '@/components/tools/chartConfig/ApexChartConfig'
import {c} from "vite/dist/node/types.d-aGj9QkWt";


const props = defineProps<{
  chartData: any[]
}>()

let chart: ApexCharts;
let dataset: any[] = [];
let sorted = ref(false);


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
    const chartinfo = apex.formatData(props.chartData)
    console.log(chartinfo, 'chartinfo')
    if (chartinfo) {
      dataset = apex.normalizeData(chartinfo);
      chart.updateSeries(dataset);
      sort();
    }
  }, { immediate: true });
});

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

</style>