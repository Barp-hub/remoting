FROM nginx:latest
MAINTAINER michael zhujun@qbb6.com

WORKDIR /home

RUN rm -rf /etc/nginx/conf.d/default.conf

COPY default.conf /etc/nginx/conf.d

EXPOSE 80

CMD ["nginx", "-c", "/etc/nginx/nginx.conf", "-g", "daemon off;"]
