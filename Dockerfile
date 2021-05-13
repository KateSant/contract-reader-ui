# Build stage
FROM maven AS build
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml clean package

# Image for deploy
FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
