# using multistage docker build
# ref: https://docs.docker.com/develop/develop-images/multistage-build/

# Install maven and copy project for compilation
FROM maven:3.9.3-eclipse-temurin-17-alpine AS builder

WORKDIR /opt/app/

COPY src ./src
COPY pom.xml ./

RUN mvn clean install -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine

#ARG JAR_FILE=app/build/libs/DroneDeliveryApp-0.0.1-SNAPSHOT

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
#COPY ${JAR_FILE} app.jar
COPY --from=builder /opt/app/target/DroneDeliveryApp-0.0.1-SNAPSHOT.jar ./app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]