import type {Municipality} from "@/components/types/Municipality";
import {onMounted, ref} from "vue";
const BACKEND = "http://localhost:3001";

const selectedList = ref<Municipality[]>([]);

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