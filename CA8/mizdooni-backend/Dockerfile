# Stage 1: Build the application
FROM maven:3.9.7-eclipse-temurin-21 AS build

# Set the working directory inside the build container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

RUN mvn clean install -DskipTests=true

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre

# Set the working directory inside the runtime container
WORKDIR /app

# Copy the jar file from the build container to the runtime container
COPY --from=build /app/target/*.jar MizdooniApplication.jar
COPY src/main/resources .

# Expose the port your Spring Boot application runs on
EXPOSE 443

# Run the jar file
ENTRYPOINT ["java", "-jar", "MizdooniApplication.jar"]