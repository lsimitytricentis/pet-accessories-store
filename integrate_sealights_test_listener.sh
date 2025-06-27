#!/bin/bash
echo "--- Running Command: Reference Test Listener into POM file ---"
java -jar sl-build-scanner.jar -pom -configfile sltest.json -workspacepath .
echo "--- Command Finished ---"
exit 0
