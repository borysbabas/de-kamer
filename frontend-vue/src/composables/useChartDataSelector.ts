import {ref} from "vue";
import {setChartData} from "@/composables/useChartConfig";
import {options} from "@/composables/useDropdown";

export const optionsList = ref([]);
export const selectedOption = ref({
    0: {selected: 'Votes'},
    1: {selected: 'Landelijk'},
    2: {selected: 'Partij'},
    3: {selected: 'VVD'},
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
            const partyOption = {label: 'Partij', options: await getPartyList(), selected: selectedOption.value[3]?.selected || 'VVD'};
            optionsList.value.push(partyOption);
            if (basisOption.selected === 'Gemeente' || basisOption.selected === 'Stembereau') {
                const candidateList = await getPartyCandidateList(partyOption.selected);
                const CandidateOption = {label: 'Candidaat', options:candidateList , selected: selectedOption.value[4]?.selected || candidateList[0]};
                optionsList.value.push(CandidateOption);
            }
        }
    }
}

export const setSelectOption = async (index: number, selectedValue: string) => {
    selectedOption.value[index].selected = selectedValue;
    await fetchOptions();
    await fetchChartData();
}
export const getPartyCandidateList = async (affilationName: String) => {
    console.log('getPartyCandidateList', affilationName);
    const data =  await fetch("http://localhost:3001/candidates/affiliation/" + affilationName)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    console.log(data, data.map((item: any) => item.firstName + ' ' + item.lastName));
    return data.map((item: any) => item.firstName + ' ' + item.lastName);
}


const getPartyList = async () => {
    const data =  await fetch("http://localhost:3001/affiliation/all")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    console.log(data);
    return data.map((item: any) => item.affiliationName);
}



export const fetchChartData = async () => {
    console.log('fetchChartData', selectedOption.value);
    let data;
    switch (selectedOption.value[0].selected) {
        case 'Members':
            data = await getMembers();
            break;
        case 'Votes':
            switch (selectedOption.value[1].selected) {
                case 'Landelijk':
                    switch (selectedOption.value[2].selected) {
                        case 'Partij':
                            data = await getVotes();
                            break;
                        case 'Candidaat':
                            data = [];
                            console.log(await fetch("http://localhost:3001/votes/candidate/affiliation/" + selectedOption.value[3]?.selected || 'VVD'));
                            break;
                        default:
                            data = [];
                    }
                    break;
                case 'Gemeente':
                    data = [0];
                    break;
                case 'Stembereau':
                    data = [0];
                    break;
                default:
                    data = [];
            }
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
