# Build stage
FROM maven:3.9.4-amazoncorretto-11 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml clean compile test package

# Image for deploy
FROM openjdk:11
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
