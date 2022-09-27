#!/bin/bash
env=cc.renx
groject=uu

version=`cat  ./src/main/java/cc/renx/uu/UU.java |grep -oP '(?<= version = ").*(?=";)'`

echo "-git pull"
git pull
if [ $? -ne 0 ]
then
	echo -e "\033[31mfail: git pull失败\033[0m"
	exit 1
fi
echo

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
sed -i "0,/^\(\s*\)public static String version = \".*\";$/s//\1public static String version = \"$newVersion\";/" ./src/main/java/cc/renx/uu/UU.java
###########################################

echo "-mvn -q clean install"
mvn -q clean install
echo

echo "-rename package"
packageName=$env-$groject-$newVersion.jar
mv target/*.jar target/$packageName
echo

if [ ! -f "./target/$packageName" ];then
  echo -e "\033[31mfail: 打包失败，请检查\033[0m"
  exit 1
fi

###########################################
echo "-git status"
git status
echo

echo "-git rm -r --cached ."
git rm -r --cached .
echo

echo "-git add"
git add .
echo

echo "-git commit"
git commit -am "$newVersion"
echo

git tag -a "$newVersion" -m "$newVersion"

echo "-git push"
git push
echo


echo "-git status"
result=`git status`
echo $result
ck=$(echo $result | grep "nothing to commit, working tree clean")
if [[ "$ck" = "" ]]
then 
	echo -e "\033[31mfail: git有未提交的内容，请检查。\033[0m"
	exit 1
fi

ck=$(echo $result | grep "Your branch is up to date with")
if [[ "$ck" = "" ]]
then
	echo -e "\033[31mfail: git push失败，请检查。\033[0m"
	exit 1
fi
echo
###########################################

echo $packageName
echo -e  "\033[32msuccess\033[0m"