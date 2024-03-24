## Micronaut 4.3.7 Documentation

- [User Guide](https://docs.micronaut.io/4.3.7/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.3.7/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.3.7/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Jib Gradle Plugin](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
# Telegram ChatBot

Follow the instructions in the [Micronaut ChatBot Documentation](https://micronaut-projects.github.io/micronaut-chatbots/latest/guide/) to create a Telegram ChatBot.

Once you have a username and HTTP auth key for your new bot, edit the application config in this project to set the bot username and make up a WEBHOOK_TOKEN so you can ensure it's Telegram that's calling your bot.

This project has a dependency on `micronaut-chatbots-telegram-http` which has added a controller to your application with the path `/telegram`.

When registering your bot with Telegram, you will need to provide the HTTPS URL of your application including this path.
If you are running your application locally, you can use a tool like [ngrok](https://ngrok.com/) to expose your application to the internet.

You can then set up the Telegram webhook by running the following command:

```bash
curl -X POST 'https://api.telegram.org/bot${HTTP_AUTH_KEY}/setWebhook?url=${YOUR_HTTP_TRIGGER_URL}&secret_token=${YOUR_SECRET_TOKEN}'
```

Where HTTP_AUTH_KEY is the key given to you by the BotFather, YOUR_HTTP_TRIGGER_URL is the URL of your HTTP function and YOUR_SECRET_TOKEN is the value you chose for the WEBHOOK_TOKEN in the configuration.


- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
## Feature management documentation

- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)


## Feature kapt documentation

- [Micronaut Kotlin Annotation Processing (KAPT) documentation](https://docs.micronaut.io/snapshot/guide/#kapt)

- [https://kotlinlang.org/docs/kapt.html](https://kotlinlang.org/docs/kapt.html)


## Feature jetty-server documentation

- [Micronaut Jetty Server documentation](https://micronaut-projects.github.io/micronaut-servlet/1.0.x/guide/index.html#jetty)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature micronaut-test-rest-assured documentation

- [Micronaut Micronaut-Test REST-assured documentation](https://micronaut-projects.github.io/micronaut-test/latest/guide/#restAssured)

- [https://rest-assured.io/#docs](https://rest-assured.io/#docs)


## Feature testcontainers documentation

- [https://www.testcontainers.org/](https://www.testcontainers.org/)


## Feature validation documentation

- [Micronaut Validation documentation](https://micronaut-projects.github.io/micronaut-validation/latest/guide/)


## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


## Feature mockito documentation

- [https://site.mockito.org](https://site.mockito.org)


## Feature swagger-ui documentation

- [Micronaut Swagger UI documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://swagger.io/tools/swagger-ui/](https://swagger.io/tools/swagger-ui/)


## Feature chatbots-telegram-http documentation

- [Micronaut Telegram ChatBot as a controller documentation](https://micronaut-projects.github.io/micronaut-chatbots/latest/guide/)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#nettyHttpClient)


