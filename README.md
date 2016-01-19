Generic Rest Web App : mvn + Java 7 + Spring MVC + Spring core + Logback

This is a ZERO config webapp. 
It is polyglott : it accepts application/json, application/xml, text/yaml and application/x-www-form-urlencoded
It produces : application/json, application/xml, text/yaml and text/html (+ jquery + bootstrap)

To start the server, use one of these commands : 
* `mvn install && mvn jetty:run`
* `mvn install && java -jar target/GenericRestWebApp.jar`
* `docker pull libetl/genericrestwebapp:latest && docker start -p8080 -d libetl/genericrestwebapp:latest`

You can scaffold this web app to build yours. It is generic and can be adapted to your business needs.

