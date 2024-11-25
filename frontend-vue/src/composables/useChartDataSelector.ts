import { ref } from "vue";
import {setChartData} from "@/composables/useChartConfig";

export const optionsList = ref([]);
export const selectedOption = ref(optionsList);
export const curretDataSet = ref([]);




export const fetchOptions = async () => {
    optionsList.value = [
        {label: 'Type', options: ['Members', 'Votes']},
        {label: 'TEMP', options: ['Option 1', 'Option 2', 'Option 3']},
    ];
    return optionsList;
}
export const setSelectOption = async (index: number, selectedValue: string) => {
    optionsList.value[index].selected = selectedValue;
    console.log(optionsList.value);
    await fetchChartData();
}

export const fetchChartData = async () => {
    let data = [];
    switch (selectedOption.value[0].selected) {
        case 'Members':
            data = await getMembers();
            break;
        case 'Votes':
            data = await getVotes();
            break;
        default:
            data = [];
    }
    curretDataSet.value = data;
    setChartData();
}

async function getMembers() {
    return await fetch("http://localhost:3001/affiliation/members/count")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getVotes() {
    return await fetch("http://localhost:3001/votes/affiliations")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}


// temp posible options
// members, votes
