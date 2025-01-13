# How to deploy

This guide will go through the steps to deploy the application. 

## Infrastructure

The backend of the application is deployed on Render.com. The frontend is deployed on Strato. The database is hosted on Aiven.

## Prerequisites

- You have installed [Docker](https://docs.docker.com/get-docker/) on your machine.
- You have a [Docker Hub](https://hub.docker.com/) account.
- You have access to the [Render.com](https://render.com/) platform.
- You have access to the [Aiven](https://aiven.io/) platform.
- You have access to the [Strato](https://www.strato.de/) platform.
- You have installed a SFTP client ([FileZilla](https://filezilla-project.org/) for example).
- You have installed the Java Development Kit (JDK) on your machine.
- You have the xml dataset.

## Steps

### Step 1: Update the main branch

Make sure you have the latest changes from the main branch.

```bash
git checkout main
git pull origin main
```

### Step 2: Frontend for production

Navigate to the `frontend` directory and run the build script. There are multiple build scripts 
available, make sure to choose the build-production script.

```bash
cd .\frontend-vue\
npm run build-production
```

After the build is finished, you can find the production-ready files in the `dist` directory. 

Now you can build the Docker image for the frontend.

```bash
docker build --tag <your-docker-username>/frontend-vue .
```

### Step 3: Backend for production

Do the same for the backend. Navigate to the `backend` directory and run the build script.

```bash
cd .\backend\
mvn clean package
```

After the build is finished, you can find the production-ready jar file in the `target` directory.

Now you can build the Docker image for the backend.

```bash
docker build --tag <your-docker-username>/backend .
```

### Step 4: Deploy the database (once per project)

Because of the free tier of Aiven, the insertion of a lot of data will be slow. 
The dataset can luckily be inserted once and shouldn't need to be updated afterward. 

Navigate to the [BackendApplication.java](../../../backend/src/main/java/com/example/backend/BackendApplication.java)
file and set the `RUN_PARSER` variable to `true`. Go to the [Dockerfile](../../../backend/Dockerfile)
and after `COPY target/*.jar my-app.jar` add `COPY res/* res/*`. This will copy the dataset to the Docker image 
and the parser will insert the data into the database when deployed to Render. The advantage of this
opposed to inserting the data on your local machine is that the parser will run 24/7 until all the data 
is inserted, so there is no need to keep your machine running. Also make sure to add the dataset to the 
backend root in the `res` directory.

1. Set `RUN_PARSER` to `true` in [BackendApplication.java](../../../backend/src/main/java/com/example/backend/BackendApplication.java)
2. Add `COPY res/* res/*` to the [Dockerfile](../../../backend/Dockerfile)
3. Add the dataset to the `res` directory in the backend root

```bash
cd .\backend\
mkdir -p res
cp <path-to-dataset> .\res\
```

### Step 5: Test the application locally

Before deploying the application, it is recommended to test the application locally.
This ensures that the application is working as expected.

```bash
docker run -p 3000:3000 <your-docker-username>/frontend-vue
```

```bash
docker run -p 3001:3001 <your-docker-username>/backend
```

### Step 6: Push the Docker images to Docker Hub

Before deploying the application, you need to push the Docker images to Docker Hub.

```bash
docker push <your-docker-username>/frontend-vue
```

```bash
docker push <your-docker-username>/backend
```

### Step 7: Deploy the application

To deploy the backend on Render, you need to create a new web service or log in to an existing one.
We will be using the latter, since we already have a web service running. 

1. Navigate to the [Render dashboard](https://dashboard.render.com/) and log in. 
2. Click on the web service you want to deploy the backend to.
3. Click on `Manual Deploy`
4. Choose `Deploy latest reference`

To deploy the frontend on Strato, you need to buy a domain or log in to an existing one.
Again, because we already have a domain, we will be using the latter. Sadly, Strato does not
support Docker images, so we need to deploy the frontend manually using SFTP. 

1. Open your SFTP client and connect to your Strato server.
2. Add the files from the `dist` directory to the root of your domain.

### Step 8: Test the live application

Make sure to test the live application to ensure that everything is working as expected.

### Step 9: Update deployment logs



## Result

The application is now deployed and can be accessed by anyone.

