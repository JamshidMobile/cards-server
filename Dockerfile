# 1. Stage: Build the fat JAR
FROM gradle:8.5-jdk17 AS builder

WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

# 2. Stage: Run in lightweight Alpine image
FROM openjdk:17-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
