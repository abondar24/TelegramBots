package org.abondar.experimental.telegrambots.stats

import io.micronaut.chatbots.core.SpaceParser
import io.micronaut.chatbots.core.TextResourceLoader
import io.micronaut.chatbots.telegram.api.Chat
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.chatbots.telegram.core.CommandHandler
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.chatbots.telegram.core.TelegramSlashCommandParser
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Singleton
open class StatsCommandHandler(
   slashCommandParser: TelegramSlashCommandParser,
   textResourceLoader: TextResourceLoader,
   spaceParser: SpaceParser<Update, Chat>
    //TODO add redis service
) : CommandHandler(slashCommandParser, textResourceLoader, spaceParser) {

    private val logger: Logger = LoggerFactory.getLogger(StatsCommandHandler::class.java)

    override fun handle(bot: TelegramBotConfiguration?, input: Update?): Optional<SendMessage> {
        logger.info("Got stats command");
        //TODO: call redis
        return Optional.empty();
    }

    override fun getCommand() = COMMAND_ABOUT

    companion object {
        private const val COMMAND_ABOUT = "/stats"
    }
}