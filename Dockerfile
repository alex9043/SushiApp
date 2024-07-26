FROM gradle:jdk22-alpine AS build

WORKDIR /app

VOLUME /home/gradle/.gradle

COPY build.gradle settings.gradle ./

RUN gradle clean build -x test --stacktrace --info || return 0

COPY src ./src

RUN gradle clean build -x test --stacktrace --info

FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

COPY src ./src

VOLUME /app/src

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]