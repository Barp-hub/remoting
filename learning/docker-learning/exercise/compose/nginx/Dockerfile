FROM nginx:1.13-alpine
MAINTAINER michael zhujun@qbb6.com

WORKDIR /home

RUN rm -rf /etc/nginx/conf.d/default.conf

COPY default.conf /etc/nginx/conf.d

RUN apk update && apk add curl

HEALTHCHECK --interval=30s --timeout=2s --retries=6 CMD curl -fs http://localhost/ || exit 1

EXPOSE 80

CMD ["nginx", "-c", "/etc/nginx/nginx.conf", "-g", "daemon off;"]
