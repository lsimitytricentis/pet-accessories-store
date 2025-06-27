#!/bin/bash
echo "--- Running Command: Run Application ---"
#mvn spring-boot:run
#java -javaagent:sl-test-listener.jar -Dsl.tags=backend -Dsl.tokenFile=sltoken.txt -Dsl.buildSessionIdFile=buildSessionId.txt -Dsl.log.level=off -Dsl.log.toConsole=true -jar target/#pet-accessories-store-0.0.1-SNAPSHOT.jar
java -javaagent:sl-test-listener.jar -Dsl.tags=backend -Dsl.tokenFile=sltoken.txt -Dsl.buildSessionIdFile=buildSessionId.txt -Dsl.log.level=off -jar target/pet-accessories-store-0.0.1-SNAPSHOT.jar
echo "--- Command Finished ---"
exit 0
