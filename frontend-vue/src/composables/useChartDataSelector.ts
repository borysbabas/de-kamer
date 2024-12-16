import {ref} from "vue";
import {setChartData} from "@/composables/useChartConfig";
import {options} from "@/composables/useDropdown";

export const optionsList = ref([]);
export const selectedOption = ref({
    0: {selected: 'Stemmen'},
    1: {selected: 'Landelijk'},
    2: {selected: 'Partij'},
    3: {selected: 'VVD'},
    4: {selected: 'Mark Rutte'}
});
export const curretDataSet = ref([]);

const partyList = ref([]);
const candidateList = ref([]);

export const fetchOptions = async () => {
    const typeOption = {label: 'Type', options: ['Kanditaten', 'Stemmen'], selected: selectedOption.value[0]?.selected ?? 'Stemmen'};
    optionsList.value = [typeOption];

    if (typeOption.selected === 'Stemmen') {
        const basisOption = {label: 'Op basis van', options: ['Landelijk', 'Gemeente', 'Kieskring'], selected: selectedOption.value[1]?.selected || 'Landelijk'};
        const searchOption = {label: 'Zoeken op', options: ['Partij', 'Kandidaat'], selected: selectedOption.value[2]?.selected || 'Partij'};
        optionsList.value.push(basisOption, searchOption);

        if (searchOption.selected === 'Kandidaat') {
            const partyOption = {label: 'Partij', options: await getPartyList(), selected: selectedOption.value[3]?.selected || 'VVD'};
            optionsList.value.push(partyOption);
            if (basisOption.selected === 'Gemeente' || basisOption.selected === 'Kieskring') {
                const candidateList = await getPartyCandidateList(partyOption.selected);
                const CandidateOption = {label: 'Kandidaat', options:candidateList , selected: selectedOption.value[4]?.selected || candidateList[0]};
                optionsList.value.push(CandidateOption);
            }
        } else if (basisOption.selected === 'Gemeente' || basisOption.selected === 'Kieskring') {
            const partyOption = {label: 'Partij', options: await getPartyList(), selected: selectedOption.value[3]?.selected || 'VVD'};
            optionsList.value.push(partyOption);
        }
    }
}

export const setSelectOption = async (index: number, selectedValue: string) => {
    selectedOption.value[index].selected = selectedValue;
    await fetchOptions();
    await fetchChartData();
}
export const getPartyCandidateList = async (affilationName: String) => {
    const data =  await fetch("http://localhost:3001/candidates/affiliation/" + affilationName)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    candidateList.value = data;
    return data.map((item: any) => item.firstName + ' ' + item.lastName);
}


const getPartyList = async () => {
    const data =  await fetch("http://localhost:3001/affiliation/all")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
    partyList.value = data;
    return data.map((item: any) => item.affiliationName);
}



export const fetchChartData = async () => {
    let data;
    switch (selectedOption.value[0].selected) {
        case 'Kanditaten':
            data = await getMembers();
            break;
        case 'Stemmen':
            switch (selectedOption.value[1].selected) {
                case 'Landelijk':
                    switch (selectedOption.value[2].selected) {
                        case 'Partij':
                            data = await getVotes();
                            break;
                        case 'Kandidaat':
                            data = [];
                            await getVotesByAffilation(selectedOption.value[3].selected).then((result) => {
                                data = result;
                            });
                            break;
                        default:
                            data = [];
                    }
                    break;
                case 'Gemeente':
                    switch (selectedOption.value[2].selected) {
                        case 'Partij':
                            data = []
                            await getMunicipalityVotesByAffilation(selectedOption.value[3].selected).then((result) => {
                                data = result;
                            });
                            break;
                        case 'Kandidaat':
                            data = []
                            await getMunicplaityVotesByCandidate(selectedOption.value[4].selected).then((result) => {
                                data = result;
                            });
                            break;
                        default:
                           // data = [];
                            break;
                    }
                    break;
                case 'Kieskring':
                    switch (selectedOption.value[2].selected) {
                        case 'Partij':
                            data = [];
                            await getConstituencyByAffilation(selectedOption.value[3].selected).then((result) => {
                                data = result;
                            });
                            break;
                        case 'Kandidaat':
                            data = [];
                            await getConstituencyByCandidate(selectedOption.value[4].selected).then((result) => {
                                data = result;
                            });
                            break;
                        default:
                            data = [];
                            break;
                    }
                    break;
                default:
                    // data = [];
                    break;
            }
            break;
        default:
            // data = [];
    }
    curretDataSet.value = data;
    await setChartData();
}

function getAffilationId(affilationName: string) {
    return partyList.value.find((item: any) => item.affiliationName === affilationName).affiliationId;
}


async function getVotesByAffilation(affilationName: string) {
    const affilationId = getAffilationId(affilationName);
    return await fetch("http://localhost:3001/chart/votes/" + affilationId)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getMembers() {
    return await fetch("http://localhost:3001/chart/members/count")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getVotes() {
    return await fetch("http://localhost:3001/chart/votes/affiliations")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}
async function  getMunicipalityList() {
    return await fetch("http://localhost:3001/municipality/all")
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getMunicplaityVotesByCandidate(candidateName: string) {
    const candidateId = candidateList.value.find((item: any) => item.firstName + ' ' + item.lastName === candidateName).candidateId;
    return await fetch("http://localhost:3001/chart/get-municipality-votes-by-candidateId/" + candidateId)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getMunicipalityVotesByAffilation(affilationName: string) {
    const affilationId = getAffilationId(affilationName);
    return await fetch("http://localhost:3001/chart/get-municipality-votes-by-affiliationId/" + affilationId)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getConstituencyByAffilation(affilationName: string) {
    const affilationId = getAffilationId(affilationName);
    return await fetch("http://localhost:3001/chart/votes/constituency/affiliation/" + affilationId)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}

async function getConstituencyByCandidate(candidateName: string) {
    const candidateId = candidateList.value.find((item: any) => item.firstName + ' ' + item.lastName === candidateName).candidateId;
    const affilationId = getAffilationId(selectedOption.value[3].selected);
    return await fetch("http://localhost:3001/chart/votes/constituency/affiliation/"+ affilationId + "/candidate/" + candidateId)
        .then((response) => response.json())
        .then((data) => {
            return data;
        });
}


// temp posible options
// members, votes
