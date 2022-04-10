FROM openjdk:11.0.7-jdk-slim

WORKDIR /usr/src/app

#COPY build/libs/ReviewzorBack-0.0.1-SNAPSHOT.jar ./reviewzor.jar

#RUN chmod 777 reviewzor.jar

#EXPOSE 8080

#CMD ["java", "-jar", "reviewzor.jar"]

ADD build/libs/ReviewzorBack-0.0.1-SNAPSHOT.jar ./reviewzor.jar

CMD ["java", "-jar", "reviewzor.jar"]

#ENTRYPOINT ["java","-jar","demo.jar"]