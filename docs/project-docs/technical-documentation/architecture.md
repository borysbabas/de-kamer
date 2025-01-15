# Architecture Document

## 1. Overview of the System

**Key Features**:

1. Municipality comparison tool.
2. Graphical data visualizations.
3. Forum with Markdown-supported posts.
4. Login and registration system.
5. Admin dashboard.
6. Search bar for parties/candidates.
7. Tabular data on voting offices.

**Supported Platforms**: Desktop, tablet, and mobile (fully responsive design).

---

## 2. Roles and Permissions

**Roles**:

- **Admin**: Full access to manage users and content.
- **Unblocked User**: Access to all features except admin-specific tools.
- **Blocked User**: Restricted access; no posting allowed.

---

## 3. Technical Stack

**Frontend**:

- Vue.js
- HTML, CSS, TypeScript

**Backend**:

- Spring Boot and Java
- Hosted on Render

**Database**:

- MySQL, hosted on Aiven

**APIs**:

- Custom API for data exchange, hosted on Render.
- External APIs for supplemental data, such as average voter age.

**Deployment**:

- Dockerized deployment pipeline
- Hosted on Strato

---

## 4. System Design

### 4.1 Frontend Architecture

- Component-based structure in Vue.js.
- Reusable components for graphs, tables, and forms.
- Media queries for responsive design.

### 4.2 Backend Architecture

- RESTful API design in Spring Boot.
- Handles authentication, data retrieval, and updates.
- Implements business logic for municipality comparisons, forum posts, and user roles.

### 4.3 Database Design

- Relational model in MySQL.
- **Tables**:
    - Users (encrypted passwords, role management)
    - Posts (Markdown content, timestamps)
    - Election Results (per municipality and vote office)

### 4.4 Security

- **Authentication**: JWT token-based validation.
- **Data Protection**: Encrypted passwords and secure API communication.
- **User Management**: Role-based access control.

### 4.5 Hosting and Deployment

- Docker for containerized deployment.
- Hosted on Strato for frontend and Render for backend and API. Aiven for database

---

## 5. Key Pages

1. **Homepage**: Overview of election results and navigation to other features.
2. **Info Page**: Details about the election and development team.
3. **Results Page**: Graphical and tabular representation of voting data.
4. **Forum**: Interactive platform for discussions.
5. **Login/Register**: Authentication system for user access.
6. **Admin Dashboard**: Management of users and content.
7. **Dicover Page**: General overview of what you can do on the website.

---

## 6. Data Flow

### Interaction Between Frontend, Backend, and Database

1. **Frontend**:

    - Sends user requests (e.g., login, search) to the backend via API calls.
    - Receives data responses from the backend and renders content dynamically.

2. **Backend**:

    - Validates requests and processes business logic.
    - Interacts with the database for CRUD operations.
    - Fetches supplemental data from external APIs (e.g., average voter age) when required.

3. **Database**:
    - Stores persistent data such as user credentials, forum posts, and election results.
    - Provides data to the backend upon request.

**Example Flow**:

- A user searches for a political party.
    1. The frontend sends the search term to the backend via an API call.
    2. The backend queries the MySQL database for matching records.
    3. Results are sent back to the frontend and displayed to the user.

---

## 7. Challenges and Constraints

- Scalability to support Europe-wide elections in the future.
- Ensuring data privacy for sensitive user information.
- Maintaining real-time updates for dynamic election data.

---

## 8. Future Enhancements

- Real-time data streaming for live election results.
- Support for more advanced data analytics and AI-based predictions.

---

[Next page: Installation guide](installation-guide.md) | [Back to title page](/README.md) | [Previous page:
Security](security.md)