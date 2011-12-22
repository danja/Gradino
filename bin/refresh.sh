#!/bin/sh 
cd ../www
svn update
cd ../src
svn update
cd ../bin
ant compile
./start.sh