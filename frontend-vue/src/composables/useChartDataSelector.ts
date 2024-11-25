import { ref } from "vue";

export const optionsList = ref([]);
export const selectedOption = ref(optionsList);

export const fetchOptions = async () => {
    optionsList.value = [
        {label: 'Type', options: ['Members', 'Votes']},
        {label: 'TEMP', options: ['Option 1', 'Option 2', 'Option 3']},
    ];
    return optionsList;
}
export const setSelectOption = (index: number, selectedValue: string) => {
    optionsList.value[index].selected = selectedValue;
}

export const chartData = ref([]);

export const fetchChartData = async (selectedOption: string) => {
    switch (selectedOption[0]) {
        case 'Members':
            return await getMembers();
        case 'Votes':
            return await getVotes();
        default:
            return [];
    }
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
