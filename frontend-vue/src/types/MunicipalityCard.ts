export type MunicipalityCard = {
    title: string;
    subtitle?: string;
    municipalities: {
        value: string;
        subValue?: string;
        municipalityName: string;
    }[];
}