FROM openjdk:8-jdk-alpine
LABEL maintainer="darpanpatel3131@gmail.com"
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=build/libs/searchlog-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} searchlog.jar
ENTRYPOINT ["java","-jar","/searchlog.jar"]
