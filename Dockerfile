# syntax=docker/dockerfile:experimental
FROM --platform=$BUILDPLATFORM openjdk:11-jre
WORKDIR /coffee_pot
COPY target/coffee_pot.jar ./coffee_pot.jar
COPY docker/start.sh start.sh
COPY docker/wait-for-it.sh wait-for-it.sh
RUN ["/bin/bash", "-c", "touch coffee_pot.jar"]
EXPOSE 8080
ENTRYPOINT ["./start.sh"]
