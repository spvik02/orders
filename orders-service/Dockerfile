FROM openjdk:17-jdk-slim

WORKDIR /app/orders-service

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build --no-daemon

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/orders-service/build/libs/orders-service-0.0.1-SNAPSHOT.jar"]
