import type {Municipality} from "@/types/Municipality";
import {onMounted, ref} from "vue";
import type {PopulationDataSet} from "@/types/PopulationDataSet";
import type {MetaDataCategory, CategoryValue} from "@/types/MetaDataCategory";
import {MetaDataName} from "@/types/MetaDataCategory";

const BACKEND = "http://localhost:3001";
const DATA_SET_ID = "03759ned";
const metaDataCategories = ref<MetaDataCategory[]>([]);
const fetchedCategories = ref<string[]>([]);
const selectedList = ref<Municipality[]>([]);
const ageKeyToValue = ref(new Map<string, {}>());

export function useMunicipalities() {
    const municipalityList = ref<Municipality[]>([]);
    const isLoading = ref(false)
    const error = ref(null)

    async function fetchMunicipalities() {
        isLoading.value = true
        //TODO: Change endpoint path
        fetch(BACKEND + "/municipality/all")
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

export function useAdditionalMunicipalitiesData(municipalities: Municipality[]) {
    const municipalityList = ref<Municipality[]>([]);
    const isLoading = ref(false);
    const error = ref(null);

    const pad = (num: number, size: number) => {
        return "0".repeat(size - num.toString().length) + num;
    }

    const buildFilter = (municipalityId: string) => {
        // (RegioS eq 'xxxx')
        return `(RegioS%20eq%20%27GM${municipalityId}%27)`;
    }

    const {metaDataCategories, ageKeyToValue} = useReadableCategories();


    async function fetchAdditionalInformation(municipality: Municipality) {
        isLoading.value = true;

        const requestUrl = `https://opendata.cbs.nl/ODataFeed/odata/${DATA_SET_ID}/UntypedDataSet?%24filter=((Geslacht%20eq%20%27T001038%27))%20and%20((BurgerlijkeStaat%20eq%20%27T001019%27))%20and%20(${
            buildFilter(pad(municipality.municipalityId, 4))
        })%20and%20((Perioden%20eq%20%272021JJ00%27))&%24format=json`;

        const response = await fetch(requestUrl, {
            method: "GET"
        });

        if (response.status !== 200) {
            error.value = "error handling";
            isLoading.value = false;
            return;
        }

        const populationDataSet: PopulationDataSet = await response.json();


        municipalityList.value.push({
            ...municipality,
            additionalData: populationDataSet.value.map(populationData => {
                return {
                    ID: populationData.ID,
                    Leeftijd: ageKeyToValue.value.get(populationData.Leeftijd),
                    BevolkingOp1Januari_1: populationData.BevolkingOp1Januari_1?.trim(),
                }
            }),
        });
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

export function useReadableCategories() {
    const isLoading = ref(false);

    getCategory(MetaDataName.Leeftijd);

    function getCategory(category: MetaDataName) {
        const requestUrl = `https://opendata.cbs.nl/ODataApi/odata/${DATA_SET_ID}/${category}`;
        if (!(category in MetaDataName)) {
            return {metaDataCategories, ageKeyToValue, isLoading};
        }

        if (fetchedCategories.value.includes(category)) {
            return {metaDataCategories, ageKeyToValue, isLoading};
        }

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
                ageKeyToValue.value.set(value.Key, value);
            })

            metaDataCategories.value.push({category: category, value: metadata.value});
            isLoading.value = false;
        }

        fetchedCategories.value.push(category);

        void fetchCategory();
    }

    return {metaDataCategories, ageKeyToValue, isLoading};
}

export async function getMunicipalityById(id: number) {
    const result = await fetch(BACKEND + `/municipality/id/${id}`, {
        method: "GET"
    });
    if (result.status !== 200) {
        return;
    }

    return await result.json();
}

export function addMunicipality(value: Municipality): void {
    selectedList.value.push(value);
}

export function removeMunicipality(value: Municipality): void {
    const index: number = selectedList.value.indexOf(value);
    if (index > -1) {
        selectedList.value.splice(index, 1);
    }
}

export function getSelectedMunicipalities() {
    return selectedList;
}