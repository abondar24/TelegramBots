package org.abondar.experimental.telegrambots.message

import io.micronaut.chatbots.core.SpaceParser
import io.micronaut.chatbots.telegram.api.Chat
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.chatbots.telegram.core.TelegramHandler
import io.micronaut.core.order.Ordered
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Singleton
open class MessageHandler(
    //TODO add redis,
    private val spaceParser: SpaceParser<Update, Chat>
): TelegramHandler<SendMessage>  {

    private val logger: Logger = LoggerFactory.getLogger(MessageHandler::class.java)


    override fun canHandle(bot: TelegramBotConfiguration?, input: Update?): Boolean {
       return !input?.message?.text.equals("/stats")
    }

    override fun handle(bot: TelegramBotConfiguration?, input: Update?): Optional<SendMessage> {
        logger.info("Got message for analysis");
        //TODO: call redis
        return Optional.empty();
    }

    override fun getOrder() = 0
}