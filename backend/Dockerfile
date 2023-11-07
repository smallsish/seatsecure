#FROM openjdk:17-jdk
FROM openjdk:17-jdk as build
#WORKDIR /app
WORKDIR .
COPY . .
RUN ./mvnw package -DskipTests
# COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar
FROM openjdk:17-jre-slim
WORKDIR .
COPY --from=build /target/backend.jar .
EXPOSE 8080
CMD ["java", "-jar", "backend.jar"]