FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} topeducation-ms2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/topeducation-ms2-0.0.1-SNAPSHOT.jar"]