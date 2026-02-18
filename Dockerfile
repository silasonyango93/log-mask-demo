# Use Java 17 JDK on Alpine Linux
FROM openjdk:17-jdk-alpine

# Environment variables
ENV ADVANTA_API_KEY de8d4d1e223c0d21e685eee3cf0162b6
ENV ADVANTA_APP_TOKEN iGwQWm5OdlLPsd2YT2AQ8CAVMh8Khkia

# Argument for the JAR file to be copied
ARG JAR_FILE=target/*.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]