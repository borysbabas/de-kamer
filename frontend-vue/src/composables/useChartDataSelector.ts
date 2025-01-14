import { ref } from "vue";
import { setChartData } from "@/composables/useChartConfig";

/**
 * A reactive reference to store the list of options.
 * @type {import('vue').Ref<Array<Object>>}
 */
export const optionsList = ref([]);

/**
 * A reactive reference to store the selected options.
 * @type {import('vue').Ref<Object>}
 */
export const selectedOption = ref({
    0: { selected: 'Stemmen' },
    1: { selected: 'Landelijk' },
    2: { selected: 'Partij' },
    3: { selected: 'VVD' },
    4: { selected: 'Mark Rutte' }
});

/**
 * A reactive reference to store the current data set.
 * @type {import('vue').Ref<Array<Object>>}
 */
export const curretDataSet = ref([]);

/**
 * A reactive reference to store the list of parties.
 * @type {import('vue').Ref<Array<Object>>}
 */
const partyList = ref([]);

/**
 * A reactive reference to store the list of candidates.
 * @type {import('vue').Ref<Array<Object>>}
 */
const candidateList = ref([]);

/**
 * Fetches and updates the options list based on the selected options.
 * @returns {Promise<void>}
 */
export const fetchOptions = async () => {
    const typeOption = { label: 'Type', options: ['Kanditaten', 'Stemmen'], selected: selectedOption.value[0]?.selected ?? 'Stemmen' };
    optionsList.value = [typeOption];

    if (typeOption.selected === 'Stemmen') {
        const basisOption = { label: 'Op basis van', options: ['Landelijk', 'Gemeente', 'Kieskring'], selected: selectedOption.value[1]?.selected || 'Landelijk' };
        const searchOption = { label: 'Zoeken op', options: ['Partij', 'Kandidaat'], selected: selectedOption.value[2]?.selected || 'Partij' };
        optionsList.value.push(basisOption, searchOption);

        if (searchOption.selected === 'Kandidaat') {
            const partyOption = { label: 'Partij', options: await getPartyList(), selected: selectedOption.value[3]?.selected || 'VVD' };
            optionsList.value.push(partyOption);
            if (['Gemeente', 'Kieskring'].includes(basisOption.selected)) {
                const candidateList = await getPartyCandidateList(partyOption.selected);
                const CandidateOption = { label: 'Kandidaat', options: candidateList, selected: selectedOption.value[4]?.selected || candidateList[0] };
                optionsList.value.push(CandidateOption);
            }
        } else if (['Gemeente', 'Kieskring'].includes(basisOption.selected)) {
            const partyOption = { label: 'Partij', options: await getPartyList(), selected: selectedOption.value[3]?.selected || 'VVD' };
            optionsList.value.push(partyOption);
        }
    }
}

/**
 * Sets the selected option and updates the options and chart data.
 * @param {number} index - The index of the option to set.
 * @param {string} selectedValue - The selected value.
 * @returns {Promise<void>}
 */
export const setSelectOption = async (index: number, selectedValue: string) => {
    selectedOption.value[index].selected = selectedValue;
    await fetchOptions();
    await fetchChartData();
}

/**
 * Fetches the list of candidates for a given party affiliation.
 * @param {string} affilationName - The name of the party affiliation.
 * @returns {Promise<Array<string>>} - The list of candidate names.
 */
export const getPartyCandidateList = async (affilationName: string) => {
    const data = await fetch(`${import.meta.env.VITE_API_URL}/candidates/affiliation/${affilationName}`).then(res => res.json());
    candidateList.value = data;
    return data.map((item: any) => `${item.firstName} ${item.lastName}`);
}

/**
 * Fetches the list of all parties.
 * @returns {Promise<Array<string>>} - The list of party names.
 */
const getPartyList = async () => {
    const data = await fetch(`${import.meta.env.VITE_API_URL}/affiliation/all`).then(res => res.json());
    partyList.value = data;
    return data.map((item: any) => item.affiliationName);
}

/**
 * Fetches and updates the chart data based on the selected options.
 * @returns {Promise<void>}
 */
