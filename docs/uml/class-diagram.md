```mermaid
---
title: Elections Entity Class Diagram V1
---

classDiagram
    Constituency <|-- Municipality
    Municipality <|-- PollingStation
    
    Party <|-- Candidate
    
    Constituency <|-- ConstituencyVotes
    Party <|-- ConstituencyVotes
    Candidate <|-- ConstituencyVotes

    Municipality <|-- MunicipalityVotes
    Party <|-- MunicipalityVotes
    Candidate <|-- MunicipalityVotes

    PollingStation <|-- PollingStationVotes
    Party <|-- PollingStationVotes
    Candidate <|-- PollingStationVotes
    
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
    
    class Party {
        -int partyId
        -String partyName
    }
    class Candidate {
        -int candidateId
        -Party party
        -int memberId
        -String initials
        -String firstName
        -String LastName
        -String gender
        -String locality
    }
    
    class ConstituencyVotes {
        -int constituencyId
        -Party party
        -Candidate candidate
        -int totalVotes
    }
    class MunicipalityVotes {
        -int municipalityId
        -Party party
        -Candidate candidate
        -int totalVotes
    }
    class PollingStationVotes {
        -int pollingStationId
        -Party party
        -Candidate candidate
        -int totalVotes
    }
```

Note: Link-table between Candidate and Constituency has been removed due to the ConstituencyVotes class linking these two classes as well as keeping track of the votes.