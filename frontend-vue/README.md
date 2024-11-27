# **Project Structure Documentation**

This structure represents a Vue.js frontend application categorized by features and components. Below is a tree-style
representation along with explanations of the folder purpose:

---

## **Tree Structure**

```plaintext
frontend-vue/
├── .vite/                             # Vite configuration folder (e.g., cache)
├── dist/                              # Compiled application build files
├── node_modules/                      # Project dependencies
├── public/                            # Static files (served directly as-is)
├── src/                               # Application source code
│   ├── assets/                        # Static assets like favicon, fonts, or styles
│   ├── img/                           # All image assets for the application
│   ├── components/                    # Shared or reusable Vue components
│   │   ├── pages/                     # Page-specific components
│   │   ├── tools/                     # General-purpose tools/components
│   │       ├── CustomTable.vue
│   │       ├── VotesTable.vue
│   ├── composables/                   # Vue composable utilities (reusable logic)
│   ├── pages/                         # Feature-based page components
│   │   ├── candidate-details/         # Candidate details page
│   │   │   ├── components/
│   │   │   ├── CandidateDetailsPage.vue
│   │   ├── footer/                    # Footer components
│   │   ├── forum/                     # Forum page
│   │   │   ├── components/
│   │   │   │   ├── ForumHome.vue
│   │   ├── home/                      # Home page
│   │   │   ├── components/
│   │   │   │   ├── HomePage.vue
│   │   ├── login/                     # Login page
│   │   │   ├── components/
│   │   │   │   ├── LoginPage.vue
│   │   ├── navbar/                    # Navigation bar components
│   │   │   ├── Navbar.vue
│   │   ├── party-details/             # Party details page
│   │   │   ├── components/
│   │   │   │   ├── PartyDetailsPage.vue
│   │   ├── register/                  # Registration page
│   │   │   ├── components/
│   │   │   │   ├── RegisterPage.vue
│   │   ├── results/                   # Results page
│   │   │   ├── components/
│   │   │   │   ├── ResultsPage.vue
│   ├── types/                         # TypeScript type definitions
│   ├── App.vue                        # Main Vue application file
│   ├── main.ts                        # Application entry point
│   ├── shims-vue.d.ts                 # TypeScript shim for Vue files
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