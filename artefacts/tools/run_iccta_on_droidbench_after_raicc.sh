for app in raicc_output/*.apk
do
	./launch_iccta.sh -a $app -p ~/git/android-platforms/ -d cc
done
