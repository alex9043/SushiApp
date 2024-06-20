FROM gradle:jdk22-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean build -x test --stacktrace --info

FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]