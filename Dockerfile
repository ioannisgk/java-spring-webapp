FROM tomcat:9.0.52-jdk11-corretto
COPY ./target/spring-app-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]