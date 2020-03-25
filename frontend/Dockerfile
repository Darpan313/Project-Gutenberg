FROM tomcat:8.0.51-jre8-alpine
RUN rm -rf /user/local/tomcat/webapps/*
ADD ./out/artifacts/frontend_war_exploded/frontend.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh","run"]
