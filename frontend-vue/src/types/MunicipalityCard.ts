export type MunicipalityCard = {
    title: string;
    subtitle?: string;
    municipalities: {
        value: string;
        valueSuffix?: string;
        subValue?: string;
        subValueSuffix?: string;
        municipalityName: string;
    }[];
}