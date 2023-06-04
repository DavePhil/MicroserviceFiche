FROM openjdk:8
WORKDIR /app
EXPOSE 9004
COPY target/microfiche.jar /app
CMD ["java","-jar","microfiche.jar"]