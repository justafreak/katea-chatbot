FROM gradle:5.6.2-jdk11

WORKDIR app

COPY build/libs/katea-server*.jar ./katea-server.jar

CMD [ "java", "jar", "katea-server.jar" ]
