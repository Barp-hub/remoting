FROM andreptb/oracle-java:1.8.0_112

MAINTAINER michael zhujun@qbb6.com

RUN apk update && apk add bash

WORKDIR /home

VOLUME ["/log"]

ADD apache-tomcat-8.5.4 apache-tomcat-8.5.4
ADD docker-entry.sh docker-entry.sh

EXPOSE 8080

RUN chmod +x ./apache-tomcat-8.5.4/bin/*.sh
RUN chmod +x ./docker-entry.sh

CMD ./docker-entry.sh
