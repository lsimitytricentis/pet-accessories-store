#!/bin/bash
echo "--- Running Command: Reference the Build scanner in POM file ---"
java -jar sl-build-scanner.jar -pom -configfile slbuild.json -workspacepath .
echo "--- Command Finished ---"
exit 0
