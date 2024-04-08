# Chat Analyzer Bot

Webhook based bot which analyzes which words are commonly used in chat.

The bot handles /stats command to retrieve the most commonly used words. 
For stats a rest endpoint is also available (check /swagger-ui endpoint for details)

## Build and run

The app can be built in three variants: regular java app, native runtime image and docker image (based on regular and native run time)

- Java build and run
```
./gradlew clean build
./gradlew run or java -jar /build/libs/chatanalyzer-1.0-all.jar
```

- Native build and run 
```
./gradlew nativeCompile
./gradlew nativeRun  or ./build/native/nativeCompile/chatanalyzerbot
```

- Docker build and run
```
./gradlew dockerBuild or ./gradlew dockerBuildNative

docker run ...
```
Check build.gradle.kts for image name and tag. check application-cloud.properties for required env_variables to be set. 
Also set WEBHOOK_TOKEN variable


## Improvement

In Redis a collections with word stats also need to contain chat id. 
