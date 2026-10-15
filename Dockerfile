# 1. Spring-Boot-Anwendung mit Java 25 bauen
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

COPY . .
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# 2. Nur die fertige Anwendung in ein schlankeres Laufzeit-Image kopieren
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /workspace/build/libs/*-SNAPSHOT.jar /app/app.jar

# Render erwartet standardmäßig Port 10000.
EXPOSE 10000
CMD ["sh", "-c", "exec java -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
