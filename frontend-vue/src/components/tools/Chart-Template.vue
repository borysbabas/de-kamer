<template>
  <div style="height: 600px; width: 400px">
    <Doughnut
        v-if="chartType === 'doughnut'"
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
    />
    <Bar
        v-else="chartType === 'bar'"
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
    />
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
      chartData: {
        labels: [ 'January', 'February', 'March', 'April' ],
        datasets: [
          {
            label: [ 'Data One', 'Data Two', 'Data Three', 'Data Four' ],
            data: [40, 20, 12, 50],
            backgroundColor: [ '#FF6633', '#FFB399', '#FF33FF' ]
          }
        ]
      },
      chartOptions: {
        responsive: true
      }
    }
  }
}
</script>
