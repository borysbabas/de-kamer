import type {Affiliation} from "@/types/Affiliation";
import type {Candidate} from "@/types/Candidate";

export type Municipality = {
    constituencyId: number;
    municipalityId: number;
    name: string;
    additionalData?: {
        averageAge: number;
        population: number;
        eligibleVoters: number;
        turnoutPercentage: number;
    };
    affiliations?: Affiliation[];
    topCandidate?: Candidate;
}