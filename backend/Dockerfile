# Stage 1: Dependencies
FROM maven:3.8.3-openjdk-17-slim AS dependencies
WORKDIR /home/app
COPY backend/restApi/pom.xml restApi/pom.xml
WORKDIR /home/app/restApi
RUN mvn dependency:resolve dependency:resolve-plugins

# Stage 2: Build the Project
FROM dependencies AS build
WORKDIR /home/app
COPY backend/restApi /home/app/restApi
WORKDIR /home/app/restApi
RUN mvn package -DskipTests

# Stage 3: Deployment
FROM eclipse-temurin:17-jre AS deploy
WORKDIR /home/app

# Copy the built JAR file from the previous stage
COPY --from=build /home/app/restApi/target/restApi-0.0.1-SNAPSHOT.jar /home/app/app.jar

# Optionally, copy additional resources if needed
COPY backend/restApi/src/main/resources /home/app/back/src/main/resources

# Expose the port that your Spring Boot application is using (adjust if needed)
EXPOSE 9000

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/home/app/app.jar"]