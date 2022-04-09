FROM ubuntu:20.04 

# default user 
ENV USER ssafy 

# packages install 
RUN apt-get update && apt-get upgrade -y 
RUN apt-get install -y sudo vim net-tools ssh openssh-server openjdk-8-jdk-headless

# Access Option 
RUN sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config
RUN sed -i 's/UsePAM yes/#UserPAM yes/g' /etc/ssh/sshd_config 

#user add & set 
RUN groupadd -g 999 $USER 
RUN useradd -m -r -u 999 -g $USER $USER 

RUN sed -ri '20a'$USER' ALL=(ALL) NOPASSWD:ALL' /etc/sudoers 

#set root & user passwd 
RUN echo 'root:root' | chpasswd 
RUN echo $USER':ssafy' | chpasswd 

# java 환경변수 
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64 

# 한글 인코딩
ENV LANG ko_KR.utf8
ENV LC_ALL ko_KR.utf8

ENTRYPOINT sudo service ssh restart && bash 

# USER $USER

# ARG JAR_FILE=target/BackEnd-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} BackEnd-0.0.1-SNAPSHOT.jar
# COPY python python
# ENTRYPOINT ["java", "-jar", "/BackEnd-0.0.1-SNAPSHOT.jar"]
