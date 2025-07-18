# Use official Maven image to build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use a minimal JDK runtime for running the app
FROM eclipse-temurin:21-jre-alpine

# Set working directory in runtime container
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/billibgsoftwere-0.0.1-SNAPSHOT.jar .

# Expose application port (change if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app/billibgsoftwere-0.0.1-SNAPSHOT.jar"]