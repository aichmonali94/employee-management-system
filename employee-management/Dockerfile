FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8001
ADD target/employee-management.jar employee-management.jar
ENTRYPOINT ["java", "-jar", "/employee-management.jar"]