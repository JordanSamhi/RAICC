mvn clean install:install-file -Dfile=libs/ic3-0.2.1-full.jar -DgroupId=edu.psu.cse.siis -DartifactId=ic3 -Dversion=0.2.1 -Dpackaging=jar
mvn clean install:install-file -Dfile=libs/preprocessIntentSender-0.1.jar -DgroupId=lu.uni.trux -DartifactId=preprocessIntentSender -Dversion=0.1 -Dpackaging=jar
export MAVEN_OPTS=-Xss32m
mvn clean install
echo "RAICC installed"
cp target/RAICC-0.1-jar-with-dependencies.jar artefacts/tools/raicc.jar
echo "RAICC copied"
