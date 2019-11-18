FROM openjdk:8-jre
WORKDIR /coffee_pot
VOLUME ["/coffee_pot"]
COPY maven/coffee_pot.jar coffee_pot.jar
COPY maven/start.sh start.sh
COPY maven/wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch coffee_pot.jar'
ENTRYPOINT ["./start.sh"]
