import type {PopulationData} from "@/types/PopulationData";
import type {Affiliation} from "@/types/Affiliation";
import type {Candidate} from "@/types/Candidate";

export type Municipality = {
    constituencyId: number;
    municipalityId: number;
    name: string;
    additionalData?: PopulationData[];
    affiliations?: Affiliation[];
    topCandidate?: Candidate;
}