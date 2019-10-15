FROM gradle:5.6.2-jdk11

WORKDIR app

COPY .  /app

USER root
RUN chown -R gradle /app
USER gradle

RUN ./gradlew build --stacktrace

CMD [ "java", "-jar", "build/libs/katea-server-0.0.1-SNAPSHOT.jar" ]
