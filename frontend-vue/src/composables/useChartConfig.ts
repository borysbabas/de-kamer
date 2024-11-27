import {ref } from "vue";
import {curretDataSet} from "@/composables/useChartDataSelector";

let curretId = 0;

export let chartData = ref([]);

export const sortData = (order: string) => {
    const sortedData = chartData.value[0].data.sort((a: any, b: any) => order === 'asc' ? b.y - a.y : a.y - b.y);
    return [{ name: chartData.value[0].name, data: sortedData }];
}

export const setChartData = async () => {
    if (curretDataSet.value.length === 0) {
        return;
    }
    chartData.value = normalizeData(formatChartData(curretDataSet.value));
    sortData('asc');
}


export const formatChartData = (data: any) => {
        curretId = 0;
        const type = findType(data[0]);
        const formatData = data.map((item: any) => {
            let value = null
            let itemName = null;
            switch (type) {
                case "Members":
                value = item.memberCount;
                itemName = item.affiliationName;
                break;
                case "Member Votes":
                value = item.votes;
                itemName = item.name;
                break;
                case "Votes":
                value = item.affiliationVotes;
                itemName = item.affiliationName;
                break
                default:
                value = item.affiliationVotes;
                itemName = item.affiliationName;
                break;
            }
            return constructObject(itemName, value, item?.color);
        });
        return type ? { name: type, formatData } : null;
}
export const normalizeData = (data: any) => {
    return [
        {
            name: data.name ? data.name : 'Count',
            data: data.formatData.map((item: any) => {
                const color = item.color ? item.color : getRandomColor();
                return {
                    x: item.itemName,
                    y: item.itemValue,
                    fillColor: color,
                    id: item.id,
                    state: "show",
                    hiddenValue: { y: item.itemValue, x: item.itemName, fillColor: color}
                }
            })
        }
    ];
}











function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
function findType(item: any) {
    if (item?.affiliationName) {
        if (item.hasOwnProperty('votes')) return "Member Votes";
        if (item.hasOwnProperty('memberCount')) return "Members";
        if (item.hasOwnProperty('affiliationVotes')) return "Votes";
    }
    return null;
}

function constructObject(itemName: string, itemValue: number, color?: string) {
    return {
        id: curretId++,
        itemName,
        itemValue,
        color: color || getRandomColor()
    }
}