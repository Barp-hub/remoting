FROM andreptb/oracle-java:1.8.0_112

MAINTAINER michael zhujun@qbb6.com

WORKDIR /home

ADD dubbo-monitor dubbo-monitor

VOLUME ["/log"]

EXPOSE 8888 7070

WORKDIR /home/dubbo-monitor/dubbo-monitor-simple-2.8.4

RUN apk update && apk add bash

RUN chmod +x ./bin/*.sh

CMD ./bin/start.sh
