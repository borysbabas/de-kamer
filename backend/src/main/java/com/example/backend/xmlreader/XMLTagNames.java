package com.example.backend.xmlreader;

import lombok.experimental.UtilityClass;

@UtilityClass
public class XMLTagNames {
    public static final String ID = "Id";
    public static final String TYPE = "Type";
    public static final String COUNT = "Count";
    public static final String CONTEST = "Contest";

    public static final String CONTEST_ID = "ContestIdentifier";
    public static final String CONTEST_NAME = "ContestName";
    public static final String REGISTERED_NAME = "RegisteredName";
    public static final String IDENTICAL_LISTS = "stel gelijkluidende lijsten";
    public static final String AFFILIATION = "Affiliation";
    public static final String AFFILIATION_ID = "AffiliationIdentifier";

    public static final String CANDIDATE = "Candidate";
    public static final String CANDIDATE_ID = "CandidateIdentifier";
    public static final String NAME_LINE_INITIALS = "NameLine";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME_PREFIX = "NamePrefix";
    public static final String LAST_NAME = "LastName";
    public static final String GENDER = "Gender";
    public static final String LOCALITY_NAME = "LocalityName";

    public static final String SELECTION = "Selection";
    public static final String VALID_VOTES = "ValidVotes";
    public static final String TOTAL_VOTES = "TotalVotes";
    public static final String SHORT_CODE = "ShortCode";

    public enum FILE_TYPE {
        CANDIDATE_LIST,
        RESULT,
        VOTES_CONSTITUENCY,
        VOTES_MUNICIPALITY,
        TOTAL_COUNT,
        ELECTION_DEFINITION,
    }
}
