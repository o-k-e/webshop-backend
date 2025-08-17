# 1️⃣ Build stage – Maven image
FROM maven:3.9.6-eclipse-temurin-21 AS builder
# A fájlokat bemásoljuk és lebuildeljük
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# 2️⃣ Runtime stage – csak a JAR
FROM eclipse-temurin:21-jdk

# JAR fájl bemásolása
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot default port
EXPOSE 8080

# JAR indítása
ENTRYPOINT ["java", "-jar", "app.jar"]