FROM openjdk: 11-jre-slim
WORKDIR /movies
COPY target/*.jar /movies/app.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -jar app.jar