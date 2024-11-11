package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = "/members/count";

    public static final String VOTES = "/votes";
    public static final String AFFILIATION_SERVICE_VOTES = VOTES + "/affiliations";

    public static final String AFFILIATION_SERVICE_GETBYNAME = "/get-by-name/{name}";

}
