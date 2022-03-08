ARG BUILD_HOME=/app

FROM gradle:jdk17-alpine as build-image
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME/
COPY apigateway $APP_HOME/apigateway
RUN gradle --no-daemon build -p ./apigateway

FROM openjdk:17-alpine
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/apigateway/build/libs/apigateway.jar apigateway.jar
ENTRYPOINT java -jar apigateway.jar