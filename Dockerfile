FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/ibm-atm-challenge-1.0.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -XX:+UseSerialGC -Xverify:none -XX:+TieredCompilation -XX:TieredStopAtLevel=1","-jar","/app.jar"]