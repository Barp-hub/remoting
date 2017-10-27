FROM andreptb/oracle-java:1.8.0_112

MAINTAINER michael zhujun@qbb6.com

WORKDIR /home

COPY spring-boot.jar /home

VOLUME ["/log"]

EXPOSE 8080

RUN apk update && apk add curl

HEALTHCHECK --interval=30s --timeout=2s --retries=6 CMD curl -fs http://localhost:8080/ || exit 1

CMD exec java -jar /home/spring-boot.jar
