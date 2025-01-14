import { ref } from "vue";
import { curretDataSet } from "@/composables/useChartDataSelector";

/**
 * Reactive reference to hold chart data.
 * @type {Ref<Array<any>>}
 */
export let chartData = ref([]);

/**
 * Sorts the chart data in ascending or descending order based on the 'y' property.
 * @param {string} order - The order to sort the data ('asc' for ascending, 'desc' for descending).
 * @returns {Object} - The sorted chart data with the same name.
 */
export const sortData = (order: string) => {
    const sortedData = chartData.value.data.sort((a: any, b: any) => order === 'asc' ? b.y - a.y : a.y - b.y);
    return { name: chartData.value.name, data: sortedData };
}

/**
 * Sets the chart data from the current data set.
 * If any data item does not have a fill color, a random color is assigned.
 * The data is then sorted in ascending order.
 * @async
 */
export const setChartData = async () => {
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

/**
 * Generates a random hex color code.
 * @returns {string} - A random hex color code.
 */
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}