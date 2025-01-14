# Data Processing Documentation

## Data Collection

### Overview
The data collection on our website is limited to what is strictly necessary to support the functionality of the forum and related features. No additional information is collected or processed intentionally.

### Collected Data
When using the forum, we collect the following data:
- **Email Address**: Required for account creation and communication with users.
- **Username**: Used as a unique identifier for the forum.
- **Posts**: Content posted by users on the forum.
- **Date of Birth**: Collected during registration.
- **Password (hashed)**: Used for authentication. The password is securely stored in a hashed form and is not readable to us.

### Data Collection Method
- **Registration**: During account registration, the necessary data (email address, username, date of birth, and password) is collected.
- **Posting Content**: Posts are saved when a user publishes them.

No additional data collection is performed, such as logging search queries or other user interactions.

### Tools and Technologies
No external tools or technologies are used for data collection. All data is managed locally.

### Timing of Data Collection
- **During Registration**: Data is stored when a user successfully creates an account.
- **When Posting Content**: Posts are stored when they are submitted by the user.

### Informing Users
Users are indirectly informed about data collection through notifications such as:
- Successful account creation.
  Users have the ability to delete their own posts, allowing data to be permanently removed from our system.

### Storage Location
All collected data is stored in an SQL database. No data is stored locally on user devices or in external systems.

----------------------------------------------------------------------------------------------------------------------------

## Data Storage

### Overview
Our data is stored in an external database solution and is protected with appropriate measures to ensure both privacy and security. Below are the details regarding storage and access management.

### Storage Locations
- **Database**:  
  All data is stored in an external **Aiven database**.  
  The database contains:
    - User data (email address, username, date of birth, hashed passwords)
    - Forum data (posts)

### Security Measures
1. **Access Control**:
    - Only administrators have access to the database via special **administrator accounts**.
    - Access to administrator accounts is strictly monitored and limited.
    - Users can manage their own data via the website (e.g., delete posts).

2. **Encryption**:
    - **Passwords** are hashed using **bcrypt** to ensure the security of user accounts.
    - **JWT (JSON Web Tokens)** is used for authenticating actions performed by users, such as managing accounts or posts.

3. **Data Transmission**:
    - Data is transmitted over secure connections (HTTPS) between the website and the database.

### Back-ups and Data Integrity
- No backups of the database are made because this is a project environment (school website).

### Access Rights
- **Admins**:  
  Only administrators have direct access to the database and can manage it.
- **Users**:  
  Users can manage their own posts and accounts via the website and delete them if desired.

### Logging
        No log files are currently stored.
----------------------------------------------------------------------------------------------------------------------------

## Data Processing

### Overview
The data processing on our website is simple and focused on supporting the basic functionality of the website. No complex processing steps are performed, and the user has control over their own data.

### Processing Steps
1. **Validation**:
    - During account creation, the following checks are made:
        - Whether the provided **username** and **email address** are already in use.
        - Whether all required fields are correctly and completely filled in.
    - In case of an error, the user receives a general error message (“Something went wrong”).

2. **Storage**:
    - Once validation is successful, the data is directly sent to the backend, where it is processed and stored in the database.
    - No further transformation is performed.

3. **User Management**:
    - Users have full control over their data, such as changing their password or username.

### Analysis and Cleanup
- **Analysis**: No analysis is performed on the data.
- **Cleanup**: No automated cleanup processes are in place. However, users have the ability to edit or delete their own data, such as posts or accounts.

### Automation
- There are no automated processes for data processing or reporting.

----------------------------------------------------------------------------------------------------------------------------


## Data Transfer

### Overview
Data is transferred between the frontend (the website) and the backend using various HTTP methods. The data transfer is secure and utilizes modern authentication and authorization methods to ensure the privacy and security of users.

### Methods of Data Transfer
- **HTTP Methods**:
    - **POST**: Used to create new posts.
    - **GET**: Used to retrieve data, such as fetching public information and using tools like heatmaps and charts.
    - **DELETE**: Used to delete posts.
    - **Fetch API**: Used to provide dynamic data from the backend to the frontend, such as retrieving user or post information.

- **External APIs**:
    - For the **heatmap**, an external API from CBS is called to retrieve coordinates of municipalities in the Netherlands for visualization. This is done via a GET request.

### Security of Data Transfer
- **HTTPS**:  
  Communication between the website (frontend) and the backend always occurs via **HTTPS**, ensuring an encrypted connection and securing data during transfer.

- **JWT**:
    - User authentication is enforced using **JWT (JSON Web Tokens)**. The JWT token is securely stored in the frontend and is sent with every request to the backend for validation.
    - The JWT token is checked by the backend with each request to verify the user's validity.
    - Passwords are **never** transmitted during data transfer. Only public user information, such as **email**, **username**, and **age**, is available after successful login.

