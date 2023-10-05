FROM openjdk:17-jdk

WORKDIR /app

COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]