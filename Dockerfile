FROM maven:3.2-jdk-7-onbuild
MAINTAINER libetl
LABEL Description="Generic Rest Web App" Vendor="libetl" Version="latest" Tag="libetl/GenericRestWebApp:latest"
EXPOSE 8080
CMD mvn jetty:run
