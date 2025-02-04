# Stage 1: Build the Java application using Maven
FROM maven:3.9.2-eclipse-temurin AS build
WORKDIR /app

# Copy the pom.xml first to leverage Docker caching
COPY pom.xml .

# Download dependencies (this improves caching)
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a smaller OpenJDK image
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
