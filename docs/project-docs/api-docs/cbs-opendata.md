# CBS open data API 

I acquired the country data from the [Cbs' opendata](https://opendata.cbs.nl/portal.html?_la=nl&_catalog=CBS) website.

## Population data

The population data is from the [CBS](https://opendata.cbs.nl/portal.html?_la=nl&_catalog=CBS&tableId=03759ned&_theme=275) website. The data is for the population count on the first of January 2021 and the average population of 2021.

The metadata for correctly reading the response is: https://opendata.cbs.nl/ODataApi/odata/03759ned

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

---

fetch query for all municipalities:

```
https://opendata.cbs.nl/ODataFeed/odata/03759ned/UntypedDataSet?%24filter=((Perioden%20eq%20%272021JJ00%27))%20and%20((substringof(%27PV%27%2CRegioS))%20or%20(substringof(%27GM%27%2CRegioS))%20or%20(RegioS%20eq%20%27NL01%20%20%27))%20and%20((Geslacht%20eq%20%27T001038%27))%20and%20((BurgerlijkeStaat%20eq%20%27T001019%27))&%24format=json
```

Because the response is too large another couple of queries need to be send before all the data is fetched. The following query fetches the next page of data:

```

https://opendata.cbs.nl/ODataFeed/odata/03759ned/UntypedDataSet?$filter=%28%28Perioden%20eq%20%272021JJ00%27%29%29%20and%20%28%28substringof%28%27PV%27%2CRegioS%29%29%20or%20%28substringof%28%27GM%27%2CRegioS%29%29%20or%20%28RegioS%20eq%20%27NL01%20%20%27%29%29%20and%20%28%28Geslacht%20eq%20%27T001038%27%29%29%20and%20%28%28BurgerlijkeStaat%20eq%20%27T001019%27%29%29&$format=json&$skip=10000
```

The difference between the two queries is the `$skip` parameter. The value of the `$skip` parameter is the number of entries that have already been fetched. The value of the `$skip` parameter can be increased to fetch more data.

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