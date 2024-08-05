FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle clean build


FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]