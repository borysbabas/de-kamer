# **Project Structure Documentation**

This structure represents a Vue.js frontend application categorized by features and components. Below is a tree-style
representation along with explanations of the folder purpose:

---

## **Tree Structure | Frontend**

```plaintext
├── Dockerfile
│   * Instructions for building a Docker image.
├── README.md
│   * Project documentation.
├── UiServices
│   ├── FetchInterceptor.ts
│   │   * Handles HTTP fetch logic (e.g., headers, error handling).
│   └── SessionSbService.ts
│       * Manages session-based functionalities.
├── env.d.ts
│   * TypeScript declarations for environment variables.
├── index.html
│   * Entry HTML file for the Vue app.
├── package-lock.json
│   * Ensures consistent dependency versions.
├── package.json
│   * Defines scripts, dependencies, and project metadata.
├── public
│   ├── affiliation-logos
│   │   ├── 50plus.png
│   │   ├── bbb.png
│   │   └── ... (other party logos)
│   │       * Political party logos.
│   └── favicon.ico
│       * Browser tab icon.
├── src
│   ├── App.vue
│   │   * Root Vue component.
│   ├── assets
│   │   ├── favicon.ico
│   │   ├── img
│   │   │   ├── tweede-kamer-720p.jpg
│   │   │   └── zetelverdeling-tk.png
│   │   │       * Images for "Tweede Kamer" and seat distribution.
│   │   ├── main.css
│   │   │   * Main stylesheet.
│   │   ├── reset.css
│   │   │   * CSS reset for browser defaults.
│   │   └── svg
│   │       └── red-cross.svg
│   │           * SVG file for warnings/icons.
│   ├── components
│   │   ├── CustomTable.vue
│   │   ├── Selection-Element.vue
│   │   ├── VotesTable.vue
│   │   │   * Reusable table components.
│   │   ├── pages
│   │   │   ├── CandidateDetailsPage.vue
│   │   │   └── ResultsPage.vue
│   │   │       * Page components for candidates and results.
│   │   └── tools
│   │       ├── Apex-Chart-Template.vue
│   │       ├── Data-Selector-Bar.vue
│   │       └── chartConfig
│   │           └── Apex-Chart-Interface.vue
│   │               * Tools and interfaces for charts.
│   ├── composables
│   │   ├── useChartConfig.ts
│   │   ├── useFetch.ts
│   │   ├── useMunicipalities.ts
│   │   ├── useParties.ts
│   │   └── usePosts.ts
│   │       * Vue 3 Composition API for reusable logic.
│   ├── main.ts
│   │   * Entry point for the Vue app.
│   ├── pages
│   │   ├── candidate-details
│   │   │   └── CandidateDetailsPage.vue
│   │   │       * Page for candidate details.
│   │   ├── compare-municipalities
│   │   │   ├── CompareMunicipalities.vue
│   │   │   └── components
│   │   │       ├── MunicipalityCards.vue
│   │   │       └── MunicipalitySearch.vue
│   │   │           * Municipality comparison and search.
│   │   ├── forum
│   │   │   ├── ForumHome.vue
│   │   │   ├── components
│   │   │   │   ├── PostItem.vue
│   │   │   │   ├── add-post
│   │   │   │   │   ├── AddPost.vue
│   │   │   │   │   ├── AddPostButton.vue
│   │   │   │   │   └── MarkdownMenu.vue
│   │   │   │   ├── hero
│   │   │   │   │   ├── Hero.vue
│   │   │   │   │   ├── Pagination.vue
│   │   │   │   │   └── PostList.vue
│   │   │   │   └── recent-posts
│   │   │   │       └── RecentPosts.vue
│   │   │   └── pages
│   │   │       ├── AddPostPage.vue
│   │   │       └── ForumHome.vue
│   │   ├── home
│   │   │   ├── HomePage.vue
│   │   │   └── components
│   │   │       ├── AboutUs.vue
│   │   │       ├── Graph.vue
│   │   │       ├── Hero.vue
│   │   │       ├── LoadMore.vue
│   │   │       └── Parties.vue
│   │   │           * Homepage components.
│   │   ├── login
│   │   │   ├── LoginPage.vue
│   │   │   └── components
│   │   │       └── Login.vue
│   │   ├── navbar
│   │   │   ├── Navbar.vue
│   │   │   └── components
│   │   │       └── Searchbar.vue
│   │   │           * Navbar and search components.
│   │   ├── party-details
│   │   │   └── PartyDetailsPage.vue
│   │   └── results
│   │       ├── ResultsPage.vue
│   │       └── components
│   │           ├── Results.vue
│   │           └── VotesTable.vue
│   │               * Voting and results components.
│   ├── shims-vue.d.ts
│   │   * TypeScript declarations for `.vue` files.
│   └── types
│       ├── Account.ts
│       ├── Candidate.ts
│       ├── Municipality.ts
│       └── ... (other type files)
│           * TypeScript interfaces for structured data.
├── tsconfig.app.json
│   * TypeScript config for the app.
├── tsconfig.json
│   * Main TypeScript config file.
├── tsconfig.node.json
│   * TypeScript config for Node.js.
├── tsconfig.vitest.json
│   * TypeScript config for Vitest testing.
├── vite.config.ts
│   * Configuration for Vite build tool.
└── vitest.config.ts
    * Configuration for Vitest testing framework.
```

