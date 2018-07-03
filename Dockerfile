FROM openjdk:alpine

ARG JAR_FILE=./target/paysafe_backend_test-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} /app/paysafe_backend_test.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "paysafe_backend_test.jar"]
