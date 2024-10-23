package com.example.backend.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiPaths {
    public static final String AUTH_SERVICE = "/auth";
    public static final String AUTH_SERVICE_LOGIN = AUTH_SERVICE + "/login";

    public static final String AFFILIATION_SERVICE = "/affiliation";
    public static final String AFFILIATION_SERVICE_MEMBER_COUNT = AFFILIATION_SERVICE + "/members/count";
}
