# syntax=docker/dockerfile:1

# Base image with Java 21
FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /build

# Copy the Gradle wrapper and Gradle configuration files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY ./src ./src

# Run Gradle build to resolve dependencies and build the application
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew build -x test

# Create a final image with only the JRE for running the application
FROM eclipse-temurin:21-jre-jammy AS final

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /build/build/libs/*.jar app.jar

# Expose the port on which your app will run
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
