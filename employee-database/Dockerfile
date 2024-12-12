FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 9001
ADD target/employee-database.jar employee-database.jar
ENTRYPOINT ["java", "-jar", "/employee-database.jar"]