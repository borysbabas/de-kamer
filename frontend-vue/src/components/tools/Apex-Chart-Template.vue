<template>
  <div>
    <div id="chart"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import ApexCharts from 'apexcharts'
import * as apex from '@/components/tools/chartConfig/ApexChartConfig'


const props = defineProps<{
  chartData: any[]
}>()

let chart: ApexCharts;



var options = {
  series: [
    {
      name: 'Data',
      data: [],
    }
  ],
  chart: {
    height: 800,
    type: 'bar'
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
    const dataset = apex.normalizeData(props.chartData);
    console.log(dataset);
    const sortedData = apex.sortData(dataset);
    console.log(sortedData);
    chart.updateSeries(sortedData);
  }, { immediate: true });
});


</script>


<style scoped>

</style>