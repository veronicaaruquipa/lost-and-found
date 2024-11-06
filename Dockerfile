# Define the base image to spin up the container
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/lost-and-found-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]