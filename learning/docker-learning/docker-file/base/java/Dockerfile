FROM centos:7
MAINTAINER michael

RUN echo "Asia/shanghai" > /etc/timezone;

COPY server-jre-8u131-linux-x64.tar.gz /usr/local/

RUN cd /usr/local/ && \
    tar -zxvf server-jre-8u131-linux-x64.tar.gz && \
    rm -rf server-jre-8u131-linux-x64.tar.gz

ENV JAVA_HOME=/usr/local/jdk1.8.0_131
ENV CLASSPATH=$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH=$JAVA_HOME/bin:$PATH

CMD /bin/bash
