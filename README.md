### Test framework СTCo

##### General information
The framework created for autotests with extension for different directions Back, UI, etc.
At the moment, one case of the UI part from the test task has been implemented
to run tests of all modules, you need to run the command:

    mvn clean test                  

To run a single module:

    mvn clean test -pl=${write-module-name-here}
    
The ``maven-dependency-plugin`` plugin used to keep pom dependencies clean
You can start the check separately with the command:

    mvn clean compile test-compile dependency:analyze-only

        