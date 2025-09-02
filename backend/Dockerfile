# Etap 1: Budowanie aplikacji
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Etap 2: Tworzenie finalnego obrazu
FROM openjdk:21-slim
WORKDIR /fixpoint

# ZMIANA: Zainstaluj wget, który jest potrzebny dla skryptu wait-for-it.sh
RUN apt-get update && apt-get install -y wget --no-install-recommends && rm -rf /var/lib/apt/lists/*

# Skopiuj zbudowany .jar z etapu buildera
COPY --from=builder /app/target/*.jar app.jar

# Skopiuj i przygotuj skrypt czekający
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh

EXPOSE 8080

# Użyj skryptu do uruchomienia: poczekaj na 'keycloak-fixpoint:8080' i wtedy uruchom 'java -jar app.jar'
ENTRYPOINT ["./wait-for-it.sh", "keycloak-fixpoint:8080", "java", "-jar", "app.jar"]