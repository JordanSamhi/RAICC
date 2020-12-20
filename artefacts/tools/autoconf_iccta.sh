#!/bin/bash

DATABASE_NAME="cc"

end_program () {
    echo "[!] $1"
    echo "End of program."
    exit 1
}

print_info () {
    echo "[*] $1"
}

check_return () {
    if [ $1 -ne 0 ]
    then
        end_program "$2"
    else
        if [ "$3" ]
        then
            print_info "$3"
        fi
    fi
}

while getopts u:p:j: option
do
    case "${option}"
        in
        u) USERNAME=${OPTARG};;
        p) PASSWORD=${OPTARG};;
        j) PLATFORMS=${OPTARG};;
    esac
done

echo "Auto-configuration of IccTA"
if [ -z "$USERNAME" ]
then
    echo
    read -p "Database username: " USERNAME
fi
if [ -z "$PASSWORD" ]
then
    read -sp "Database password: " PASSWORD
fi
if [ -z "$PLATFORMS" ]
then
    echo
    read -p "Path to your platforms folder: " PLATFORMS
fi
echo

rm -rf soot-infoflow-android-iccta &> /dev/null
print_info "Cloning IccTA repository..."
git clone https://github.com/lilicoding/soot-infoflow-android-iccta.git &> /dev/null
check_return $? "Something went wrong while cloning IccTA repository." "IccTA repository cloned."
cd soot-infoflow-android-iccta

awk 'NR==211 {$0=") ENGINE=InnoDB  CHARACTER SET utf8;"} 1' res/schema > res/tmp && mv res/tmp res/schema &> /dev/null
check_return $? "Something went wront with awk command." "Schema updated."

mysql -u $USERNAME -p$PASSWORD -e "drop database $DATABASE_NAME" &> /dev/null
mysql -u $USERNAME -p$PASSWORD -e "create database $DATABASE_NAME" &> /dev/null
check_return $? "Something went wrong while creating database." "Database successfully created."

mysql -u $USERNAME -p$PASSWORD $DATABASE_NAME < res/schema &> /dev/null
check_return $? "[!] Something went wrong while importing schema" "Schema successfully imported."

print_info "Checking files..."
echo "iccProvider=ic3" > res/iccta.properties
check_return $? "Something went wrong with res/iccta.properties" "res/iccta.properties OK"

sed -i "s/<username>.*<\/username>/<username>$USERNAME<\/username>/" res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (res/jdbc.xml)."
sed -i "s/<password>.*<\/password>/<password>$PASSWORD<\/password>/" res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (res/jdbc.xml)."
sed -i "s/<name>.*<\/name>/<name>$DATABASE_NAME<\/name>/" res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (res/jdbc.xml)." "res/jdbc.xml OK"

sed -i "s/<username>.*<\/username>/<username>$USERNAME<\/username>/" release/res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (release/res/jdbc.xml)."
sed -i "s/<password>.*<\/password>/<password>$PASSWORD<\/password>/" release/res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (release/res/jdbc.xml)."
sed -i "s/<name>.*<\/name>/<name>$DATABASE_NAME<\/name>/" release/res/jdbc.xml &> /dev/null
check_return $? "Something went wrong with sed (release/res/jdbc.xml)." "release/res/jdbc.xml OK"

sed -i "s/android_jars=.*/android_jars=${PLATFORMS////\\/}/" release/res/iccta.properties &> /dev/null
check_return $? "Something went wrong with sed (release/res/iccta.properties)." "release/res/iccta.properties OK"

wget https://github.com/JordanSamhi/Tools/raw/master/ic3.jar &> /dev/null
check_return $? "Something went wrong while downloading IC3." "IC3 downloaded."

wget https://github.com/JordanSamhi/Tools/raw/master/iccta.jar &> /dev/null
check_return $? "Something went wrong while downloading IccTA." "IccTA downloaded."

echo "user=$USERNAME" > cc.properties
echo "password=$PASSWORD" >> cc.properties
echo "characterEncoding=ISO-8859-1" >> cc.properties
echo "useUnicode=true" >> cc.properties
print_info "cc.properties successully created."

echo "Auto-configuration of IccTA successful"
