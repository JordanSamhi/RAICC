for app in /home/raicc/git/RAICC/artefacts/droidbench/apks/*.apk
do
	./launch_iccta.sh -a $app -p ~/git/android-platforms/ -d cc
done
