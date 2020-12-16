# RAICC
Reveal Atypical Inter-Component Communication

## Getting started

### Downloading the tool

<pre>
git clone https://github.com/JordanSamhi/RAICC.git
</pre>

### Installing the tool

<pre>
cd RAICC
mvn clean install:install-file -Dfile=libs/ic3-0.2.1-full.jar -DgroupId=edu.psu.cse.siis -DartifactId=ic3 -Dversion=0.2.1 -Dpackaging=jar
mvn clean install:install-file -Dfile=libs/preprocessIntentSender-0.1.jar -DgroupId=lu.uni.trux -DartifactId=preprocessIntentSender -Dversion=0.1 -Dpackaging=jar
mvn clean install
</pre>

### Issues

If you stumble upon a stack overflow error while building RAICC, increase memory available with this command:

<pre>
export MAVEN_OPTS=-Xss32m
</pre>

Then, try to rebuild.

### Using the tool

<pre>
java -jar RAICC/target/RAICC-0.1-jar-with-dependencies.jar <i>options</i>
</pre>

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details
