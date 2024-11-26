package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = "/members/count";

    public static final String VOTES = "/votes";
    public static final String AFFILIATION_SERVICE_VOTES = VOTES + "/affiliations";
    public static final String CANDIDATE_SERVICE_VOTES = VOTES + "/candidates";
    public static final String MUNICIPALITIES_SERVICE_VOTES = VOTES + "/municipalities";
    public static final String MUNICIPALITY_SERVICE_VOTES = VOTES + "/municipality";

    public static final String AFFILIATION_SERVICE_GETBYNAME = "/get-by-name/{name}";

    private static final String MUNICIPALITY_SERVICE = "/municipality";
    public static final String MUNICIPALITY_ID = MUNICIPALITY_SERVICE + "/id/{id}";
    public static final String MUNICIPALITY_ALL = "/municipalities";

}
