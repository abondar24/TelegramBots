package org.abondar.experimental.telegrambots.stats

import io.micronaut.chatbots.core.SpaceParser
import io.micronaut.chatbots.core.TextResourceLoader
import io.micronaut.chatbots.telegram.api.Chat
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.chatbots.telegram.core.CommandHandler
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.chatbots.telegram.core.TelegramSlashCommandParser
import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Singleton
@Introspected
open class StatsCommandHandler(
    slashCommandParser: TelegramSlashCommandParser,
    textResourceLoader: TextResourceLoader,
    spaceParser: SpaceParser<Update, Chat>,
    private val wordCountService: WordCountService,
) : CommandHandler(slashCommandParser, textResourceLoader, spaceParser) {

    private val logger: Logger = LoggerFactory.getLogger(StatsCommandHandler::class.java)


    override fun handle(bot: TelegramBotConfiguration?, input: Update?): Optional<SendMessage> {
        logger.info("Got stats command");
        val wordsStat = wordCountService.getWordStat(STAT_LIMIT)

        val sendMessage = SendMessage()
        sendMessage.text = prepareStatMessage(wordsStat)

        return Optional.of(sendMessage);
    }

    private fun prepareStatMessage(map: Map<String, Int>): String {
        logger.info("Preparing word response");
        val stringBuilder = StringBuilder()
        stringBuilder.append("Most popular words in chat\n")
        for ((key, value) in map) {
            stringBuilder.append("$key: $value\n")
        }
        return stringBuilder.toString()
    }

    override fun getCommand() = COMMAND_ABOUT

    companion object {
        private const val COMMAND_ABOUT = "/stats"
        private  const val STAT_LIMIT = 50
    }

    override fun getOrder() = 1
}