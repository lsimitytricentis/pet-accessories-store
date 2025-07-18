java -jar sl-build-scanner.jar -restorePom -workspacepath .
java -jar sl-build-scanner.jar -pom -configfile sltest.windows.json -workspacepath .
mvn clean verify
