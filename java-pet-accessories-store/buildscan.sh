java -jar sl-build-scanner.jar -restorePom -workspacepath .
java -jar sl-build-scanner.jar -pom -configfile slbuild.json -workspacepath .
mvn clean install
