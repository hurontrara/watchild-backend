# Description: Dockerfile for building the Spring Boot application
FROM openjdk:23-jdk as build
WORKDIR /workspace/app
RUN microdnf install findutils
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

# Package the application
FROM openjdk:23-jdk
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
