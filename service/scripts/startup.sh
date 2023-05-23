#!/bin/bash -e

export JAVA_OPTIONS="-XX:MaxMetaspaceSize=128m -XX:CompressedClassSpaceSize=32m -XX:ReservedCodeCacheSize=64m -XX:+UnlockExperimentalVMOptions -Xmx512M -Xms512M -Xss256k $EXTRA_JAVA_OPTIONS"
export GC_OPTIONS="-XX:+UseG1GC"

echo "Attempting to start the App"

exec java $JAVA_OPTIONS $GC_OPTIONS -jar /app/service.jar
