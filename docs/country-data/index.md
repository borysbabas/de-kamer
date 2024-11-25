# Getting country data 

I acquired the country data from the [Cbs' opendata](https://opendata.cbs.nl/portal.html?_la=nl&_catalog=CBS) website.

## Population data

The population data is from the [CBS](https://opendata.cbs.nl/portal.html?_la=nl&_catalog=CBS&tableId=03759ned&_theme=275) website. The data is for the population count on the first of January 2021 and the average population of 2021.

### Queries

fetch query for three specific municipalities:

```
https://opendata.cbs.nl/ODataFeed/odata/03759ned/UntypedDataSet?%24filter=((Geslacht%20eq%20%27T001038%27))%20and%20((BurgerlijkeStaat%20eq%20%27T001019%27))%20and%20((RegioS%20eq%20%27GM1979%27)%20or%20(RegioS%20eq%20%27GM0014%27)%20or%20(RegioS%20eq%20%27GM0432%27))%20and%20((Perioden%20eq%20%272021JJ00%27))&%24format=json
```

With the percentage codes removed for readability:

```
https://opendata.cbs.nl/ODataFeed/odata/03759ned/UntypedDataSet?%24filter=((Geslacht eq 'T001038')) and ((BurgerlijkeStaat eq 'T001019')) and ((RegioS eq 'GM1979') or (RegioS eq 'GM0014') or (RegioS eq 'GM0432')) and ((Perioden eq '2021JJ00'))&%24format=json
```

The query fetches the population data for the three municipalities with the codes GM1979, GM0014, and GM0432. The data is for the year 2021.

The municipality codes can be changed to the desired municipalities.

The metadata for correctly reading the response is: https://opendata.cbs.nl/ODataApi/odata/03759ned

### Response structure

A json response is returned with the following structure:

```ts
type PopulationData = {
  "odata.metadata": string;
  "value": {
      "ID": number;
      "Geslacht": string;
      "Leeftijd": string;
      "BurgerlijkeStaat": string;
      "RegioS": string;
      "Perioden": string;
      "BevolkingOp1Januari_1": string;
      "GemiddeldeBevolking_2": string
    }[]
}
```