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

echo "IccTA launcher"
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

cp $APP_PATH ./soot-infoflow-android-iccta &> /dev/null
cd soot-infoflow-android-iccta

APP_BASENAME_APK=$(basename $APP_PATH .apk)
APP_BASENAME=$(basename $APP_PATH)
DARE_RESULTS=dareResults
RETARGETED_PATH=$DARE_RESULTS/retargeted
PATH_LOGS=logs

if [ ! -d $PATH_LOGS ]
then
    mkdir $PATH_LOGS
fi

print_info "Running IC3"
before_ic3=$(date +%s)
java -jar ic3.jar -apk $APP_BASENAME -cp $ANDROID_JAR -db cc.properties -dbname $DBNAME &> $PATH_LOGS/$APP_BASENAME-ic3.txt
after_ic3=$(date +%s)
time_ic3=$(($after_ic3-$before_ic3))
echo "IC3 time: $time_ic3">>$PATH_LOGS/$APP_BASENAME-ic3.txt

print_info "Executing IccTA"
before_iccta=$(date +%s)
java -jar iccta.jar $APP_BASENAME $ANDROID_JAR $DBNAME &> $PATH_LOGS/$APP_BASENAME-iccta.txt
after_iccta=$(date +%s)
time_iccta=$(($after_iccta-$before_iccta))
echo "ICCTA time: $time_iccta">>$PATH_LOGS/$APP_BASENAME-iccta.txt

print_info "$APP_BASENAME successfully analyzed."
print_info "Check results in soot-infoflow-android-iccta/$PATH_LOGS/"
rm -rf $APP_BASENAME
