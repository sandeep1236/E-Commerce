FROM openjdk:17-ea-17-jdk-slim

LABEL description="Dockerfile for E-Commerce application"

COPY target/E-Commerce-0.0.1-SNAPSHOT.jar E-Commerce-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","E-Commerce-0.0.1-SNAPSHOT.jar"]