FROM openjdk:8
VOLUME /tmp
ADD target/graphqlshopdemo-0.0.1-SNAPSHOT.jar graphqlshopdemo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","graphqlshopdemo-0.0.1-SNAPSHOT.jar"]