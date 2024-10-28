package com.example.backend.xmlreader;

import lombok.experimental.UtilityClass;

@UtilityClass
public class XMLTagNames {
    public static final String ID = "Id";
    public static final String TYPE = "Type";
    public static final String COUNT = "Count";

    public static final String CONTEST_ID = "ContestIdentifier";
    public static final String IDENTICAL_LISTS = "stel gelijkluidende lijsten";
    public static final String AFFILIATION = "Affiliation";
    public static final String AFFILIATION_IDENTIFIER = "AffiliationIdentifier";
    public static final String CANDIDATE = "Candidate";
    public static final String CANDIDATE_IDENTIFIER = "CandidateIdentifier";

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
