package org.abondar.experimental.updbot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;


public class UpdateBot extends TelegramLongPollingBot {

    private static final Logger logger = LogManager.getLogger();

    private String token;


    public UpdateBot() {
        super();
        readProperties();
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();
            if (msg.getText().equals("start")){
                startTimedSending(msg.getChatId());
            }

            if (msg.getText().equals("stop")){
                stopTimedSending();
            }

        }
    }

    @Override
    public String getBotUsername() {
        return "learmning_bot";
    }

    @Override
    public String getBotToken() {

        return token;
    }

    private void startTimedSending(long chatId) {

        TimerExecutor.Task task = TimerExecutor.getInstance().new Task("Update task") {
            @Override
            public void execute() {
                sendMessage(chatId);
            }

        };

        if (chatId!=0){
            TimerExecutor.getInstance().start(task);
        }

    }

    private void stopTimedSending(){
        TimerExecutor.getInstance().stop();
    }

    private void sendMessage(long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        String update = LocalDateTime.now().toString();
        sendMessage.setText("Date and time now: " + update);
        try {
            execute(sendMessage);
            logger.info("Sent message: "+sendMessage.getText());
        } catch (TelegramApiException ex) {
            logger.error(ex.getMessage());
        }
    }


    private void readProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tg.properties");

            properties.load(inputStream);
            token = properties.getProperty("api.token");

        } catch (IOException ex) {
            logger.error("Error reading token from file");
            logger.error(ex.getLocalizedMessage());
        }
    }
}
