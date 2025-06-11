This is a very simple Java spring application that provides a web shop interface. There is no DB required, H2 runs in memory and the app is somewhat static.

To run:
1. mvn clean install
2. mvn spring-boot:run

You should then see the app is available on http://localhost:8080/

Tests
Unit tests are avaialble by running
1. mvn test


If you need to change the port it runs on, you can access src/main/resources/application.properties and change the server.port property, or pass through the command line (usual spring possibilities)


For Sealights Integration:
1. You can easily integrate for builds and unit tests run on CI/CD by using the Maven plugin 
2. If you want to run the app standalone to capture code coverage for other sorts of tests, then this is fairly simple
3. a) Check you have the compiled JAR file by running 'mvn clean install' on the root
   b) In your ./target directory you should see something like pet-accessories-store****.jar 
   c) You can start this simply by usual command eg. 'java -jar pet-accessories-store.jar' 
   d) To use with sealights you will need to use sl-test-listener.jar and then supply appropriate parameters
   e) runpetstore.sh attempts to make this a little simpler, you will need a buildSessionId.txt file and sltoken.txt file ready to go (assuming therefore you've run a build scan)
   f) Start a test session on SL in some way, run tests, close tests, observe coverage