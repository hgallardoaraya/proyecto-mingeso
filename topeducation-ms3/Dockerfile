FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} topeducation-ms3.jar
ENTRYPOINT ["java","-jar","/topeducation-ms3.jar"]