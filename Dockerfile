FROM openjdk:8-jre-alpine

COPY /build/libs/package-api.jar /opt/package-api/package-api.jar

ENTRYPOINT exec java $JAVA_OPTS $@ -jar  /opt/package-api/package-api.jar

