#!/bin/bash
group=renx
artifact=uu

version=`cat  ./src/main/java/renx/uu/UU.java |grep -oP '(?<= version = ").*(?=";)'`
echo current version: $version

read -p "Enter new version: " newVersion

if [[ "$newVersion" = "" ]]
then 
	newVersion=$version
fi

sed -i "0,/^\(.*\)<groupId>.*<\/groupId>\(.*\)$/s//\1<groupId>$group<\/groupId>/" ./pom.xml
sed -i "0,/^\(.*\)<artifactId>.*<\/artifactId>\(.*\)$/s//\1<artifactId>$artifactId<\/artifactId>/" ./pom.xml
sed -i "0,/^\(.*\)<version>.*<\/version>\(.*\)$/s//\1<version>$newVersion<\/version>/" ./pom.xml
#sed -i "0,/^\(\s*\)public static String version = \".*\";$/s//\1public static String version = \"$newVersion\";/" ./src/main/java/renx/uu/UU.java

echo "-mvn -q clean install"
mvn -q clean install
echo

echo "-git status"
git status
echo

echo "-git add"
git add .
echo

echo "-git commit"
git commit -am "$version"
echo

echo "-git pull"
git pull
echo

echo "-git push"
git push
echo

echo "-rename package"
packageName=$group-$artifact-$newVersion.jar
mv target/*.jar target/$packageName
echo

echo success