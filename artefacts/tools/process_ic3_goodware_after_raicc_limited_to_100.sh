while read sha
do
	echo "Downloading $sha..."
	./download_app.sh $sha &> /dev/null
	./launch_raicc.sh $sha.apk
	if [ -f "$sha.apk" ] 
	then
		rm raicc_output/*.csv
		./launch_ic3.sh -a raicc_output/$sha.apk -p ~/git/android-platforms/ -d cc
		rm raicc_output/$sha.apk
	fi
done < /home/raicc/git/RAICC/artefacts/hashes/minimal_datasets/ic3_benign_100.txt
