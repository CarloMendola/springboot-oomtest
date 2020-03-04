FROM registry.access.redhat.com/redhat-openjdk-18/openjdk18-openshift:latest

WORKDIR /deployments

COPY ./application.* ./data
COPY ./log4j* ./data
COPY ./*.jar ./

EXPOSE 8080

CMD java $JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Dspring.config.location=/deployments/data/application.yaml -Dlogging.config=/deployments/data/log4j2.yaml -jar /deployments/*.jar

