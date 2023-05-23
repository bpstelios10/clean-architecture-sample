FROM openjdk:11
WORKDIR /

EXPOSE 8080 8080

COPY scripts/startup.sh /
ADD build/libs/service.jar /app/service.jar

CMD ["/startup.sh"]
