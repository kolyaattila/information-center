FROM java:8-jre
MAINTAINER Kolya Attila <attila.kolya@yahoo.com>

ADD ./target/topic-service.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/topic-service.jar"]

EXPOSE 8881


