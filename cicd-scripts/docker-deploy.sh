#!/bin/bash
git -C /home/katea-chatboot/katea-chatbot/ pull origin master
docker stop katea-server && docker rm katea-server && docker image rm katea-server
docker stop katea-ui && docker rm katea-ui && docker image rm katea-ui
docker stop katea-recomandation-engine && docker rm katea-recomandation-engine && docker image rm katea-recomandation-engine
docker build -t katea-server /home/katea-chatboot/katea-chatbot/katea-server/
docker build -t katea-ui /home/katea-chatboot/katea-chatbot/ui/
docker build -t katea-recomandation-engine /home/katea-chatboot/katea-chatbot/recommendation-engine/
docker run --name katea-server -v /etc/google-auth:/etc/google-auth -v /etc/letsencrypt:/etc/letsencrypt -p 8080:8080 -p 8443:8443 -d katea-server
docker run --name katea-ui -v /etc/letsencrypt:/etc/letsencrypt -p 5000:5000 -d katea-ui
docker run --name katea-recomandation-engine -e NODE_ENV='production' -v /etc/google-auth:/etc/google-auth -v /etc/letsencrypt:/etc/letsencrypt -p 3000:3000 -d katea-recomandation-engine
