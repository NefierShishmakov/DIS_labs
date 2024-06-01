FROM openjdk:19

WORKDIR /app

COPY build/libs/server.jar /app/server.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/server.jar"]