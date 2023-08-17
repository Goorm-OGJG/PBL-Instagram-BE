#!/bin/sh


JAR_FILE=$(ls /app/*.jar | head -n 1)

java -jar $JAR_FILE