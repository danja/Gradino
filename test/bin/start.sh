#!/bin/bash

LIB=/home/danny/workspace/gradino/lib
JENA=/home/danny/workspace/gradino/lib/jena
JETTY=/home/danny/workspace/gradino/lib/jetty

CP=$LIB/wrhapi-jetty-0.8-SNAPSHOT.jar:$LIB/org.trialox.triaxrs-0.9-SNAPSHOT.jar:$LIB/wrhapi-jetty-0.8-SNAPSHOT.jar:$LIB/jsr311-api-1.0.jar:$LIB/org.osgi.compendium-4.1.0.jar:$LIB/org.tri\
alox.jaxrs.extensions-0.2.jar:$LIB/wrhapi-0.8-20090421.130602-1.jar:$LIB/jtidy-r820.jar:$LIB/org.osgi.core-4.1.0.jar

CP=$CP:/home/danny/workspace/gradino/classes
CP=$CP:$JENA/arq-extra.jar:$JENA/jena.jar:$JENA/log4j-1.2.12.jar:$JENA/stax-api-1.0.jar:$JENA/arq.jar:$JENA/jenatest.jar:$JENA/lucene-core-2.3.1.jar:$JENA/wstx-asl-3.0.0.jar:$JENA/icu4j_3_4.jar:$JENA/json.jar:$JENA/slf4j-api-1.5.6.jar:$JENA/xercesImpl.jar:$JENA/iri.jar:$JENA/junit-4.5.jar:$JENA/slf4j-log4j12-1.5.6.jar

CP=$CP:../classes:$JETTY/jetty-rewrite-7.0.0.RC5.jar:$JETTY/jetty-ajp-7.0.0.RC5.jar:$JETTY/jetty-security-7.0.0.RC5.jar:$JETTY/jetty-annotations-7.0.0.RC5.jar:$JETTY/jetty-server-7.0.0.RC5.jar:$JETTY/jetty-client-7.0.0.RC5.jar:$JETTY/jetty-servlet-7.0.0.RC5.jar:$JETTY/jetty-continuation-7.0.0.RC5.jar:$JETTY/jetty-servlets-7.0.0.RC5.jar:$JETTY/jetty-deploy-7.0.0.RC5.jar:$JETTY/jetty-util-7.0.0.RC5.jar:$JETTY/jetty-http-7.0.0.RC5.jar:$JETTY/jetty-webapp-7.0.0.RC5.jar:$JETTY/jetty-io-7.0.0.RC5.jar:$JETTY/jetty-xml-7.0.0.RC5.jar:$JETTY/jetty-jmx-7.0.0.RC5.jar:$JETTY/jndi:$JETTY/jetty-jndi-7.0.0.RC5.jar:$JETTY/jetty-plus-7.0.0.RC5.jar:$JETTY/servlet-api-2.5.jar:$JETTY/jetty-policy-7.0.0.RC5.jar

echo $CP

## scalac -d ../classes/ -classpath $CP -sourcepath ../src/
# scalac -cp $CP -d ../classes ../src/*.scala
# scala -cp $CP com.HelloTest

java -cp $CP com.HelloTest

