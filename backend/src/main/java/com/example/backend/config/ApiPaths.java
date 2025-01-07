package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_ALL = "/all";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = "/members/count";
    public static final String CHART_SERVICE = "/chart";
    public static final String LEAFLET_SERVICE = "/leaflet";

    public static final String CANDIDATE_SERVICE_GET_AFFILIATION_VOTES = "/votes/{affiliationId}";

    public static final String CONSTITUENCY_SERVICE = "/constituencies";

    public static final String AUTHENTICATION_SERVICE="/authentication";

    public static final String CANDIDATES_SERVICE="/candidates";

    public static final String AUTHENTICATION_SERVICE_LOGIN= AUTHENTICATION_SERVICE + "/login";
    public static final String AUTHENTICATION_SERVICE_REGISTER = AUTHENTICATION_SERVICE + "/register";
    public static final String AUTHENTICATION_SERVICE_DELETE = AUTHENTICATION_SERVICE + "/{id}";

    public static final String ACCOUNTDETAILS_SERVICE="/accountdetails";
    public static final String ACCOUNTDETAILS_SERVICE_GET_USERBYID=ACCOUNTDETAILS_SERVICE+"/{id}";
    public static final String ACCOUNTDETAILS_SERVICE_DELETE_USERBYID=ACCOUNTDETAILS_SERVICE+"/delete/user/{id}";
    public static final String ACCOUNTDETAILS_SERVICE_UPDATE= ACCOUNTDETAILS_SERVICE+"/update/{id}";



    public static final String VOTES = "/votes";
    public static final String AFFILIATION_SERVICE_VOTES = VOTES + "/affiliations";
    public static final String CONSTITUENCY_SERVICE_VOTES = VOTES + "/constituencies/{constituencyId}/{pagination}/{page}";
    public static final String CONSTITUENCY_SERVICE_VOTES_COUNT = VOTES + "/constituencies/{constituencyId}/count";
    public static final String CANDIDATE_SERVICE_VOTES = VOTES + "/candidates";
    public static final String CANDIDATE_VOTES_BY_AFFILIATION_NAME = VOTES + "/candidate/affiliation/{affiliationName}";
    public static final String MUNICIPALITIES_SERVICE_VOTES = VOTES + "/municipalities";
    public static final String MUNICIPALITY_SERVICE_VOTES = VOTES + "/municipality";
    public static final String AFFILIATION_SERVICE_GETBYID = VOTES + "/affiliations/{id}";
    public static final String MUNICIPALITY_GET_AFFILIATION_VOTES = VOTES + "/municipality/{municipalityId}/affiliations";
    public static final String MUNCIIPALITY_GET_TOP_CANDIDATE = VOTES + "/municipality/{municipalityId}/top";
    public static final String MUNICIPALITY_SERVICE_VOTES_COUNT = VOTES + "/municipalities/{municipalityId}/count";
    public static final String MUNICIPALITY_SERVICE_VOTES_BY_MUNICIPALITY = VOTES + "/municipalities/{municipalityId}/{pagination}/{page}";
    public static final String POLLING_STATION_SERVICE_VOTES_COUNT = VOTES + "/polling-stations/{pollingStationId}/count";
    public static final String POLLING_STATION_SERVICE_VOTES = VOTES + "/polling-stations/{pollingStationId}/{pagination}/{page}";

    public static final String AFFILIATION_SERVICE_GETBYNAME = "/get-by-name/{name}";

    private static final String MUNICIPALITY_SERVICE = "/municipality";
    public static final String MUNICIPALITY_ID = MUNICIPALITY_SERVICE + "/id/{id}";
    public static final String MUNICIPALITY_ALL = "/municipalities";
    public static final String MUNICIPALITY_SERVIVE_GETMUNICIPALITYVOTESBYCANDIDATEID = "/get-municipality-votes-by-candidateId/{candidateId}";
    public static final String MUNICIPALITY_SERVICE_GET_MUNICIPALITYVOTES_BY_AFFILIATIONID = "/get-municipality-votes-by-affiliationId/{affiliationId}";
    public static final String CONSTITUENCY_VOTES_BY_AFFILIATION = VOTES + "/constituency/affiliation/{affiliationId}";
    public static final String CONSTITUENCY_VOTES_BY_CANDIDATE = VOTES + "/constituency/affiliation/{affiliationId}/candidate/{candidateId}";
    public static final String MUNICIPALITY_SERVICE_BY_CONSTITUENCY = MUNICIPALITY_ALL + "/constituency/{constituencyId}";

    public static final String POLLING_STATION_SERVICE = "/polling-station";
    public static final String POLLING_STATION_BY_MUNICIPALITY = POLLING_STATION_SERVICE + "/municipality/{municipalityId}";

    public static final String POST_SERVICE = "/posts";
    public static final String ADD_POST = POST_SERVICE + "/add";
    public static final String FIND_POST_BY_ID = POST_SERVICE + "/{postId}";
    public static final String POST_SERVICE_COUNT = POST_SERVICE + "/count";
    public static final String POST_SERVICE_RECENT = POST_SERVICE + "/recent";
}
