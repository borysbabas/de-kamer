import {ref} from "vue";
import type {PartyImages} from "@/types/PartyImages";
import type {Affiliation} from "@/types/Affiliation";

export const allParties = ref<Affiliation[]>([]);
export const mediaData = ref<any[]>([]);

/*
    * Fetches the party data from the API
    * @returns {Promise<Affiliation[]>} - The party data
    *
    * @throws {Error} - If the fetch operation fails
 */
export async function fetchParties() {
    try {
        const response = await fetch("http://localhost:3001/votes/affiliations");
        if (!response.ok) {
            console.error('Network response was not ok');
        }
        const data = await response.json();
        allParties.value = data.map((party: Affiliation) => ({
            ...party,
            imageUrl: party.imageUrl || 'https://www.dummyimage.com/200'
        }));
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    console.log(allParties.value);
    return allParties.value;
}

/*
    * Fetches the candidate data from the API
    * @returns {Promise<any[]>} - The candidate data
    *
    * @throws {Error} - If the fetch operation fails
 */
export async function fetchCandidateVotes() {
    try {
        const response = await fetch("http://localhost:3001/votes/candidates");
        if (!response.ok) {
            console.error('Network response was not ok');
        }
        allCandidates.value = await response.json();
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    console.log(allCandidates.value);
    return allCandidates.value;
}

export const allCandidates = ref<any[]>([]);
export const API_KEY = import.meta.env.VITE_API_KEY;
export const APP_NAME = import.meta.env.VITE_APP_NAME;

/*
    * Fetches the party images from the WikiMedia API
    * @returns {Promise<any[]>} - The party images
    *
    * Further information can be found in the WikiMedia API documentation:
    * https://api.wikimedia.org/wiki/Learn
    *
    * @throws {Error} - If the fetch operation fails
 */
export async function fetchPartyImages() {
    const url = 'https://api.wikimedia.org/core/v1/wikipedia/nl/page/Lijst_van_politieke_partijen_in_Nederland/links/media?origin=*';
    try {
        const response = await fetch(url, {
            headers: {
                'Authorization': `Bearer ${API_KEY}`,
                'Api-User-Agent': APP_NAME,
            },
        });
        if (!response.ok) {
            console.error(`HTTP error! status: ${response.status}`);
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
            .map((file: PartyImages) => ({
                title: file.title,
                url: file.preferred.url,
                descriptionUrl: file.file_description_url
            }));

        mediaData.value = allParties.value.map(party => {
            const partyName = party.affiliationName.toLowerCase();
            const images = mediaFiles.filter((file: PartyImages) =>
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