FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/time-blade-rider-0.0.1-SNAPSHOT-standalone.jar /time-blade-rider/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/time-blade-rider/app.jar"]
