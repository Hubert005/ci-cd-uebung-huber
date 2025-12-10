FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn -q -DskipTests package && ls -la target

FROM eclipse-temurin:17-jre-alpine

USER 1000

WORKDIR /app

USER root
RUN apk update && apk add --no-cache curl wget
USER 1000

COPY --from=build /build/target/java-hello-1.0.0.jar app.jar

ENTRYPOINT ["java", "-cp", "/app/app.jar", "com.example.cicd.App"]
