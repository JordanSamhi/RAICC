# RAICC
Reveal Atypical Inter-Component Communication

In this repository, we host the necessary artefacts for reproducing our study.

[![DOI](https://zenodo.org/badge/254038125.svg)](https://zenodo.org/badge/latestdoi/254038125)

:warning: To reproduce the experiments of the paper, please use version 1.0.


## Publication

The paper describing the approach for RAICC is in the proceedings of the 43rd International Conference on Software Engineering (ICSE) 2021.

The paper is available at: https://ieeexplore.ieee.org/document/9402001

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
sh build.sh
</pre>

### Using the tool

<pre>
java -jar RAICC/target/RAICC-2.0-jar-with-dependencies.jar <i>options</i>
</pre>

Options:

* ```-a``` : The path to the APK to process.
* ```-p``` : The path to Android platofrms folder.
* ```-o``` : Sets the output folder.
* ```-r``` : Prints raw results.
* ```-to``` : Sets the timeout.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

## Contact

For any question regarding this study, please contact us at:
[Jordan Samhi](mailto:jordan.samhi@uni.lu)
