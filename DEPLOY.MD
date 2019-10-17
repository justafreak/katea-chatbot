
We recomand to use ```./deploy-docker.sh``` file from ```katea_chatboot@strikes.ml:~/```. If that isn't work then you can try to deploy the project manually following this steps:

## 1. UPDATE PROJECT
```
git -C /home/katea-chatboot/katea-chatbot/ pull origin master
```
## 2. STOP EXISTING CONTINERS DELETE EXISTING IMAGES
```
docker stop katea-server && docker rm katea-server && docker image rm katea-server
docker stop katea-ui && docker rm katea-ui && docker image rm katea-ui
docker stop katea-recomandation-engine && docker rm katea-recomandation-engine && docker image rm katea-recomandation-engine
```
## 3. BUILD IMAGES
```
docker build -t katea-server /home/katea-chatboot/katea-chatbot/katea-server/
docker build -t katea-ui /home/katea-chatboot/katea-chatbot/ui/
docker build -t katea-recomandation-engine /home/katea-chatboot/katea-chatbot/recommendation-engine/
```
## 4. RUN CONTAINERS
```
docker run --name katea-server -v /etc/letsencrypt:/etc/letsencrypt -p 8080:8080 -p 8443:8443 -d katea-server
docker run --name katea-ui -v /etc/letsencrypt:/etc/letsencrypt -p 5000:5000 -d katea-ui
docker run --name katea-recomandation-engine -v /etc/letsencrypt:/etc/letsencrypt -p 3000:3000 -d katea-recomandation-engine
```
