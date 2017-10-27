FROM andreptb/oracle-java:1.8.0_112

MAINTAINER michael zhujun@qbb6.com

WORKDIR /home

RUN apk update && apk add bash
COPY hello-dubbo-consumer.jar /home
ADD docker-entry.sh docker-entry.sh

VOLUME ["/log"]

RUN chmod +x ./docker-entry.sh

CMD ./docker-entry.sh
