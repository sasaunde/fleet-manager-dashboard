# Use an official OpenJDK runtime as a base image
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

RUN echo "$PWD"


# Copy the JAR file from the target directory to the container
COPY target/fleet-manager-1.0.0.jar app.jar

# Expose the port your application runs on
EXPOSE 8990

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
