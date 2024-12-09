import type {Municipality} from "@/types/Municipality";
import {onMounted, ref} from "vue";
import type {PopulationDataSet} from "@/types/PopulationDataSet";
import type {MetaDataCategory, CategoryValue} from "@/types/MetaDataCategory";
import {MetaDataName} from "@/types/MetaDataCategory";
import type {Affiliation} from "@/types/Affiliation";

// define constants
const BACKEND = "http://localhost:3001";
const DATA_SET_ID = "03759ned";
const LEGAL_VOTE_AGE = 18;

const metaDataCategories = ref<MetaDataCategory[]>([]);
const fetchedCategories = ref<string[]>([]);
const selectedList = ref<Municipality[]>([]);
const ageKeyToValue = ref(new Map<string, string>());

/**
 * Composable function to manage municipalities data.
 * @returns {Object} - An object containing municipalityList, isLoading, and error.
 */
export function useMunicipalities() {
    const municipalityList = ref<Municipality[]>([]);
    const isLoading = ref(false)
    const error = ref(null)

    /**
     * Fetches the list of municipalities from the backend.
     */
    async function fetchMunicipalities() {
        isLoading.value = true
        fetch(BACKEND + "/municipalities")
            .then(response => {
                if (!response.ok) {
                    error.value = "error ..."
                }
                return response.json()
            })
            .then(data => {
                municipalityList.value = data
                isLoading.value = false
            })
            .catch(err => {
                isLoading.value = false
                error.value = err.message
            })

    }

    onMounted(() => {
        void fetchMunicipalities()
    })

    return {
        municipalityList,
        isLoading,
        error
    }
}

/**
 * Composable function to fetch additional data for municipalities. (currently only population data)
 * @param {Municipality[]} municipalities - The list of municipalities to fetch additional data for.
 * @returns {Object} - An object containing municipalityList, isLoading, and error.
 */
export function useAdditionalMunicipalitiesData(municipalities: Municipality[]) {
    const municipalityList = ref<Municipality[]>([]);
    const isLoading = ref(false);
    const error = ref(null);

    /**
     * Pads a number with leading zeros.
     * @param {number} num - The number to pad.
     * @param {number} size - The desired length of the output string.
     * @returns {string} - The padded number as a string.
     */
    const pad = (num: number, size: number) => {
        return "0".repeat(size - num.toString().length) + num;
    }

    /**
     * Builds a filter string for the municipality ID.
     * @param {string} municipalityId - The ID of the municipality.
     * @returns {string} - The filter string.
     */
    const buildFilter = (municipalityId: string) => {
        // (RegioS eq 'xxxx')
        return `(RegioS%20eq%20%27GM${municipalityId}%27)`;
    }

    const {ageKeyToValue} = useReadableCategories();

    /**
     * Fetches additional information for a given municipality.
     * @param {Municipality} municipality - The municipality to fetch additional information for.
     */
    async function fetchAdditionalInformation(municipality: Municipality) {
        const MUNICIPALITY_ID_LENGTH = 4;
        isLoading.value = true;
        municipality.additionalData = {};

        const requestUrl = `https://opendata.cbs.nl/ODataFeed/odata/${DATA_SET_ID}/UntypedDataSet?%24filter=((Geslacht%20eq%20%27T001038%27))%20and%20((BurgerlijkeStaat%20eq%20%27T001019%27))%20and%20(${
            buildFilter(pad(municipality.municipalityId, MUNICIPALITY_ID_LENGTH))
        })%20and%20((Perioden%20eq%20%272021JJ00%27))&%24format=json`;

        let response;
        try {
            response = await fetch(requestUrl, {
                method: "GET"
            });
        } catch (e) {
            error.value = "error fetching";
        }

        let eligibleVoters: number;

        if (response && response.status === 200) {
            error.value = null;
            const populationDataSet: PopulationDataSet = await response.json();

            let totalPopulation = 0;
            let totalAge = 0;
            let underage = 0;

            for (const populationData of populationDataSet.value) {
                const age = ageKeyToValue.value.get(populationData.Leeftijd);
                const amount = parseInt(populationData.BevolkingOp1Januari_1.trim());
                if (totalPopulation === 0) {
                    if (age === "Totaal") {
                        totalPopulation = amount;
                        continue;
                    }
                }
                const ageNumber = parseInt(age.replace(/ .*/gm, "").trim());
                if (ageNumber < LEGAL_VOTE_AGE) {
                    underage += amount;
                }
                totalAge += (amount * ageNumber);
            }

            eligibleVoters = totalPopulation - underage;

            municipality.additionalData = {
                averageAge: totalAge / totalPopulation,
                population: totalPopulation,
                eligibleVoters: eligibleVoters,
                turnoutPercentage: undefined,
            };

        }

        try {
            const affiliations: Affiliation[] = await getAffiliationVotesForMunicipality(municipality);
            municipality.affiliations = affiliations;
            municipality.topCandidate = await getTopCandidatesForMunicipality(municipality);

            if (eligibleVoters) {
                // Sum up the total votes for the municipality
                const totalVotes = affiliations.reduce((acc, affiliation) => acc + affiliation.totalVotes, 0);
                municipality.additionalData.turnoutPercentage = totalVotes / eligibleVoters * 100;
            }
        } catch (e) {
            error.value = "error fetching affiliations";
        }


        isLoading.value = false;

        municipalityList.value.push(municipality);
    }

    onMounted(() => {
        municipalities.forEach(municipality => {
            void fetchAdditionalInformation(municipality);
        });
    });

    return {
        municipalityList,
        isLoading,
        error
    }
}

