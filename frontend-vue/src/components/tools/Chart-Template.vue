<template>
  <div class="chart-container">
    <h1>Chart Template</h1>
    <div v-for="item in chartData" :key="item.title">
      <h2>{{ item.title }}</h2>
<!--      <div v-for="data in item.data" :key="data.label" class="chart-data">-->
<!--        <p>{{ data.label }}: {{ data.value }}</p>-->
<!--        <button @click="toggleData(data.label)">Toggle</button>-->
<!--      </div>-->
    </div>
  </div>
  <div style="height: 400px">
    <Bar :data="data" :options="options" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  ChartData
} from 'chart.js'
import { Bar } from 'vue-chartjs'
import * as chartConfig from '@/components/tools/chartConfig/chartConfig'

const props = defineProps<{
  chartData: any[]
}>()

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

const options = chartConfig.options
const data = ref<ChartData<'bar'>>({
  datasets: [],
});

watch(() => props.chartData, (newChartData) => {
  data.value = chartConfig.generateData(newChartData);
}, { immediate: true });
</script>

<style scoped>
.chart-container {
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  color: #000000; /* Set text color to black */
}

.chart-item {
  margin-bottom: 20px;
}

.chart-data {
  background-color: #ffffff;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-bottom: 10px;
  color: #000000; /* Set text color to black */
  display: flex; /* Align text and button horizontally */
}
</style>