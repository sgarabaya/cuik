FROM alpine:latest AS builder

RUN apk update
RUN apk add openjdk25-jdk

COPY . .

RUN chmod +x gradlew
RUN ./gradlew build
RUN ./gradlew shadowJar

FROM alpine:latest

RUN apk update
RUN apk add openjdk25-jre

COPY --from=builder cuik.jar .

EXPOSE 8080 8080

CMD java -jar cuik.jar
