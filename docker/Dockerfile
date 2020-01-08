FROM openjdk:11-jre
WORKDIR /coffee_pot
VOLUME ["/coffee_pot"]
COPY ../target/coffee_pot.jar coffee_pot.jar
COPY start.sh start.sh
COPY wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch coffee_pot.jar'
ENTRYPOINT ["./start.sh"]
