FROM openjdk:8
WORKDIR /app

COPY target/microfiche.jar /app
CMD ["java","-jar","microfiche.jar"]