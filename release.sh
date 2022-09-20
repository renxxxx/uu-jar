#!/bin/bash
env=renx
groject=uu

version=`cat  ./src/main/java/renx/uu/UU.java |grep -oP '(?<= version = ").*(?=";)'`

echo "-git status"
result=`git status`
echo "$result"
ck=$(echo $result | grep "nothing to commit, working tree clean")
echo

###########################################

echo current version is $version

read -p "Enter new version: " newVersion

if [[ "$newVersion" = "" ]]
then 
	if [[ "$version" = "" ]]
	then 
		newVersion=1.0.0.0
	else
		if [[ "$ck" = "" ]]
		then 
			in=`echo $version | awk -F ''.'' '{printf "%d", length($0)-length($NF)}'`
			l=${#version}
			v=${version:0:$in-1}
			v2=${version:$in-$l}
			newVersion=$v.`expr $v2 + 1`
		else
			newVersion=$version
		fi
	fi
fi

echo current version: $newVersion

echo

###########################################

sed -i "0,/^\(.*\)<groupId>.*<\/groupId>\(.*\)$/s//\1<groupId>$env<\/groupId>/" ./pom.xml
sed -i "0,/^\(.*\)<artifactId>.*<\/artifactId>\(.*\)$/s//\1<artifactId>$groject<\/artifactId>/" ./pom.xml
sed -i "0,/^\(.*\)<version>.*<\/version>\(.*\)$/s//\1<version>$newVersion<\/version>/" ./pom.xml
sed -i "0,/^\(\s*\)public static String version = \".*\";$/s//\1public static String version = \"$newVersion\";/" ./src/main/java/renx/uu/UU.java

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
git commit -am "$newVersion"
echo

echo "-git pull"
git pull
echo

echo "-git push"
git push
echo

echo "-rename package"
packageName=$env-$groject-$newVersion.jar
mv target/*.jar target/$packageName
echo

echo success