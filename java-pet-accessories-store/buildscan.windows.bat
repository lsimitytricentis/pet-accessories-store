java -jar sl-build-scanner.jar -restorePom -workspacepath .
java -jar sl-build-scanner.jar -pom -configfile slbuild.windows.json -workspacepath .
mvn clean install
