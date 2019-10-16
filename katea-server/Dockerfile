FROM gradle:5.6.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build --no-daemon

FROM gradle:5.6.2-jdk11

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/strikers-server.jar

ENTRYPOINT ["java","-jar","/app/strikers-server.jar"]