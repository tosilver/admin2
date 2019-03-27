#!/bin/sh
echo ************************** running *************************
mvn clean tomcat7:run -e -Dmaven.test.skip=true