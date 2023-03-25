FROM openjdk:8-alpine

RUN mkdir /usr/app

WORKDIR /usr/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:resolve

COPY src ./src

RUN ./mvnw package -DskipTests

RUN cp target/test-*.jar /test.jar