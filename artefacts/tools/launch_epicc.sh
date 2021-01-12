appName=$1
appBaseName=$(basename $appName .apk)
dareResults=dareResults
retargetedPath=$dareResults/retargeted

echo "Retargeting $appName"
./execute_with_limit_time.sh ./dare-1.1.0-linux/dare -d ../$dareResults ../$appName &> /dev/null

echo "Running EPICC"

./execute_with_limit_time.sh java -jar epicc.jar -android-directory $retargetedPath/$appBaseName -apk $appName -cp android.jar -icc-study epicc_output >> epicc_output/$appBaseName.txt
rm -rf $retargetedPath/$appBaseName
