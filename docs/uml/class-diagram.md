```mermaid
---
title: Elections Entity Class Diagram V1
---

classDiagram
    Constituency o-- Municipality
    Municipality o-- PollingStation
    
    Affiliation o-- Candidate
    
    Affiliation *-- CountryVotes
    Candidate *-- CountryVotes
    
    Constituency *-- ConstituencyVotes
    Affiliation *-- ConstituencyVotes
    Candidate *-- ConstituencyVotes

    Municipality *-- MunicipalityVotes
    Affiliation *-- MunicipalityVotes
    Candidate *-- MunicipalityVotes

    PollingStation *-- PollingStationVotes
    Affiliation *-- PollingStationVotes
    Candidate *-- PollingStationVotes
    
    class Constituency {
        -int constituencyId
        -String constituencyName
    }
    class Municipality {
        -int municipalityId
        -Constituency constituency
        -String municipalityName
    }
    class PollingStation {
        -int pollingStationId
        -Municipality municpality
        -String pollingStationName
    }
    
    class Affiliation {
        -int affiliationId
        -String affiliationName
    }
    class Candidate {
        -int candidateId
        -Affiliation affiliation
        -int memberId
        -String initials
        -String firstName
        -String LastName
        -String gender
        -String locality
    }
    
    class CountryVotes {
        -Party party
        -Candidate candidate
        -int totalVotes
    }
    
    class ConstituencyVotes {
        -Constituency constituency
        -Affiliation affiliation
        -Candidate candidate
        -int totalVotes
    }
    class MunicipalityVotes {
        -Municipalities municipality
        -Affiliations affiliation
        -Candidate candidate
        -int totalVotes
    }
    class PollingStationVotes {
        -PollingStation pollingStation
        -Affiliation affiliation
        -Candidate candidate
        -int totalVotes
    }
```

Note: Link-table between Candidate and Constituency has been removed due to the ConstituencyVotes class linking these two classes as well as keeping track of the votes.