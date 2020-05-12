FROM java:8-jre
MAINTAINER Kolya Attila <attila.kolya@yahoo.com>

ADD ./target/auth-service.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/auth-service.jar"]

EXPOSE 8884