### Data Transferred
- **Post Data**:  
  The content of posts (including title and text) is sent to the backend when creating or deleting posts.

- **User Data**:  
  Only public user data (such as email, username, and age) is transferred. Sensitive data, like the password, is never transmitted.

- **Tools Data**:  
  **GET** requests are sent to the backend to fetch public data for tools like the heatmap and charts.

### Authentication and Authorization
- **JWT Token Validation**:  
  The backend checks each request with a JWT token to confirm that the user is authenticated and authorized to perform the requested action.

- **Fetch Interceptor**:  
  The frontend contains a **fetch interceptor** that checks whether the user has a valid account and is authorized to perform the requested action. If the user is not authorized, the action is blocked.

### Error Handling
- **Error Messages**:  
  In case of errors, such as attempting to access non-existent pages, a **404 error** is displayed. Users will receive a popup notification and be automatically redirected to a valid page.

### Limitations
- **No File Uploads**:  
  There is no functionality to upload files to the database. Only text-based posts, including a title, can be submitted and stored.

----------------------------------------------------------------------------------------------------------------------------


## Data Usage

### Overview
The data collected on our website is used solely for basic functionality and is not intended for advanced applications such as personalization, statistical analysis, machine learning, or marketing.

### Applications
1. **Personalization**:  
   The collected data is **not** used for personalizing the user experience. No content adjustments or targeted recommendations are made based on the user's data.

2. **Statistical Analysis**:  
   **No** statistical analysis is performed on the collected data. No data is collected or analyzed to generate statistics on user behavior or post frequency.

3. **Machine Learning**:  
   Machine learning is **not** used for processing user data or making predictions or recommendations.

4. **Marketing**:  
   The collected data is **not** used for marketing purposes, such as targeted advertisements or email campaigns.

### User Control
- Since the collected data is used solely for basic functionality, there is no need for specific user settings or preferences regarding the use of their data for personalization or marketing.

### Data Sharing with Third Parties
- **No** data is shared with third parties for use. All collected data remains within the website's system and is not sent to external parties for other purposes.

----------------------------------------------------------------------------------------------------------------------------

## Security and Privacy

### Security Measures
1. **Encryption**:
    - **Passwords** are hashed with **bcrypt** before being stored in the database. This means that even administrators do not have access to users' passwords.

2. **Hosting Security**:
    - The **frontend** is hosted with **Strato**, the **backend** with **Render**, and the **database** with **Aiven**. Each of these external parties has their own security measures, but these are beyond our control. Since this is a school project, we do not bear additional responsibility for managing this security.

3. **Third-Party Security Measures**:
    - No further security of personal data is implemented by us, as the external parties likely have their own security measures in place. We rely on their infrastructure and security.

### User Rights
1. **Access to Data**:
    - Users can view their own data via the **detail page** of their account. They can view their **email**, **username**, and **age**.

2. **Right to Deletion**:
    - Users have the right to **delete** their data entirely. This can be easily done via the website, where users can delete their own posts and accounts.
    - As administrators, we can manually delete data, but users have full control over deleting their own data.

3. **No Control Over Data**:
    - Since users can delete their own data, we have no control over their data after deletion. Once they delete their data, it is permanently removed from the system.

### Data Breaches and Incidents
- There are currently **no procedures** for data breaches or security incidents, as this is a school project and such measures were not expected. There is no policy for notifying users about data breaches.

### Compliance with GDPR
- Since this is a school project, **explicit consent** is not sought for data collection, and no further compliance with GDPR is expected or implemented. The data is collected solely for the operation of the website and is not used for other purposes.
----------------------------------------------------------------------------------------------------------------------------

## Data Deletion

### Methods of Data Deletion
1. **Data Deletion**:
    - When a user deletes their **account**, **all posts** by that user are also deleted, along with the account itself. After this action, no data from the user is retained, and no log files or other data are returned or kept. The deletion is permanent.
    - Users can also delete **individual posts**, meaning they have control over their own contributions to the website. Deleting a post is final and cannot be undone.

2. **Anonymization**:
    - There is currently no use of **anonymization** for data. The data is entirely deleted when a user deletes their account or post.

### Data Deletion Schedule
1. **Deletion upon Account or Post Deletion**:
    - Data is **immediately deleted** once a user deletes their account or post. After confirmation from the user, the deletion is permanent and cannot be reversed.

2. **Retention Periods**:
    - There is no **retention period** for data. All data is immediately deleted once the user decides to delete it. No data is retained after the deletion of the account or posts.

