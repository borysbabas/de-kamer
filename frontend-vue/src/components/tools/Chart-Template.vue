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
import { ref, onMounted } from 'vue'
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

let props = defineProps<{
  chartData: any[]
}>();

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

const options = chartConfig.options
const data = ref<ChartData<'bar'>>({
  datasets: [],
})
// const testDataSet = [
//   {
//     data: [
//       { label: "test 1", value: 10 },
//       { label: "test 2", value: 20 },
//       { label: "test 3", value: 30, color: "blue" },
//       { label: "test 4", value: 40, color: "red" },
//     ],
//     title: "test",
//   }
// ]
// let datashow = JSON.parse(JSON.stringify(testDataSet));
let chartData = ref<any[]>([])

//temp get data
async function getChartData() {
  chartData.value = await fetch("http://localhost:3001/affiliation/members/count")
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        return data;
      });
}

onMounted(async() => {
  const result = await getChartData()
  console.log(chartData.value)
  data.value = chartConfig.generateData(chartData.value)
})
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