<template>
  <div class="background" id="chart-box">
    <div class="border">
      <div class="layer">
        <button
            @click="sort"
            class="sortbutton"
        >Sorteer Grafiek
        </button>
      </div>
      <div class="chartContainer">
        <div
            id="chart"
            class="apex-chart"
        ></div>
        <apex-chart-interface
            class="apex-interface"
            height="100%"
            v-if="interface"
            @hide-hightlight="hideHightlight"
            @select="select"
        ></apex-chart-interface>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import ApexCharts from 'apexcharts';
import ApexChartInterface from '@/components/tools/chartConfig/ApexChartInterface.vue';
import {chartData, setChartData, sortData} from '@/composables/useChartConfig';
import {curretDataSet} from '@/composables/useChartDataSelector';

// Define component props
defineProps<{
  interface: boolean,
}>();

let chart: ApexCharts;
const sorted = ref(false);

// Chart configuration options
const options = {
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
    },
  },
};

// Lifecycle hook to initialize the chart when the component is mounted
onMounted(() => {
  chart = new ApexCharts(document.querySelector("#chart"), options);
  chart.render();
  setChartData();
  extendItemHeight();
  watch(curretDataSet, () => {
    updateChart();
  });
});

/**
 * Updates the chart with new data.
 */
function updateChart() {
  chart.updateSeries([chartData.value]);
  // TODO: find out whats wrong
  // chart.updateOptions({
  //     chart: {
  //         height: 800,
  //     }
  // });
  sorted.value = false;
  // extendItemHeight();
}

/**
 * Extends the height of the chart based on the data length.
 */
function extendItemHeight() {
  if (!chartData.value || !chartData.value.data) return
  chart.updateOptions({
    chart: {
      height: chartData.value.data.length * 20 + 100,
    }
  }).catch((e) => console.error(e));
}

/**
 * Sorts the chart data in ascending or descending order.
 */
function sort() {
  if (sorted.value) {
    chartData.value = sortData('asc');
    chart.updateSeries([chartData.value]);
    sorted.value = false;
    return;
  }
  sorted.value = true;
  chartData.value = sortData('desc');
  chart.updateSeries([chartData.value]);
}

/**
 * Hides the highlight for a given item.
 * @param {number} id - The ID of the item to hide the highlight for.
 */
function hideHightlight(id: number) {
  return;
}

/**
 * Selects an item in the chart.
 * @param {number} id - The ID of the item to select.
 */
function select(id: number) {
  const selectedItem = chartData.value[0].data.find((item: any) => item.id === id);
}
</script>

<style scoped>
/* Styling for the sort button */
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

/* Styling for select options */
.selectOptions option {
  padding: 30px;
  font-size: 20px;
  cursor: pointer;
}

/* Background styling */
.background {
  background-color: var(--dark-primary);
  padding: 30px;
}

/* Border container styling */
.border {
  padding: 10px;
  border: 10px solid var(--light-primary);
  border-radius: 10px;
  background: var(--light-primary);
}

/* Layer container styling */
.layer {
  display: flex;
  justify-content: left;
  margin: 10px;
}

/* Chart container styling */
.chartContainer {
  display: flex;
  justify-content: center;
  align-content: flex-start;
  margin: 10px;
  min-height: 600px;
  height: 100%;
}

/* Styling for the chart interface */
.apex-interface {
  width: 25%;
  height: 100%;
}

/* Styling for the chart */
.apex-chart {
  width: 75%;
  margin-right: 50px;
}
</style>