### Deletion and Verification
1. **Deletion Verification**:
    - After data is deleted, the action is verified through **JWT token validation** in the backend. If the token is valid, the action is approved, and the user can delete the data.
    - After successful deletion, the user is redirected to the **homepage** of the website.

### Backups and Incomplete Deletion
1. **Backups**:
    - No data is retained in backups after deletion. All data can be fully deleted, and there are no **residues** of deleted data in the system, except for public data used for tools like the heatmap (this data is **permanently stored** and **not planned to be deleted**).

2. **Data That Cannot Be Deleted**:
    - No data is retained or returned after an account or post is deleted, except for the public data used by the website tools (such as for the heatmap).

### Legal or Administrative Reasons for Retention
- There are **no** legal obligations requiring data to be retained longer than strictly necessary. Data is only collected for the operation of the website and is fully deleted when the user requests it.
----------------------------------------------------------------------------------------------------------------------------

## Process Description

### 1. Data Collection
- **During account registration**, the user provides the following information:
    - **Username**
    - **Email**
    - **Password** (this is **hashed** with bcrypt in the backend and never leaves the backend, ensuring it is not accessible)
    - **Date of Birth**
- **When posting a post**, the user provides a **title** and a **body**. This data is associated with the user and stored in the database. The post is also marked with the **time of posting** and the **username** of the user who created the post.
- **Note**: No other user data is collected, as no other information is requested.

### 2. Data Storage
- The collected data is sent to the **backend** for processing and then stored in an **Aiven database**.
    - The **password** is encrypted (with bcrypt) and stored securely without ever leaving the backend.
    - The **username**, **email**, **date of birth**, and **posts** are stored openly in the database, but the database is only accessible to **authorized administrators** with an account on Aiven.
    - No other data is stored.

### 3. Data Processing
- **Registration**:
    - During user registration, it is checked whether the **username** or **email** is already in use. If so, the user receives an error message.
- **Post Submission**:
    - When a post is submitted, it is checked to ensure the post is not empty. This check is done by the **frontend**.
    - **Id-based** storage is used for posts, meaning duplicates are generally not an issue.
    - No further processing of the data is done. The data is simply stored and displayed in the user interface.

### 4. Data Use
- **Username** is used to display the name of the user on a post they have made.
- The **title** and **body** of the post are publicly displayed on the website.
- **Email** and **date of birth** are only visible on the **account details page**, but only to the logged-in user (when they view their own account).
- No other **statistics** or **analysis** of the data is performed. All data that is not visible on the account details page remains **hidden**.

### 5. Data Deletion
- Users can always delete their **posts** or their **account**.
    - When a user deletes a post, that post is **permanently deleted**.
    - When a user deletes their account, both the account and **all posts** of the user are **permanently deleted**.

### 6. Security During the Process
- **JWT Token Validation**:
    - For every post action (such as posting or deleting a post), the user is validated through a **JWT token**. This token determines whether the user is authorized to perform the action.
- All communication between the frontend and backend occurs through **secure connections (HTTPS)**, ensuring that data is encrypted during transfer.

----------------------------------------------------------------------------------------------------------------------------

## Tools and Technologies

### 1. **Database**
- **Database**: The website uses an **Aiven-managed PostgreSQL database** for storing user data and posts.
    - **Management**: The database is managed through **Aiven**, an external service that handles the database hosting and is only accessible to administrators with an account on Aiven.

### 2. **Backend Framework**
- **Technologies and Frameworks**:
    - The **backend** of the website is built using **Java** and utilizes the **Spring Boot** framework, combined with **JPA** (Java Persistence API) for database access and management.
    - Project dependencies are managed through **Maven**.

### 3. **External APIs**
- **External API**: The website uses the **CBS Open Data API** to retrieve **municipality information** in the Netherlands, which is used for the heatmap functionality.

### 4. **Authentication**
- **Authentication**: The website uses **JWT (JSON Web Tokens)** for user authentication. This ensures that every user action requiring authentication, such as posting or deleting a post, is validated using a valid token.

### 5. **Encryption**
- **Encryption**:
    - **Passwords**: Passwords are encrypted with **bcrypt** before being stored in the database, ensuring that passwords are not stored in plain text.
    - **Communication**: All communication between the frontend and backend is secured via **HTTPS**, ensuring data encryption during transfer.

### 6. **Frontend Framework**
- **Frontend**: The website is built with **Vue.js** and **TypeScript**. The following npm packages are used:
    - **ApexCharts** for generating charts and statistics.
    - **Leaflet** and **Leaflet-Heat** for displaying interactive maps and the heatmap functionality.

### 7. **Monitoring and Logging**
- **Logging**: Currently, there is **no logging** performed for data processing or user activities.

### 8. **Security**
- **Security**: All data transfer between the frontend and backend is encrypted via **HTTPS**, ensuring data security during communication.

