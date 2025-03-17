FROM openjdk:23-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file to the working directory
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the entry point for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