---

## **Categorization Explanation**

### 1. **Top-Level Folders:**

- `.vite/`: Used internally by Vite for caching and performance optimization.
- `dist/`: Contains build files after running `npm run build`.
- `node_modules/`: External dependencies managed by npm/yarn.
- `public/`: Static files such as logos or manifest files that don't need processing.

### 2. **Source (`src/`):**

- `assets/`: Stores project-specific assets (images, styles).
- `components/`: General and reusable Vue components, categorized further into:
    - `pages/`: Components specific to a page feature.
    - `tools/`: Utility components shared across multiple pages.
- `composables/`: Logic and utilities written as Vue 3 composition functions.
- `pages/`: Organized into feature-based folders:
    - **candidate-details**, **forum**, **home**, etc.: Each folder corresponds to a major feature or route in the app
      and contains:
        - `components/`: Subcomponents for the page.
        - Main `.vue` file implementing the page's functionality.
- `types/`: Contains `.ts` files for shared TypeScript definitions.
- `App.vue`: Root application component.
- `main.ts`: Entry point where Vue and plugins are initialized.
- `shims-vue.d.ts`: TypeScript support for Vue single-file components.

### 3. **Tree structure | Backend**

```plaintext
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── backend
│   │   │               ├── BackendApplication.java
│   │   │               ├── DTO
│   │   │               │   ├── AccountDTO.java
│   │   │               │   ├── AffiliationDTO.java
│   │   │               │   └── CandidateDTO.java
│   │   │               ├── authentication
│   │   │               │   ├── JWTRequestFilter.java
│   │   │               │   └── JWToken.java
│   │   │               ├── config
│   │   │               │   ├── ApiConfig.java
│   │   │               │   └── ApiPaths.java
│   │   │               ├── controller
│   │   │               │   ├── AffiliationController.java
│   │   │               │   ├── AuthenticationController.java
│   │   │               │   ├── CandidateController.java
│   │   │               │   ├── ConstituencyController.java
│   │   │               │   ├── DatabaseController.java
│   │   │               │   ├── MunicipalityController.java
│   │   │               │   ├── MunicipalityVotesController.java
│   │   │               │   ├── PostController.java
│   │   │               │   └── VotesController.java
│   │   │               ├── entities
│   │   │               │   ├── Account.java
│   │   │               │   ├── Affiliation.java
│   │   │               │   ├── AffiliationVotes.java
│   │   │               │   ├── Candidate.java
│   │   │               │   ├── Constituency.java
│   │   │               │   ├── ConstituencyVotes.java
│   │   │               │   ├── CountryVotes.java
│   │   │               │   ├── Municipality.java
│   │   │               │   ├── MunicipalityVotes.java
│   │   │               │   ├── PollingStation.java
│   │   │               │   ├── PollingStationVotes.java
│   │   │               │   └── Post.java
│   │   │               ├── models
│   │   │               │   ├── AffiliationNameModel.java
│   │   │               │   ├── AffiliationVotesModel.java
│   │   │               │   ├── CandidateVotesModel.java
│   │   │               │   ├── ConstituencyModel.java
│   │   │               │   ├── ConstituencyVotesModel.java
│   │   │               │   ├── MemberCountModel.java
│   │   │               │   ├── MunicipalityAffiliationVotesModel.java
│   │   │               │   ├── MunicipalityIdModel.java
│   │   │               │   ├── MunicipalityModel.java
│   │   │               │   ├── MunicipalityVoteModel.java
│   │   │               │   ├── MunicipalityVotesModel.java
│   │   │               │   ├── PostModel.java
│   │   │               │   ├── RegionModel.java
│   │   │               │   ├── VoteModel.java
│   │   │               │   └── XmlFileModel.java
│   │   │               ├── repositories
│   │   │               │   ├── AccountRepository.java
│   │   │               │   ├── AffiliationRepository.java
│   │   │               │   ├── AffiliationVotesRepository.java
│   │   │               │   ├── CandidateRepository.java
│   │   │               │   ├── ConstituencyRepository.java
│   │   │               │   ├── ConstituencyVotesRepository.java
│   │   │               │   ├── CountryVotesRepository.java
│   │   │               │   ├── MunicipalityRepository.java
│   │   │               │   ├── MunicipalityVotesRepository.java
│   │   │               │   ├── PollingStationRepository.java
│   │   │               │   ├── PollingStationVotesRepository.java
│   │   │               │   └── PostRepository.java
│   │   │               ├── services
│   │   │               │   ├── AccountService.java
│   │   │               │   ├── AffiliationService.java
│   │   │               │   ├── AffiliationVotesService.java
│   │   │               │   ├── CandidateService.java
│   │   │               │   ├── ConstituencyService.java
│   │   │               │   ├── ConstituencyVotesService.java
│   │   │               │   ├── CountryVotesService.java
│   │   │               │   ├── MunicipalityService.java
│   │   │               │   ├── MunicipalityVotesService.java
│   │   │               │   ├── PollingStationService.java
│   │   │               │   ├── PollingStationVotesService.java
│   │   │               │   └── PostService.java
│   │   │               ├── utils
│   │   │               │   ├── NotAcceptableException.java
│   │   │               │   ├── ResourceNotFoundException.java
│   │   │               │   ├── TwoKeyHashMap.java
│   │   │               │   └── UnAuthorizedException.java
│   │   │               └── xmlreader
│   │   │                   ├── FileDiscovery.java
│   │   │                   ├── XMLTagNames.java
│   │   │                   ├── XmlCandidateListReader.java
│   │   │                   ├── XmlConstituencyVotesReader.java
│   │   │                   ├── XmlElectionTreeReader.java
│   │   │                   ├── XmlFileTypeAssertion.java
│   │   │                   ├── XmlMunicipalityVotesReader.java
│   │   │                   ├── XmlStreamReaderV1.java
│   │   │                   └── XmlTotalCountReader.java
│   │   └── resources
│   │       ├── application-development.properties
│   │       ├── application-production.properties
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── backend
│                       └── BackendApplicationTests.java
└── target
├── classes
│   └── (compiled files for `src/main`)
├── generated-sources
│   └── annotations
├── generated-test-sources
│   └── test-annotations
└── test-classes
└── (compiled test files)
```