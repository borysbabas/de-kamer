import {computed, ref} from "vue";
import {selectedOption} from "@/composables/useDropdown";
import {allCandidates, allParties} from "@/composables/useFetch";


export const visibleBlocks = ref(12);
export const isMoreVisible = ref(true);
export const isLessVisible = ref(false);

export const loadMore = () => {
    visibleBlocks.value += 25;
    if (visibleBlocks.value >= allParties.value.length) {
        isMoreVisible.value = false;
        isLessVisible.value = true;
    }
};

export const loadLess = () => {
    visibleBlocks.value = 12;
    isMoreVisible.value = true;
    isLessVisible.value = false;
    window.scrollTo({top: 0, behavior: 'smooth'});
};

export const getShortenedName = (name: string) => {
    return name.length > 25 ? name.slice(0, 25) + '...' : name;
};

export const sortedItems = computed(() => {
    switch (selectedOption.value) {
        case 'most_parties':
            return allParties.value.sort((a, b) => b.affiliationVotes - a.affiliationVotes);
        case 'least_parties':
            return allParties.value.sort((a, b) => a.affiliationVotes - b.affiliationVotes);
        case 'most_candidates':
            return allCandidates.value.sort((a, b) => b.votes - a.votes);
        case 'least_candidates':
            return allCandidates.value.sort((a, b) => a.votes - b.votes);
        default:
            return allParties.value.sort((a, b) => b.affiliationVotes - a.affiliationVotes);
    }
});