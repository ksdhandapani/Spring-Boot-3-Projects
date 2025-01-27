# Use a base image with Java 17
FROM openjdk:17
# Copy the jar package into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dockerize-spring-boot-mysql.jar
# Expose port 8090
EXPOSE 8090
# Run the app
ENTRYPOINT [ "java", "-jar", "dockerize-spring-boot-mysql.jar" ]