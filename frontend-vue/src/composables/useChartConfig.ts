import {ref } from "vue";
import {curretDataSet} from "@/composables/useChartDataSelector";

export let chartData = ref([]);

export const sortData = (order: string) => {
    console.log('sortData', order, chartData.value);
    const sortedData = chartData.value.data.sort((a: any, b: any) => order === 'asc' ? b.y - a.y : a.y - b.y);
    return { name: chartData.value.name, data: sortedData };
}

export const setChartData = async () => {
    console.log('setChartData', curretDataSet.value);
    if (curretDataSet.value.length === 0) {
        return;
    }

    curretDataSet.value.data.forEach((item: any) => {
        if (!item.fillColor || item.fillColor.trim() === '') {
            item.fillColor = getRandomColor();
        }
    });
    chartData.value = curretDataSet.value;
    sortData('asc');
}




function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}