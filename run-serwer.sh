#!/bin/bash

git pull origin

cd Serwer

cd asystent

mvn clean install
mvn package
mvn spring-boot:run