export const fetchChartData = async () => {
    let data;
    const type = selectedOption.value[0].selected;
    const basis = selectedOption.value[1].selected;
    const search = selectedOption.value[2].selected;
    const party = selectedOption.value[3].selected;
    const candidate = selectedOption.value[4].selected;

    if (type === 'Kanditaten') {
        data = await getMembers();
    } else if (type === 'Stemmen') {
        if (basis === 'Landelijk') {
            data = search === 'Partij' ? await getVotes() : await getVotesByAffilation(party);
        } else if (basis === 'Gemeente') {
            data = search === 'Partij' ? await getMunicipalityVotesByAffilation(party) : await getMunicplaityVotesByCandidate(candidate);
        } else if (basis === 'Kieskring') {
            data = search === 'Partij' ? await getConstituencyByAffilation(party) : await getConstituencyByCandidate(candidate);
        }
    }
    curretDataSet.value = data;
    await setChartData();
}

/**
 * Finds the affiliation ID for a given party affiliation name.
 * @param {string} affilationName - The name of the party affiliation.
 * @returns {number|undefined} - The affiliation ID or undefined if not found.
 */
const getAffilationId = (affilationName: string) => partyList.value.find((item: any) => item.affiliationName === affilationName)?.affiliationId;

/**
 * Fetches the votes for a given party affiliation.
 * @param {string} affilationName - The name of the party affiliation.
 * @returns {Promise<Array<Object>>} - The list of votes.
 */
const getVotesByAffilation = async (affilationName: string) => {
    const affilationId = getAffilationId(affilationName);
    return await fetch(`${import.meta.env.VITE_API_URL}/chart/votes/${affilationId}`).then(res => res.json());
}

/**
 * Fetches the list of members.
 * @returns {Promise<Array<Object>>} - The list of members.
 */
const getMembers = async () => await fetch(`${import.meta.env.VITE_API_URL}/chart/members/count`).then(res => res.json());

/**
 * Fetches the votes for all affiliations.
 * @returns {Promise<Array<Object>>} - The list of votes.
 */
const getVotes = async () => await fetch(`${import.meta.env.VITE_API_URL}/chart/votes/affiliations`).then(res => res.json());

/**
 * Fetches the municipality votes for a given candidate.
 * @param {string} candidateName - The name of the candidate.
 * @returns {Promise<Array<Object>>} - The list of municipality votes.
 */
const getMunicplaityVotesByCandidate = async (candidateName: string) => {
    const candidateId = findCandidateId(candidateName);
    if (!candidateId) return [];
    return await fetch(`${import.meta.env.VITE_API_URL}/chart/get-municipality-votes-by-candidateId/${candidateId}`).then(res => res.json());
}

/**
 * Fetches the municipality votes for a given party affiliation.
 * @param {string} affilationName - The name of the party affiliation.
 * @returns {Promise<Array<Object>>} - The list of municipality votes.
 */
const getMunicipalityVotesByAffilation = async (affilationName: string) => {
    const affilationId = getAffilationId(affilationName);
    return await fetch(`${import.meta.env.VITE_API_URL}/chart/get-municipality-votes-by-affiliationId/${affilationId}`).then(res => res.json());
}

/**
 * Fetches the constituency votes for a given party affiliation.
 * @param {string} affilationName - The name of the party affiliation.
 * @returns {Promise<Array<Object>>} - The list of constituency votes.
 */
const getConstituencyByAffilation = async (affilationName: string) => {
    const affilationId = getAffilationId(affilationName);
    return await fetch(`${import.meta.env.VITE_API_URL}/chart/votes/constituency/affiliation/${affilationId}`).then(res => res.json());
}

/**
 * Fetches the constituency votes for a given candidate.
 * @param {string} candidateName - The name of the candidate.
 * @returns {Promise<Array<Object>>} - The list of constituency votes.
 */
const getConstituencyByCandidate = async (candidateName: string) => {
    const candidateId = findCandidateId(candidateName);
    if (!candidateId) return [];
    const affilationId = getAffilationId(selectedOption.value[3].selected);
    return await fetch(`${import.meta.env.VITE_API_URL}/chart/votes/constituency/affiliation/${affilationId}/candidate/${candidateId}`).then(res => res.json());
}

/**
 * Finds the candidate ID for a given candidate name.
 * @param {string} candidateName - The name of the candidate.
 * @returns {number|undefined} - The candidate ID or undefined if not found.
 */
const findCandidateId = (candidateName: string) => candidateList.value.find((item: any) => `${item.firstName} ${item.lastName}` === candidateName)?.candidateId;