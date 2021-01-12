#!/bin/bash

print_info () {
    echo "[*] $1"
}

while getopts a:p:d: option
do
    case "${option}"
        in
        a) APP_PATH=${OPTARG};;
        p) ANDROID_JAR=${OPTARG};;
        d) DBNAME=${OPTARG};;
    esac
done

echo "IC3 launcher"
echo
if [ -z "$APP_PATH" ]
then
    read -p "Path of APK: " APP_PATH
fi
if [ -z "$ANDROID_JAR" ]
then
    read -p "Path to android.jar: " ANDROID_JAR
fi
if [ -z "$DBNAME" ]
then
    read -p "Name of database: " DBNAME
fi

APP_BASENAME_APK=$(basename $APP_PATH .apk)
APP_BASENAME=$(basename $APP_PATH)
PATH_LOGS=ic3_output

if [ ! -d $PATH_LOGS ]
then
    mkdir -p $PATH_LOGS
fi

print_info "Running IC3"
before_ic3=$(date +%s)
./execute_with_limit_time.sh java -jar ic3.jar -apk $APP_PATH -cp $ANDROID_JAR -db soot-infoflow-android-iccta/cc.properties -dbname $DBNAME -model ../../src/main/resources/models/ &> $PATH_LOGS/$APP_BASENAME-ic3.txt
after_ic3=$(date +%s)
time_ic3=$(($after_ic3-$before_ic3))
echo "IC3 time: $time_ic3">>$PATH_LOGS/$APP_BASENAME-ic3.txt

print_info "$APP_BASENAME successfully analyzed."
print_info "Check results in $PATH_LOGS"
rm -rf $APP_BASENAME
