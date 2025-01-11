## Votes table

### API Documentation

When the votes table first gets loaded it calls the following endpoint in the constituency controller:
```
@GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE)
public List<ConstituencyDTO> getAllConstituencies() {
    List<Constituency> constituencies = constituencyService.findAllConstituencies();
    List<ConstituencyDTO> constituencyList = new ArrayList<>();

    for (Constituency constituency : constituencies) {
        // Exclude constituencies "alle" and "Bonaire", because they have little to no data elsewhere in the database
        if (!constituency.getConstituencyName().equals("alle") && !constituency.getConstituencyName().equals("Bonaire"))
            constituencyList.add(new ConstituencyDTO(
                    constituency.getConstituencyName(),
                    constituency.getConstituencyId()
            ));
    }
    return constituencyList;
}
```
This endpoint gets all constituencies from the database and puts them in a list of constituency DTOs, excluding 
constituencies with no votes data, and returns the list to the frontend. 

Whenever a constituency is selected, another endpoint is called in the municipality controller:
```
@GetMapping(path = ApiPaths.MUNICIPALITY_SERVICE_BY_CONSTITUENCY)
public List<MunicipalityDTO> getMunicipalitiesByConstituency(@PathVariable Long constituencyId) {
    Constituency constituency = constituencyService.findConstituencyById(constituencyId);
    List<MunicipalityDTO> municipalityList = new ArrayList<>();
    List<Municipality> municipalities = municipalityService.getMunicipalitiesByConstituency(constituency);

    for (Municipality municipality : municipalities) {
        municipalityList.add(new MunicipalityDTO(
                municipality.getMunicipalityName(),
                municipality.getMunicipalityId(),
                municipality.getConstituency().getConstituencyId()
        ));
    }

    return municipalityList;
}
```
This endpoint gets all municipalities within the selected constituency from the database and puts them in a list of 
municipality DTOs and returns the list to the frontend. <br>
Whenever a municipality is selected, another endpoint is called in the polling station controller, this endpoint is 
structurally the same as the municipality controller endpoint above, with municipality being replaced by polling 
station and constituency being replaced by municipality. <br> 
These endpoints all serve so the table in the frontend can be provided with selection lists for the user to choose 
which votes to display in the table. 

Whenever a different constituency is selected or the page of the table is changed and a constituency is selected, 
two endpoints are called in the votes table controller: 
```
@GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES_COUNT)
public int getConstituencyVotesCountByConstituencyId(@PathVariable("constituencyId") Long constituencyId) {
    Constituency constituency = constituencyService.findConstituencyById(constituencyId);
    return constituencyVotesService.getConstituencyVotesCountByConstituency(constituency);
}
```
This endpoint gets the number of candidates with at least 1 vote in the selected constituency from the database and 
returns this number to the frontend. In the frontend this number is used to control the pagination of the votes 
table. <br>
Whenever a municipality or polling station is selected, another endpoint is called in the votes table controller, 
these endpoints are structurally the same as the endpoint above, with constituency being replaced with municipality 
or polling station.
```
/**
 * Endpoint to send limited list of constituency votes of given constituency and page to frontend
 *
 * @param constituencyId Path variable, id of constituency to get the votes of
 * @param pagination Path variable, number of rows to get from database
 * @param page Path variable, selected table page of source element
 * @return List of limited number of constituency votes from given position in the database
 */
@GetMapping(path = ApiPaths.CONSTITUENCY_SERVICE_VOTES)
public List<ConstituencyVotesDTO> getConstituencyVotesByConstituencyIdByPage(@PathVariable("constituencyId") Long constituencyId, @PathVariable("pagination") Integer pagination, @PathVariable("page") Integer page) {
    Constituency constituency = constituencyService.findConstituencyById(constituencyId);
    List<ConstituencyVotes> constituencyVotes = constituencyVotesService.getConstituencyVotesByConstituencyByPage(constituency, pagination, page);
    List<ConstituencyVotesDTO> constituencyVotesList = new ArrayList<>();
    for (ConstituencyVotes constituencyVote : constituencyVotes) {
        constituencyVotesList.add(new ConstituencyVotesDTO(constituencyVote));
    }
    return constituencyVotesList;
}
```
This endpoint gets the votes of all candidates in the selected constituency from the database and puts them in a 
list of constituency votes DTOs to send to the frontend. These are then displayed in the votes table. When the table 
is first loaded it will call this endpoint with id 1, which is Groningen, so the table has default data when loading 
in. <br>
Whenever a municipality or polling station is selected, another endpoint is called in the votes table controller,
these endpoints are structurally the same as the endpoint above, with constituency being replaced with municipality
or polling station.