#!/bin/bash
echo "--- Running Command: Restoring POM ---"
java -jar sl-build-scanner.jar -restorePom -workspacepath .
echo "--- Command Finished ---"
exit 0

