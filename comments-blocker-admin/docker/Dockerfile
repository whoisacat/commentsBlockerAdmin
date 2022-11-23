FROM openjdk:17.0.1-jdk-slim as blocker_admin_app

RUN apt-get --yes update
RUN apt-get --yes install git
RUN apt-get --yes install curl

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

#ARG JAR_FILE=$PROJECT_DIR/commentsBlockerAdmin/comments-blocker-admin/target/*.jar
COPY ./target/admin-0.0.1.jar $PROJECT_DIR/app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/opt/project/app.jar"]
