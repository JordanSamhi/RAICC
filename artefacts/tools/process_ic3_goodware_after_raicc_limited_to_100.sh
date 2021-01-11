i=0
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
	let "i+=1"
	if [ $i -eq 100 ]
	then
		break
	fi
done < /home/raicc/git/RAICC/artefacts/hashes/5k_ic3_raicc_benign_section_V_D_1.txt
