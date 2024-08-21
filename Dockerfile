#FROM amazoncorretto:21-alpine
#
#WORKDIR /hotel
#
#COPY rest/target/hotel.jar hotel.jar
#EXPOSE 8080
#
#CMD ["java", "-jar", "hotel.jar"]

FROM amazoncorretto:21-alpine
WORKDIR /app

COPY rest/target/*.jar rest.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","rest.jar"]

