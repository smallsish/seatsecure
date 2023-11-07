#FROM openjdk:17-jdk
FROM openjdk:17-jdk as build
#WORKDIR /app
WORKDIR .
COPY . .
RUN chmod +x mvnw
# RUN ./mvnw package -DskipTests
RUN ./mvnw clean package
# COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
FROM openjdk:17-jdk-slim
WORKDIR .
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]