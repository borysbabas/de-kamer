```mermaid
---
title: Elections Entity Class Diagram V1
---

classDiagram
    Constituency <|-- Municipality
    Municipality <|-- PollingStation
    
    Parties <|-- Candidates
    
    Parties <|-- CountryVotes
    Candidates <|-- CountryVotes
    
    Constituency <|-- ConstituencyVotes
    Parties <|-- ConstituencyVotes
    Candidates <|-- ConstituencyVotes

    Municipality <|-- MunicipalityVotes
    Parties <|-- MunicipalityVotes
    Candidates <|-- MunicipalityVotes

    PollingStation <|-- PollingStationVotes
    Parties <|-- PollingStationVotes
    Candidates <|-- PollingStationVotes
    
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
    
    class Parties {
        -int partyId
        -String partyName
    }
    class Candidates {
        -int candidateId
        -Party party
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
        -Party party
        -Candidate candidate
        -int totalVotes
    }
    class MunicipalityVotes {
        -Municipality municipality
        -Party party
        -Candidate candidate
        -int totalVotes
    }
    class PollingStationVotes {
        -PollingStation pollingStation
        -Party party
        -Candidate candidate
        -int totalVotes
    }
```

Note: Link-table between Candidate and Constituency has been removed due to the ConstituencyVotes class linking these two classes as well as keeping track of the votes.