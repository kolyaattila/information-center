FROM java:8-jre
MAINTAINER Kolya Attila <attila.kolya@yahoo.com>

ADD ./target/config.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/config.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://user:$CONFIG_SERVICE_PASSWORD@$CONFIG_SERVICE_HOST:8888/actuator/health || exit 1

EXPOSE 8888
