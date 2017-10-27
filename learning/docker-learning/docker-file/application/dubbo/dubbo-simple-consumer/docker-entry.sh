#!/bin/bash

nohup java -jar /home/hello-dubbo-consumer.jar 1>/dev/null 2>/dev/null &

while true; 
    do echo "I'm working..."; 
    sleep 60;
done