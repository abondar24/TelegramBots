package org.abondar.experimental.cmdbot;

import org.abondar.experimental.cmdbot.commands.ByeCommand;
import org.abondar.experimental.cmdbot.commands.HelloCommand;
import org.abondar.experimental.cmdbot.commands.TimeCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@PropertySource("classpath:tg.properties")
public class CommandLongPollingBot extends TelegramLongPollingCommandBot {

    @Value("${api.token}")
    private String token;

    @Value("${tg.botname}")
    private static String username;


    private static final Logger logger = LogManager.getLogger();

    public CommandLongPollingBot() {
        super(username);

        register(new HelloCommand());
        register(new TimeCommand());
        register(new ByeCommand());

        HelpCommand help = new HelpCommand();
        register(help);
        registerDefaultAction(((absSender, message) -> {
            SendMessage unknownCommand = new SendMessage();
            unknownCommand.setChatId(message.getChatId());
            unknownCommand.setText("The command " + message.getText() + "is not known. Use /help");

            try {
                absSender.execute(unknownCommand);
                logger.info("Sent message: " + unknownCommand.getText());
            } catch (TelegramApiException ex) {
                logger.error(ex.getMessage());
            }
            help.execute(absSender, message.getFrom(), message.getChat(), new String[]{});
        }));
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText("Racing is life. Everything before or after is just waiting");
            try {
                execute(sendMessage);
                logger.info("Sent message: " + sendMessage.getText());
            } catch (TelegramApiException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
