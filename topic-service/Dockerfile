FROM maven:3.6.0-jdk-8-alpine
USER root
RUN mkdir -p /usr/src
WORKDIR /usr/src
COPY . /usr/src/

RUN mvn clean install
RUN ls -l
WORKDIR /usr/src/target
RUN ls -l
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","topic-service-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
