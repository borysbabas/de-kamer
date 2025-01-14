# Leaflet Controller Documentation

This document provides an overview of the `LeaflatController` class and its REST API endpoints. The controller handles requests related to leaflet data, specifically votes by municipality.

---

## **Overview**

### **Base Path**
`/api/leaflet-service`

### **Response Data Format**
All responses follow this structure:
```json
{
    "data": [
        {
            "name": "Location Name",
            "lat": "Latitude",
            "lon": "Longitude",
            "value": "Value",
            "id": "ID"
        }
    ]
}
```

---

## **Endpoints**

### **1. Get Votes by Municipality**

**Path**: `/api/municipality-service/get-municipalityvotes-by-affiliationid/{affiliationId}`  
**Method**: `GET`  
**Description**: Retrieves total votes by municipality for a specific affiliation.

**Parameters**:
- `affiliationId` (Path): The ID of the affiliation.

**Response**:
```json
{
    "data": [
        {
            "name": "Municipality Name",
            "lat": "Latitude",
            "lon": "Longitude",
            "value": 1234,
            "id": 1
        }
    ]
}
```

---

## **Internal Methods**

### **1. Fetch Coordinates for Municipalities**

**Method Name**: `fetchCords`  
**Description**: Fetches geographical coordinates (latitude and longitude) for municipalities from an external API.

**External API**:
- URL: `https://public.opendatasoft.com/api/explore/v2.1/catalog/datasets/georef-netherlands-gemeente/records`
- Parameters:
    - `select`: `geo_point_2d`, `gem_name`, `gem_code`
    - `limit`: 100 (API limit)
    - `offset`: Variable to paginate through results.

**Response Format** (from external API):
```json
{
    "results": [
        {
            "geo_point_2d": { "lat": 52.378, "lon": 4.9 },
            "gem_name": ["Municipality Name"],
            "gem_code": ["Municipality Code"]
        }
    ]
}
```

**Returns**:
A map of municipality codes to their corresponding coordinates and names:
```json
{
    "Municipality Code": {
        "lat": 52.378,
        "lon": 4.9,
        "name": "Municipality Name",
        "id": "Municipality Code"
    }
}
```

---

## **Annotations**

- `@RestController`: Indicates that this class is a REST controller.
- `@RequestMapping`: Defines the base path for the controller.
- `@Autowired`: Marks the constructor for dependency injection.

---

## **Dependencies**

The following services are utilized by the `LeaflatController`:
- `MunicipalityService`
- `MunicipalityVotesService`

---

This documentation provides details for developers working with or integrating the `LeaflatController` API. For further clarification, refer to the service and entity implementations.