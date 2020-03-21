FROM java:8-jre
MAINTAINER Kolya Attila <attila.kolya@yahoo.com>

ADD ./target/config-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/config-0.0.1-SNAPSHOT.jar"]

HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://user:$CONFIG_SERVICE_PASSWORD@localhost:8888/actuator/health || exit 1

#http://${REGISTRY_USERNAME}:${REGISTRY_PASSWORD}@${REGISTRY_SERVICE_HOST}:8761/eureka
EXPOSE 8888
