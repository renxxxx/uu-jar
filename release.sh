#!/bin/bash
domain=renx
project=uu

versionMsg=$1
if [ -z "$versionMsg" ]; then
 versionMsg='更新'
fi

version=`cat  ./src/main/java/renx/uu/UU.java |grep -oP '(?<= version = ").*(?=";)'`
echo current version: $version

read -p "Enter new version: " newVersion

if [[ "$newVersion" = "" ]]
then 
	newVersion=$version
fi

echo new version: $newVersion
sed -i "0,/^\(.*\)<version>.*<\/version>\(.*\)$/s//\1<version>$newVersion<\/version>/" ./pom.xml
sed -i "0,/^\(.*\)public static String version = ".*";\(.*\)$/s//\1public static String version = \"$newVersion\";/" ./src/main/java/renx/uu/UU.java
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
commitId=`git rev-parse --short HEAD`
date=`date +%Y%m%d%H%M%S`
packageName=$domain-$project-$version-$date-$commitId.jar
cp target/$project-$version.war target/$packageName
echo

echo success