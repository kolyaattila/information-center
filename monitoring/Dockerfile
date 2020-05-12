FROM java:8-jre
MAINTAINER Kolya Attila <attila.kolya@yahoo.com>

ADD ./target/monitoring-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/monitoring-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
