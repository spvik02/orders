FROM openjdk:17-jdk-slim

WORKDIR /app/number-generate-service

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/number-generate-service/build/libs/number-generate-service-0.0.1-SNAPSHOT.jar"]
