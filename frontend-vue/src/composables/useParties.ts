import {computed, ref} from "vue";
import {selectedOption} from "@/composables/useDropdown";

export const allParties = ref<any[]>([]);
export const mediaData = ref<any[]>([]);

export async function fetchData() {
    try {
        const response = await fetch("http://localhost:3001/votes/affiliations");
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        allParties.value = data.map(party => ({
            ...party,
            imageUrl: party.imageUrl || 'https://www.dummyimage.com/200'
        }));
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    return allParties.value;
}

export async function fetchCandidateVotes() {
    try {
        const response = await fetch("http://localhost:3001/votes/candidates");
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        allCandidates.value = data;
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    return allCandidates.value;
}

export const allCandidates = ref<any[]>([]);
export const API_KEY = import.meta.env.VITE_API_KEY;
export const APP_NAME = import.meta.env.VITE_APP_NAME;

export async function fetchMedia() {
    const url = 'https://api.wikimedia.org/core/v1/wikipedia/nl/page/Lijst_van_politieke_partijen_in_Nederland/links/media?origin=*';
    try {
        const response = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${API_KEY}`,
                'Api-User-Agent': APP_NAME,
            },
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        const excludedTitles = ["1rightarrow blue.svg", "Commons-logo.svg", "Evolution of Dutch political parties since 1888.svg", "Flag of the Netherlands.svg", "Leden2018-1.png", "Ledental politieke partijen.png", "OOjs UI icon article-ltr.svg", "OOjs UI icon edit-ltr.svg", "Flag of the Netherlands.svg"];

        const partyNames = allParties.value.map(party => ({
            prefix: party.affiliationName.slice(0, 3).toLowerCase()
        }));
        const mediaFiles = data.files
            .filter((file: any) => !excludedTitles.includes(file.title))
            .filter((file: any) => partyNames.some(name =>
                file.title.toLowerCase().includes(name.prefix)
            ))
            .map((file: any) => ({
                title: file.title,
                url: file.preferred.url,
                descriptionUrl: file.file_description_url
            }));

        mediaData.value = allParties.value.map(party => {
            const partyName = party.affiliationName.toLowerCase();
            const images = mediaFiles.filter(file =>
                file.title.toLowerCase().includes(partyName.slice(0, 3)) ||
                file.title.toLowerCase().includes(partyName)
            );
            return {
                ...party,
                images: images
            };
        });
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    return mediaData.value;
}

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