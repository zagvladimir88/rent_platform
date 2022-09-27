FROM openjdk:15
ADD /target/rent_platform-1.0-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]
