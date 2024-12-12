FROM openjdk:17-oracle
COPY target/*.jar my-app.jar
COPY res/* res/*
ENTRYPOINT ["java","-jar","my-app.jar"]