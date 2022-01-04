#!/bin/bash
group=renx
project=uu

versionMsg=$1
if [ -z "$versionMsg" ]; then
 versionMsg='更新'
fi

echo "-gen version"
date=`date +%y%m%d%H`
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

echo "-rename package"
commitid=`git rev-parse --short HEAD`
packagename=$group-$project-jar-$version-$commitid.war
cp target/$project-$version.jar target/$packagename
echo

echo success