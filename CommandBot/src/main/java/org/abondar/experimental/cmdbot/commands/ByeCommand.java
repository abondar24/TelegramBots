package org.abondar.experimental.cmdbot.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ByeCommand extends BotCommand {

    public ByeCommand() {
        super("bye", "Say bye to the bot");
    }

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String message = new StringBuilder().append("Bye ")
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .append(" ")
                .append(Emoji.UNAMUSED_FACE.getFirst())
                .append(Emoji.UNAMUSED_FACE.getSecond())
                .toString();


        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(message);

        try {
            absSender.execute(answer);
            logger.info("Sent answer :" + message);
        } catch (TelegramApiException ex){
            logger.error(ex.getMessage());
        }



    }
}
