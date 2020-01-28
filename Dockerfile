# syntax=docker/dockerfile:experimental
FROM --platform=$TARGETPLATFORM alpine
ARG TARGETPLATFORM
RUN echo "I'm building for $TARGETPLATFORM"

FROM ${TARGETPLATFORM}maven:3.6.3-jdk-11-slim as maven
WORKDIR /coffee_pot
COPY ./pom.xml ./pom.xml
RUN ["/bin/bash", "-c", "mvn dependency:go-offline -B"]
COPY ./src ./src

RUN ["/bin/bash", "-c", "mvn package && cp target/coffee_pot.jar coffee_pot.jar"]

FROM ${TARGETPLATFORM}openjdk:11-jre
WORKDIR /coffee_pot
COPY --from=maven /coffee_pot/coffee_pot.jar ./coffee_pot.jar
COPY docker/start.sh start.sh
COPY docker/wait-for-it.sh wait-for-it.sh
RUN ["/bin/bash", "-c", "touch coffee_pot.jar"]
EXPOSE 8080
ENTRYPOINT ["./start.sh"]
