FROM maven:latest

WORKDIR /

COPY . .

RUN mvn clean install
RUN mvn clean package

EXPOSE 8084

# Start repo app
CMD ["java", "-jar", "/target/springboot-backend-0.0.1-SNAPSHOT.jar"]