FROM openjdk:8-jdk-alpine
LABEL maintainer="darpanpatel3131@gmail.com"
VOLUME /tmp
EXPOSE 8083
ARG JAR_FILE=build/libs/catalog-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} catalog.jar
ENTRYPOINT ["java","-jar","/catalog.jar"]
