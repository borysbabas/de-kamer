import {ref} from "vue";
import {setChartData} from "@/composables/useChartConfig";

export const optionsList = ref([]);
export const selectedOption = ref({
    0: {selected: 'Votes'},
    1: {selected: null},
    2: {selected: null},
    3: {selected: null}
});
export const curretDataSet = ref([]);

export const fetchOptions = async () => {
    console.log('fetchOptions', selectedOption.value[2]?.selected);
    const typeOption = {label: 'Type', options: ['Members', 'Votes'], selected: selectedOption.value[0]?.selected ?? 'Votes'};
    optionsList.value = [typeOption];

    if (typeOption.selected === 'Votes') {
        const basisOption = {label: 'Op basis van', options: ['Landelijk', 'Gemeente', 'Stembereau'], selected: selectedOption.value[1]?.selected || 'Landelijk'};
        const searchOption = {label: 'Zoeken op', options: ['Partij', 'Candidaat'], selected: selectedOption.value[2]?.selected || 'Partij'};
        optionsList.value.push(basisOption, searchOption);

        if (searchOption.selected === 'Candidaat') {
            const partyOption = {label: 'Partij', options: ['VVD', 'PVDA', 'CDA', 'D66'], selected: selectedOption.value[3]?.selected || 'VVD'};
            optionsList.value.push(partyOption);
        }
    }
}

export const setSelectOption = async (index: number, selectedValue: string) => {
    selectedOption.value[index].selected = selectedValue;
    console.log('setSelectOption', selectedOption.value);
    await fetchOptions();
    await fetchChartData();
    console.log(await getPartyList())
}


const getPartyList = async () => {
    return await fetch("http://localhost:3001/affiliations")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}



export const fetchChartData = async () => {
    let data;
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
    await setChartData();
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
