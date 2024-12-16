package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_ALL = "/all";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = "/members/count";

    public static final String CONSTITUENCY_SERVICE = "/constituencies";

    public static final String AUTHENTICATION_SERVICE="/authentication";

    public static final String CANDIDATES_SERVICE="/candidates";

    public static final String AUTHENTICATION_SERVICE_LOGIN= AUTHENTICATION_SERVICE + "/login";
    public static final String AUTHENTICATION_SERVICE_REGISTER = AUTHENTICATION_SERVICE + "/register";
    public static final String AUTHENTICATION_SERVICE_DELETE = AUTHENTICATION_SERVICE + "/{id}";


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

    public static final String AFFILIATION_SERVICE_GETBYNAME = "/get-by-name/{name}";

    private static final String MUNICIPALITY_SERVICE = "/municipality";
    public static final String MUNICIPALITY_ID = MUNICIPALITY_SERVICE + "/id/{id}";
    public static final String MUNICIPALITY_ALL = "/municipalities";
    public static final String MUNICIPALITY_SERVIVE_GETMUNICIPALITYVOTESBYCANDIDATEID = "/get-municipality-votes-by-candidateId/{candidateId}";
}
