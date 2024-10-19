<template>
  <div class="chart-container">
    <Doughnut
        v-if="chartType === 'doughnut'"
        id="my-chart-id"
        :options="chartOptions"
        :data="testData"
        ref="chart"
    />
    <Bar
        v-else="chartType === 'bar'"
        id="my-chart-id"
        :options="chartOptions"
        :data="testData"
        ref="chart"
    />
  </div>
  <div>
    <div v-for="(data, index) in testData.datasets[0].data" :key="index">
      <button @click="hideValue(index)">Highlight segment {{index}}</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Bar, Doughnut } from 'vue-chartjs'
import {
  Chart as ChartJS,
  ArcElement,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, ArcElement)

export default {
  name: 'ChartTemplate',
  components: { Bar, Doughnut },
  props: {
    chartType: {
      type: String,
      required: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chartOptions: {
        responsive: true
      },
      testData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'Monthly Data',
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#FF6384'],
            data: [65, 59, 80, 81, 56, 55, 40]
          }
        ]
      }
    }
  },
  methods: {
    hideValue(index: number) {
      const chart = this.$refs.chart?.chart
      console.log('hideValue', index)
      if (chart) {
        chart.data.datasets[0].data = chart.data.datasets[0].data.map((value, i) => {
          return i === index ? null : value
        })
        chart.update()
      } else {
        console.log('chart not found')
      }
    }
  }
}
</script>

<style scoped>
.chart-container {
  height: 400px;
  width: 400px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
}
</style>