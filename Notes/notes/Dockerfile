FROM openjdk:8-jdk-alpine
LABEL maintainer="darpanpatel3131@gmail.com"
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=build/libs/notes-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} notes.jar
ENTRYPOINT ["java","-jar","/notes.jar"]
