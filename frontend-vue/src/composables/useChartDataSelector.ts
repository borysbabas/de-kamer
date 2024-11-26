import {computed, ref} from "vue";
import {setChartData} from "@/composables/useChartConfig";
import {options} from "@/composables/useDropdown";

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
    optionsList.value = [
        {label: 'Type', options: ['Members', 'Votes'], selected: selectedOption.value[0]?.selected ?? 'Votes'},
    ];
    if (optionsList.value[0].selected === 'Votes') {
        optionsList.value.push({label: 'Op basis van', options: ['Landelijk', 'Gemeente', 'Stembereau'], selected: selectedOption.value[1]?.selected || 'Landelijk'});
        optionsList.value.push({label: 'Zoeken op', options: ['Partij', 'Candidaat'], selected: selectedOption.value[2]?.selected || 'Partij'});
        if (selectedOption.value[2]?.selected === 'Candidaat') {
            optionsList.value.push({label: 'Partij', options: ['VVD', 'PVDA', 'CDA', 'D66'], selected: selectedOption.value[3]?.selected || 'VVD'});
        } else {
            //
        }
    }
}



export const setSelectOption = async (index: number, selectedValue: string) => {
    selectedOption.value[index].selected = selectedValue;
    console.log('setSelectOption', selectedOption.value);
    await fetchOptions();
    await fetchChartData();
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
