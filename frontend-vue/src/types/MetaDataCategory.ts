export type MetaDataCategory = {
    category: MetaDataName;
    value: CategoryValue[];
}

export type CategoryValue = {
    Key: string;
    Title: string;
    Description: string;
    CategoryGroupID: number;
}

export enum MetaDataName {
    "TableInfos" = "TableInfos",
    "UntypedDataSet" = "UntypedDataSet",
    "TypedDataSet" = "TypedDataSet",
    "DataProperties" = "DataProperties",
    "CategoryGroups" = "CategoryGroups",
    "Geslacht" = "Geslacht",
    "Leeftijd" = "Leeftijd",
    "BurgerlijkeStaat" = "BurgerlijkeStaat",
    "RegioS" = "RegioS",
    "Perioden" = "Perioden",
}