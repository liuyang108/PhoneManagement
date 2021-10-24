FROM openjdk:11
MAINTAINER belong.com.au
RUN mkdir -p /app
WORKDIR /app

COPY target/PhoneManagement-0.0.1-SNAPSHOT.jar /app/PhoneManagementService.jar
ENTRYPOINT ["java","-jar","/app/PhoneManagementService.jar"]
