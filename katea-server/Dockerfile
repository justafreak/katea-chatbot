FROM gradle:5.6.2-jdk11

WORKDIR app

WORKDIR /app
COPY --from=0 /app/myProject /app

USER root                # This changes default user to root
RUN chown -R gradle /app # This changes ownership of folder
USER gradle              # This changes the user back to the default user "gradle"

RUN ./gradlew build --stacktrace

COPY build/libs/katea-server*.jar ./katea-server.jar

CMD [ "java", "jar", "katea-server.jar" ]
