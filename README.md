# RAICC
Reveal Atypical Inter-Component Communication

In this repository, we host the necessary artefacts for reproducing our study.

## Publication

The preprint of the paper is currently hosted on arXiv website at: https://arxiv.org/abs/2012.09916

### Abstract

Inter-Component Communication (ICC) is a key mechanism in Android. It enables developers to compose rich functionalities and explore reuse within and across apps. Unfortunately, as reported by a large body of literature, ICC is rather "complex and largely unconstrained", leaving room to a lack of precision in apps modeling. To address the challenge of tracking ICCs within apps, state of the art static approaches such as Epicc, IccTA and Amandroid have focused on the documented framework ICC methods (e.g., startActivity) to build their approaches. In this work we show that ICC models inferred in these state of the art tools may actually be incomplete: the framework provides other atypical ways of performing ICCs. To address this limitation in the state of the art, we propose RAICC a static approach for modeling new ICC links and thus boosting previous analysis tasks such as ICC vulnerability detection, privacy leaks detection, malware detection, etc. We have evaluated RAICC on 20 benchmark apps, demonstrating that it improves the precision and recall of uncovered leaks in state of the art tools. We have also performed a large empirical investigation showing that Atypical ICC methods are largely used in Android apps, although not necessarily for data transfer. We also show that RAICC increases the number of ICC links found by 61.6% on a dataset of real-world malicious apps, and that RAICC enables the detection of new ICC vulnerabilities. 

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

Options:

* ```-a``` : The path to the APK to process.
* ```-cp``` : The path to Android platofrms folder.
* ```-model``` : The path to RAICC's COAL models folder.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

## Contact

For any question regarding this study, please contact us at:
[Jordan Samhi](mailto:jordan.samhi@uni.lu)
