# Chart Controller Documentation

This document provides an overview of the `ChartController` class and its REST API endpoints. The controller handles requests related to chart data, including votes and member counts.

---

## **Overview**

### **Base Path**
`/api/chart-service`

### **Response Data Format**
All responses follow this structure:
```json
{
    "data": [
        {
            "name": "Data-Set-Name",
            "data": [
                {
                    "x": "Name-value",
                    "y": int-value,
                    "id": int-value
                }
            ]
        }
    ]
}
```

---

## **Endpoints**

### **1. Get Votes by Affiliation ID**

**Path**: `/api/candidate-service/get-affiliation-votes/{affiliationId}`  
**Method**: `GET`  
**Description**: Retrieves votes by a specific affiliation ID.

**Parameters**:
- `affiliationId` (Path): The ID of the affiliation.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Candidate Name",
            "y": 1234,
            "id": 1
        }
    ]
}
```

---

### **2. Get All Member Counts**

**Path**: `/api/affiliation-service/member-count`  
**Method**: `GET`  
**Description**: Retrieves the count of members for all affiliations.

**Response**:
```json
{
    "name": "Leden",
    "data": [
        {
            "x": "Affiliation Name",
            "y": 5678,
            "id": 1
        }
    ]
}
```

---

### **3. Get Total Affiliation Votes**

**Path**: `/api/affiliation-service/votes`  
**Method**: `GET`  
**Description**: Retrieves the total votes for each affiliation.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Affiliation Name",
            "y": 12345,
            "id": 1
        }
    ]
}
```

---

### **4. Get Municipalities by Candidate ID**

**Path**: `/api/municipality-service/get-municipality-votes-by-candidate-id/{candidateId}`  
**Method**: `GET`  
**Description**: Retrieves the top 5 municipalities for a specific candidate.

**Parameters**:
- `candidateId` (Path): The ID of the candidate.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Municipality Name",
            "y": 123,
            "id": 1
        }
    ]
}
```

---

### **5. Get Municipalities by Affiliation ID**

**Path**: `/api/municipality-service/get-municipality-votes-by-affiliation-id/{affiliationId}`  
**Method**: `GET`  
**Description**: Retrieves votes in municipalities for a specific affiliation.

**Parameters**:
- `affiliationId` (Path): The ID of the affiliation.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Municipality Name",
            "y": 456,
            "id": 1
        }
    ]
}
```

---

### **6. Get Constituency Votes by Affiliation ID**

**Path**: `/api/constituency-votes/by-affiliation/{affiliationId}`  
**Method**: `GET`  
**Description**: Retrieves votes in constituencies for a specific affiliation.

**Parameters**:
- `affiliationId` (Path): The ID of the affiliation.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Constituency Name",
            "y": 789,
            "id": 1
        }
    ]
}
```

---

### **7. Get Constituency Votes by Affiliation and Candidate ID**

**Path**: `/api/constituency-votes/by-candidate/{affiliationId}/{candidateId}`  
**Method**: `GET`  
**Description**: Retrieves votes in constituencies for a specific affiliation and candidate.

**Parameters**:
- `affiliationId` (Path): The ID of the affiliation.
- `candidateId` (Path): The ID of the candidate.

**Response**:
```json
{
    "name": "Stemmen",
    "data": [
        {
            "x": "Constituency Name",
            "y": 101,
            "id": 1
        }
    ]
}
```

---

## **Dependencies**

The following services are utilized by the `ChartController`:
- `CandidateService`
- `CountryVotesService`
- `AffiliationService`
- `AffiliationVotesService`
- `MunicipalityVotesService`
- `ConstituencyVotesService`

---

## **Annotations**

- `@RestController`: Indicates that this class is a REST controller.
- `@RequestMapping`: Defines the base path for the controller.
- `@Autowired`: Marks the constructor for dependency injection.

--- 

This documentation provides details for developers working with or integrating the `ChartController` API. For further clarification, refer to the service and entity implementations.