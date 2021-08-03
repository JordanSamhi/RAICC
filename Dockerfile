FROM ubuntu:18.04
ARG android_platforms="android-platforms"
ARG jdk_installer="jdk-8u301-linux-x64.tar.gz"

# Install Requirements
RUN sed -i 's/archive.ubuntu.com/mirror.kakao.com/g' /etc/apt/sources.list
RUN apt-get update && apt-get install -y vim git maven wget unzip
RUN apt-get install -y libaio1 libaio-dev libnuma-dev numactl libc6-dev
RUN apt-get install -y mysql-server-5.7 mysql-server-core-5.7 mysql-client-5.7 mysql-client-core-5.7

# Reqire android-platforms, jdk installer
RUN mkdir -p /home/raicc/git
COPY $android_platforms /home/raicc/git/android-platforms
COPY $jdk_installer /home/raicc

# Make home directory
WORKDIR /home/raicc
ENV HOME=/home/raicc

# Install jdk 1.8
RUN mkdir jdk
RUN tar -xvzf $jdk_installer -C jdk --strip-components 1
RUN mv jdk /usr/local/jdk
RUN chmod 755 /usr/local/jdk
RUN ln -s /usr/local/jdk /usr/local/java

# Set ENV for jdk
ENV JAVA_HOME=/usr/local/java
ENV CLASSPATH=.:$JAVA_HOME/lib/tools.jar
ENV PATH=$PATH:$JAVA_HOME/bin

# For alternate javac, java, set high priorities
RUN update-alternatives --install /usr/bin/javac javac $JAVA_HOME/bin/javac 9999
RUN update-alternatives --install /usr/bin/java java $JAVA_HOME/bin/java 9999

# Clone RAICC git
WORKDIR /home/raicc/git
RUN git clone https://github.com/JordanSamhi/RAICC

# Build RAICC
WORKDIR RAICC
RUN ./build.sh

# Add raicc user
WORKDIR artefacts/tools
ADD add_user.sql .

# Start entrypoint with step 6
ENTRYPOINT service mysql restart && mysql -u root < add_user.sql && /bin/bash && ./configure
