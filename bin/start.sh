#!/bin/bash

# change to ';' for Cygwin
SEP=':'

# collect all the jar names
for jar in ../lib/**/*.jar
  do
  # Check for no expansion
  [ -e "$jar" ] || break
  #echo "Path: $jar"
  [ "$CP" != "" ] && CP="${CP}${SEP}"
  CP="${CP}$jar"
done

CP=$CP:../classes

echo $CP

## scalac -d ../classes/ -classpath $CP -sourcepath ../src/
# scalac -cp $CP -d ../classes ../src/*.scala

java -cp $CP com.dannyayers.core.Main

