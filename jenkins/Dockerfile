FROM ubuntu:16.04

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common
RUN add-apt-repository ppa:openjdk-r/ppa
RUN apt-get install -y openjdk-8-jdk

ADD run.sh /run.sh
RUN chmod +x /run.sh
ADD *.war /lolock-server.war

ENTRYPOINT ["/run.sh"]