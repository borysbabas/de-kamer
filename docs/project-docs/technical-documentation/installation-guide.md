# Installation guide

This guide will help you to install the project on your local machine.

## Prerequisites

- Npm
- Vue.js
- Git
- Java
- Hibernate
- Lombok
- Maven

## Installation

### Step 1: Clone the repository

#### Using SSH

bash
git clone git@gitlab.fdmci.hva.nl:semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/riifiipoowee33.git <project-name>


#### Using HTTPS

bash
git clone https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/riifiipoowee33.git <project-name>


### Step 2: Open the project

bash
cd <project-name>


### Step 3: Install the dependencies

#### Frontend

bash
cd frontend-vue
npm install


#### Backend

In your IDE, open up the backend folder and locate the pom.xml file. Right-click on the file and select Maven > Reload project.

### Step 4: Run the project

#### Frontend

bash
cd frontend-vue
npm run dev


#### Backend

In your IDE, locate the BackendApplication.java file in the backend folder under src > main > java > com > example > backend. Right-click on the file and select Run > Application.

### Step 5: Open the project

Open your browser and navigate to http://localhost:3000/. The project should now be running.

## Troubleshooting

If you encounter any issues during the installation process, please refer to the [troubleshooting guide](troubleshooting.md) for assistance.

---

[Next page: Deployment](deployment.md) | [Back to title page](/README.md) | [Previous page:
Architecture overview](architecture.md)