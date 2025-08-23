FROM maven:3.9.9-amazoncorretto-23-debian AS build
WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jre-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar webshop-backend.jar

ENTRYPOINT ["java", "-jar", "webshop-backend.jar"]