# 1. UPDATE PROJECT
```
cd /home/katea-chatboot/katea-chatbot/ &^ git pull
```
# 2. STOP EXISTING CONTINERS DELETE EXISTING IMAGES
```
docker stop katea-server && docker rm katea-server && docker image rm katea-server
docker stop katea-ui && docker rm katea-ui && docker image rm katea-ui
```
# 3. BUILD IMAGES
```
docker build -t katea-server /home/katea-chatboot/katea-chatbot/katea-server/
docker build -t katea-ui /home/katea-chatboot/katea-chatbot/ui/
```
# 4. RUN CONTAINERS
```
docker run --name katea-server -v /etc/letsencrypt:/etc/letsencrypt -p 8080:8080 -p 8443:8443 -d katea-server
docker run --name katea-ui -v /etc/letsencrypt:/etc/letsencrypt -p 5000:5000 -d katea-ui
```
