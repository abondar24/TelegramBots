## CommandBot

Long polling bot with commands

Bot link: https://t.me/learmning_bot

## Running
```yaml
mvn clean install

java -jar target/cmd-bot.jar or mvn clean springboot:run


```
For getting the list of command - write /help

Add your token to properties file



## PS

I tried to write this bot using webhook.

But there are some limitations:

1) You need to run a server(Tomcat or jetty)
2) You must have a webserver with public api and domain name properly set in order Telegram could send you messages
3) TelegramWebHookBot doesn't support commands out of the box. So your bot must implement ICommandRegistry interface.
For reference you can see How TelegramCommandLongPollingBot is done.
4) Library developers highly encourage to use long polling.

