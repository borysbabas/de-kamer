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
    const data =  await fetch(import.meta.env.VITE_API_URL + "/candidates/affiliation/" + affilationName)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    return data.map((item: any) => item.firstName + ' ' + item.lastName);
}


const getPartyList = async () => {
    const data =  await fetch( import.meta.env.VITE_API_URL + "/affiliation/all")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    return data.map((item: any) => item.affiliationName);
}

export const fetchChartData = async () => {
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
                            await getVotesCandidateByAffiliation(selectedOption.value[3].selected).then((result) => {
                                data = result;
                            });

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
async function getVotesCandidateByAffiliation(affilationName: String) {
    return await fetch(import.meta.env.VITE_API_URL + "/votes/candidate/affiliation/" + affilationName)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getMembers() {
    return await fetch(import.meta.env.VITE_API_URL + "/affiliation/members/count")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getVotes() {
    return await fetch(import.meta.env.VITE_API_URL + "/votes/affiliations")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}


// temp posible options
// members, votes
