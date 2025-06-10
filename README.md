This is a very simple Java spring application that provides a web shop interface. There is no DB required, H2 runs in memory and the app is somewhat static.

To run:
1. mvn clean install
2. mvn spring-boot:run

You should then see the app is available on http://localhost:8080/

Tests
Unit tests are avaialble by running
1. mvn test


If you need to change the port it runs on, you can access src/main/resources/application.properties and change the server.port property, or pass through the command line (usual spring possibilities)