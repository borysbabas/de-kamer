```mermaid
---
title: Elections Class Diagram V1
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
        -int constituencyId
        -String municipalityName
    }
    class PollingStation {
        -int pollingStationId
        -int municpalityId
        -String pollingStationName
    }
    
    class Party {
        -int partyId
        -String partyName
    }
    class Candidate {
        -int candidateId
        -int partyId
        -int memberId
        -String initials
        -String firstName
        -String LastName
        -String gender
        -String locality
    }
    
    class ConstituencyVotes {
        -int constituencyId
        -int partyId
        -int candidateId
        -int totalVotes
    }
    class MunicipalityVotes {
        -int municipalityId
        -int partyId
        -int candidateId
        -int totalVotes
    }
    class PollingStationVotes {
        -int pollingStationId
        -int partyId
        -int candidateId
        -int totalVotes
    }
    
```

Note: Link-table between Candidate and Constituency has been removed due to the ConstituencyVotes class linking these two classes as well as keeping track of the votes.