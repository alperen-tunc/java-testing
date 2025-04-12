FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# Nur pom.xml und Quellen kopieren
COPY pom.xml ./
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]