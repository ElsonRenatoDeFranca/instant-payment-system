FROM maven:3.9-openjdk-21 AS builder

WORKDIR /build

COPY . /build

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21.0.1_12-jre-alpine

VOLUME /tmp

WORKDIR /opt

COPY --from=builder /build/target/*.jar /opt/app.jar

RUN adduser -D bradesco || adduser bradesco
USER bradesco

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar "/opt/app.jar"
