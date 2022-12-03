FROM openjdk:17.0.1-jdk-slim as blocker_front_app

RUN apt-get --yes update
RUN apt-get --yes install git
RUN apt-get --yes install curl

RUN mkdir /blocker_files
VOLUME /blocker_files

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY ./target/front-0.0.1.jar $PROJECT_DIR/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/app.jar"]
