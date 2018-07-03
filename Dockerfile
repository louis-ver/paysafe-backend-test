FROM maven:alpine

WORKDIR /app

COPY . .

EXPOSE 8080

RUN mvn -Dmaven.test.skip=true install

ENTRYPOINT ["java", "-jar", "target/paysafe_backend_test-0.0.1-SNAPSHOT.jar"]
