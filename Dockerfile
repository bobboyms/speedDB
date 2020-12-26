FROM gradle:6.7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

#FROM openjdk:11-jre-slim

#EXPOSE 8080

#RUN mkdir /app

#COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar

#ENTRYPOINT ["java", "-jar","/app/application.jar"]

# use graalvm image
# replace the occurences of "product" with you jar name
FROM oracle/graalvm-ce:20.3.0-java11

# expose your port, 8080 fo Micronaut applicatoin
#EXPOSE 8080
#RUN mkdir /app

# copy the fat jar
COPY --from=build /home/gradle/src/build/libs/*.jar application.jar
#ENTRYPOINT ["java", "-jar","application.jar"]
RUN gu install native-image
RUN native-image --no-server \
                 --no-fallback \
                 -H:+ReportExceptionStackTraces \
                 -jar application.jar

#CMD ./application
ENTRYPOINT ["./application", "CONTAINER"]