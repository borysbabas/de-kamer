# DeKamer

![DeKamer homepage](docs/project-docs/technical-documentation/images/homepage.png)

A site that explains the results of the Dutch House of Representatives (Tweede Kamer) election to young adults, the
group that votes least. The homepage shows the parties and candidates with the most (or fewest) votes; from there you
dig into parties, candidates, municipalities and polling stations with charts, tables and a map, and talk about it on
the forum. Framed as an EU-funded project to strengthen trust in democracy. A Software Engineering project from
semester 3 at the HvA (2024–2025).

## Stack

| | |
|---|---|
| Frontend | Vue 3, TypeScript, Vue Router, Vite; Vitest for unit tests |
| Charts and maps | Chart.js (`vue-chartjs`), ApexCharts, Leaflet with a heat layer |
| Backend | Spring Boot (Java 17): web, JDBC and JPA |
| Data | MySQL in production, H2 in development; the official election results are read from their XML files |
| Auth | JWT (`jjwt`) and `spring-security-crypto` for passwords |
| Hosting | Docker Compose: backend on port 3001, frontend on 3000 |

## Getting started

```bash
cd frontend-vue
npm install
npm run dev               # http://localhost:3000
```

```bash
cd backend
./mvnw spring-boot:run    # API on http://localhost:3001
```

`docker compose up --build` runs both. The longer guide, with IDE steps, is in the
[installation guide](docs/project-docs/technical-documentation/installation-guide.md).

## Features

- **Home**: the most or least voted parties and candidates at a glance.
- **Results**: compare municipalities on election and population data, a votes table per constituency, municipality
  or polling station, and a chart you configure yourself and can download as SVG, PNG or CSV.
- **Party and candidate pages**: every candidate's votes, and a candidate's votes per municipality.
- **Map**: votes by municipality as a heat map.
- **Forum**: read posts, and write your own after registering.
- **Admin**: account management.

## Documentation

- [Introduction](docs/project-docs/technical-documentation/introduction.md)
- [Data processing](docs/project-docs/technical-documentation/data-processing.md)
- [API documentation](docs/project-docs/technical-documentation/api-documentation.md)
- [Security](docs/project-docs/technical-documentation/security.md)
- [Architecture overview](docs/project-docs/technical-documentation/architecture.md)
- [Installation guide](docs/project-docs/technical-documentation/installation-guide.md)
- [Deployment](docs/project-docs/technical-documentation/deployment.md)
- [Logging and monitoring](docs/project-docs/technical-documentation/logging.md)
- [Troubleshooting](docs/project-docs/technical-documentation/troubleshooting.md)

## Project structure

| Path | |
|---|---|
| `frontend-vue/src/pages/` | Home, results, compare municipalities, discover, party and candidate details, forum, account, admin, info and legal pages |
| `frontend-vue/src/components/` | Tables, selection elements and per-page components |
| `frontend-vue/src/composables/` | Fetching, accounts, admin, chart configuration and data selection |
| `backend/src/main/java/com/example/backend/controller/` | REST controllers: votes, parties, candidates, constituencies, municipalities, polling stations, charts, map, posts, accounts and admin |
| `backend/.../xmlreader/` | Reads the election result XML into the database |
| `backend/.../authentication/` | JWT creation and the request filter |
| `backend/src/main/resources/` | Spring profiles for development, test and production |
| `docs/project-docs/` | Technical documentation, API and chart docs, design, UML and user tests |
| `docs/school-docs/` | School documents |