/**
 * Composable function to create a variable that makes the results of an external API (opendata.cbs.nl) more readable.
 * @returns {Object} - An object containing metaDataCategories, ageKeyToValue, and isLoading.
 */
export function useReadableCategories() {
    const isLoading = ref(false);

    getCategory(MetaDataName.Leeftijd);

    /**
     * Fetches a category from the category list.
     * @param {MetaDataName} category - The category to fetch.
     * @returns {Object} - An object containing metaDataCategories, ageKeyToValue, and isLoading.
     */
    function getCategory(category: MetaDataName) {
        const requestUrl = `https://opendata.cbs.nl/ODataApi/odata/${DATA_SET_ID}/${category}`;
        if (!(category in MetaDataName)) {
            return {metaDataCategories, ageKeyToValue, isLoading};
        }

        if (fetchedCategories.value.includes(category)) {
            return {metaDataCategories, ageKeyToValue, isLoading};
        }

        /**
         * Fetches the category data from an external API.
         */
        async function fetchCategory() {
            isLoading.value = true;
            const response = await fetch(requestUrl, {
                method: "GET",
            });

            if (response.status !== 200) {
                return;
            }

            const metadata: { "odata.metadata": string, "value": CategoryValue[] } = await response.json();


            metadata.value.forEach(value => {
                ageKeyToValue.value.set(value.Key, value.Title);
            })

            metaDataCategories.value.push({category: category, value: metadata.value});
            isLoading.value = false;
        }

        fetchedCategories.value.push(category);

        void fetchCategory();
    }

    return {metaDataCategories, ageKeyToValue, isLoading};
}

/**
 * Fetches the affiliation votes for a given municipality.
 * @param {Municipality} municipality - The municipality to fetch affiliation votes for.
 * @returns {Promise<Affiliation[]>} - A promise that resolves to the list of affiliations.
 */
export async function getAffiliationVotesForMunicipality(municipality: Municipality) {
    /**
     * Fetches the affiliations from the backend.
     * @param {number} municipalityId - The ID of the municipality.
     * @returns {Promise<Affiliation[]>} - A promise that resolves to the list of affiliations.
     */
    async function fetchAffiliations(municipalityId: number) {
        const result = await fetch(BACKEND + `/votes/municipality/${municipalityId}/affiliations`, {
            method: "GET"
        });
        if (result.status !== 200) {
            return;
        }

        return await result.json();
    }

    return await fetchAffiliations(municipality.municipalityId)
}

/**
 * Fetches the top candidates for a given municipality.
 * @param {Municipality} municipality - The municipality to fetch top candidates for.
 * @returns {Promise<any>} - A promise that resolves to the list of top candidates.
 */
export async function getTopCandidatesForMunicipality(municipality: Municipality) {
    /**
     * Fetches the top candidates from the backend.
     * @param {number} municipalityId - The ID of the municipality.
     * @returns {Promise<any>} - A promise that resolves to the list of top candidates.
     */
    async function fetchTopCandidates(municipalityId: number) {
        const result = await fetch(BACKEND + `/votes/municipality/${municipalityId}/top`, {
            method: "GET"
        });
        if (result.status !== 200) {
            return;
        }

        return await result.json();
    }

    return await fetchTopCandidates(municipality.municipalityId)
}

/**
 * Adds a municipality to the selected list.
 * @param {Municipality} value - The municipality to add.
 */
export function addMunicipality(value: Municipality): void {
    selectedList.value.push(value);
}

/**
 * Removes a municipality from the selected list.
 * @param {Municipality} value - The municipality to remove.
 */
export function removeMunicipality(value: Municipality): void {
    const index: number = selectedList.value.indexOf(value);
    if (index > -1) {
        selectedList.value.splice(index, 1);
    }
}

/**
 * Gets the list of selected municipalities.
 * @returns {ref<Municipality[]>} - The list of selected municipalities.
 */
export function getSelectedMunicipalities(): ref<Municipality[]> {
    return selectedList;
}