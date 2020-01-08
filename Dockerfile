FROM maven:3.6.3-jdk-11-openj9 as maven
WORKDIR /coffee_pot
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src

RUN mvn package && cp target/coffee_pot.jar coffee_pot.jar

FROM openjdk:11-jre
WORKDIR /coffee_pot
COPY --from=maven /coffee_pot/coffee_pot.jar ./coffee_pot.jar
COPY docker/start.sh start.sh
COPY docker/wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch coffee_pot.jar'
EXPOSE 8080
ENTRYPOINT ["./start.sh"]
