FROM openjdk:17
LABEL authors="Rajdeepak S"
COPY build/libs/JWT04-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]