import type {Municipality} from "@/components/types/Municipality";
import {onMounted, ref} from "vue";
const BACKEND = "http://localhost:3001";

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