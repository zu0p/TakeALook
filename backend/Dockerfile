# java version
FROM openjdk:8-jdk-alpine

# set jar_file
ARG JAR_FILE=./build/libs/*.jar


# application port
EXPOSE 8080

# add application file into docker container
ADD ${JAR_FILE} app.jar

# run
ENTRYPOINT ["java", "-jar", "app.jar"]

