i=0
while read sha
do
	echo "Downloading $sha..."
	./download_app.sh $sha &> /dev/null
	./launch_epicc.sh $sha.apk
	rm $sha.apk
	let "i+=1"
	if [ $i -eq 100 ]
	then
		break
	fi
done < /home/raicc/git/RAICC/artefacts/hashes/epicc_benign_1000_section_V_D_3.txt
