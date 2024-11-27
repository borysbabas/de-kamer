package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_ALL = "/all";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = "/members/count";

    public static final String VOTES = "/votes";
    public static final String AFFILIATION_SERVICE_VOTES = VOTES + "/affiliations";
    public static final String CANDIDATE_SERVICE_VOTES = VOTES + "/candidates";
    public static final String CANDIDATE_BY_AFFILIATION = "/members/{affiliationName}";

    public static final String AFFILIATION_SERVICE_GETBYNAME = "/get-by-name/{name}";
    public static final String MUNICIPALITY_SERVICE = "/municipality";
    public static final String MUNICIPALITY_SERVIVE_GETMUNICIPALITYVOTESBYCANDIDATEID="/get-municipality-votes-by-candidateId/{candidateId}";

}
