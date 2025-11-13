# ==== Build stage ====
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /build

# Copy only what we need for the Maven build
COPY pom.xml .
COPY src ./src

# Build the JAR (tests can run in CI; here we skip for speed)
RUN mvn -q -DskipTests package && ls -la target

# ==== Runtime stage ====
FROM eclipse-temurin:17-jre-alpine

# Run as non-root (simple hard-coded UID)
USER 1000

WORKDIR /app

# The Maven template in your repo builds java-hello-1.0.0.jar
# If you ever change artifactId/version, update this filename.
COPY --from=build /build/target/java-hello-1.0.0.jar app.jar

# No Main-Class in manifest → start via classpath + FQCN
ENTRYPOINT ["java", "-cp", "/app/app.jar", "com.example.cicd.App"]
