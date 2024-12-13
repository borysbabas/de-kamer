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
        const response = await fetch(import.meta.env.VITE_API_URL + "/votes/affiliations");
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
    return allParties.value;
}

/*
    * Fetches the party details from the API
    * @param {string} partyId - The party ID
    * @returns {Promise<any>} - The party details
    *
    * @throws {Error} - If the fetch operation fails
 */
export async function fetchPartyDetails(partyId: string) {
    const response = await fetch(import.meta.env.VITE_API_URL + `/votes/affiliations/${partyId}`);
    return await response.json();
}

/*
    * Fetches the candidate data from the API
    * @returns {Promise<any[]>} - The candidate data
    *
    * @throws {Error} - If the fetch operation fails
 */
export async function fetchCandidateVotes() {
    try {
        const response = await fetch(import.meta.env.VITE_API_URL + "/votes/candidates");
        if (!response.ok) {
            console.error('Network response was not ok');
        }
        allCandidates.value = await response.json();
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
    return allCandidates.value;
}

export const allCandidates = ref<any[]>([]);
