FROM amazoncorretto:23-alpine-jdk

WORKDIR /app

COPY build/libs/app.jar app.jar

# Expose the port the app runs on (optional)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
