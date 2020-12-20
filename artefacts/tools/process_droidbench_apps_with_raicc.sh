for app in /home/raicc/git/RAICC/artefacts/droidbench/apks/*.apk
do
	./launch_raicc.sh $app
done
rm raicc_output/*.csv
