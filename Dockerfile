FROM openjdk:8-alpine
RUN mkdir /app
COPY ./build/libs/dama-all.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "dama-all.jar"]