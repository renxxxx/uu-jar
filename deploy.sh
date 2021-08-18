#!/bin/bash

versionMsg=$1
if [ -z "$versionMsg" ]; then
 versionMsg='更新'
fi

echo "-gen version"
date=`date +%y%m%d`
version=$date
echo version: $version
sed -i "0,/^\(.*\)<version>.*<\/version>\(.*\)$/s//\1<version>$version<\/version>/" ./pom.xml
sed -i "s/public static String version = \".*\"/public static String version = \"$version\"/g" ./src/main/java/renx/uu/UU.java
echo

echo "-git add"
git add .
echo

echo "-git commit"
git commit -am "$versionMsg"
echo

echo "-git pull"
git pull
echo

echo "-git push"
git push
echo

echo "-mvn -q clean install"
mvn -q clean install
echo

echo success