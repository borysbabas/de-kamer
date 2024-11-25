import type {PopulationData} from "@/types/PopulationData";

export type Municipality = {
    constituencyId: number;
    municipalityId: number;
    name: string;
    additionalData?: PopulationData[];
}