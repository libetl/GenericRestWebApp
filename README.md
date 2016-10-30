Generic Rest Web App : mvn + Java 8 + Spring MVC + Spring core + Logback

This is a ZERO config webapp. 
It is polyglott : it accepts application/json, application/xml, text/yaml and application/x-www-form-urlencoded
It produces : application/json, application/xml, text/yaml and text/html (+ jquery + bootstrap)

To start the server, use one of these commands : 
* `mvn install && mvn jetty:run` (after a project check out)
* `mvn install && java -jar target/GenericRestWebApp-0.0.1-SNAPSHOT.jar` (after a project check out)
* `docker pull libetl/genericrestwebapp:latest && docker run -d -p 8080 libetl/genericrestwebapp:latest` (docker needs to be installed first)

You can scaffold this web app to build yours. It is generic and can be adapted to your business needs.