----------------------------------------------------------------------------------------------------------------------------

## Security Measures

### 1. **Encryption**
- **Password Security**: Passwords are securely stored using **bcrypt**, a secure hashing technique. This ensures that passwords cannot be viewed in plain text, even by administrators.
- **Data Encryption**: Currently, no other data besides passwords is encrypted. All communication between the frontend and backend is secured via **HTTPS**, which ensures data encryption during transfer.

### 2. **Access Control**
- **Role-Based Access**:
    - There are two main roles: **admin** and **user**.
    - **Admins** have the ability to manage users, such as banning users. Only administrators can **manually create accounts** in the database.
    - **Users** only have access to their own data and can create and delete posts.

- **Database Access**:
    - The **database** is secured and only accessible to administrators with a valid account on **Aiven**. This prevents unauthorized access to the data in the database.

- **Authentication**:
    - **JWT** is used for **user authentication**. This ensures that users can only perform authorized actions when they log in and have a valid token.

### 3. **Data Protection**
- **Protection Against Data Breaches**:
    - **Passwords** are encrypted with bcrypt, making them inaccessible in plain text. This provides protection against password breaches.
    - There is no additional layer of data security, but the database is only accessible through an account managed by **Aiven**, and there are no known means of unauthorized access.

### 4. **Logging and Monitoring**
- **Logging and Monitoring**:
    - Currently, there is **no logging** of suspicious activities or other security incidents.
    - No tools have been implemented to actively monitor security.

### 5. **Backups and Recovery**
- **Backups**:
    - **No regular backups** of data are made, as this is a school project. This means that if data is lost, recovery is not possible.

### 6. **API Security**
- **External APIs**:
    - The website uses the **CBS Open Data API** to retrieve **municipality information** for the heatmap functionality. This API is a **GET request** that does not require user data.
    - Since this API only retrieves public data, there is no security risk for user data.

----------------------------------------------------------------------------------------------------------------------------

### Data Flows

#### 1. **User Registration**:
- **Input**: User enters:
    - Username
    - Email
    - Age
    - Password
- **Validation**:
    - Backend checks if the email or username already exists.
    - Password is hashed (bcrypt) before being stored in the database.
- **Storage**:
    - The data is stored in the **Aiven database**, including hashed passwords.
- **Output**:
    - User receives a JWT token for future authentication.

#### 2. **Login**:
- **Input**: User enters:
    - Email
    - Password
- **Validation**:
    - Backend compares the entered data with the data in the database.
    - If valid, the user receives a JWT token.
- **Storage**:
    - No additional data is stored, but the user now has access to their account and associated data.
- **Output**:
    - JWT token is stored in the frontend (for authentication of future actions).

#### 3. **Data Storage**:
- **Storage Location**:
    - User data (username, email, age, and hashed passwords) is stored in the **Aiven database**.
    - No data is stored locally in the frontend, except the JWT token, which is used only for authentication.

#### 4. **Data Transfer**:
- **Input**:
    - Frontend requests data via **GET** requests for tools such as the **heatmap** and **charts**.
    - Backend provides customized data for the **chart** and **leaflet** via unique controllers.
- **Data Processing**:
    - CBS data (municipality data, population numbers, etc.) is fetched via an external API without user data.
    - No user data is shared with the CBS API; the backend only compares the data.
- **Security**:
    - All communication between the frontend and backend is secured via **HTTPS**.
    - JWT token is used to validate requests.

#### 5. **Data Deletion**:
- **Data Deletion**:
    - If a user deletes their account, all data related to that user (including posts) is immediately removed from the database.
    - Users can also delete their individual posts through the website.
- **Validation**:
    - Users must be logged in with a valid JWT token to delete their account.

#### 6. **Use of External APIs (CBS)**:
- **Input**:
    - Municipality and population data is requested via the **CBS Open Data API**.
    - This API provides public data such as municipality coordinates and population numbers.
- **Output**:
    - Data is processed by the backend and used for tools (such as the heatmap and comparison function).
    - No user data is shared with the CBS API, and users do not need to be logged in to use the tools.

---

### Visual Data Flow

If we were to represent these data flows visually in a flowchart, it would look approximately like this:

1. **Registration**:
    - User → Website (input) → Validation (Backend) → Storage in Aiven DB → JWT Token → User (output).

2. **Login**:
    - User → Website (input) → Validation (Backend) → JWT Token → User (output).

3. **Data Retrieval for Tools**:
    - Frontend → Backend (via GET requests) → Backend processes CBS data → Frontend receives customized data (e.g., for heatmap/charts).

4. **Data Deletion**:
    - User → Website (delete request) → JWT token validation → Deletion from Aiven DB → User (output).