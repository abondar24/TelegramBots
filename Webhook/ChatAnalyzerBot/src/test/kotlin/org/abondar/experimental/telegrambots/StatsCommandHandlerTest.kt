package org.abondar.experimental.telegrambots

import io.micronaut.chatbots.core.Dispatcher
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.Send
import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.context.BeanContext
import io.micronaut.context.annotation.Replaces
import io.micronaut.json.JsonMapper
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.abondar.experimental.telegrambots.stats.StatsCommandHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@MicronautTest(startApplication = false)
class StatsCommandHandlerTest {

    @Inject
    lateinit var ctx: BeanContext

    @Inject
    lateinit var dispatcher: Dispatcher<TelegramBotConfiguration, Update, Send>

    @Inject
    lateinit var jsonMapper: JsonMapper

    @Inject
    lateinit var wordCountService: WordCountService


    @Test
    fun statsHandlerBeanExists() {
        assertTrue(ctx.containsBean(StatsCommandHandler::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun statsHandlerExistsTest() {

        `when`(wordCountService.getWordStat(50)).thenReturn(
            mapOf(
            "test" to 2,
            "test1" to 3
            )
        )

        val req =  jsonMapper.readValue(getStatsCommandJson(), Update::class.java)
        val send = dispatcher.dispatch(null, req)

        assertFalse(send.isEmpty);

        val msg = send.get();
        assertTrue(msg is SendMessage)

        assertEquals( req.message.chat.id,(msg as SendMessage).chatId )
        assertEquals("Most popular words in chat\n" +
                "test: 2\n" +
                "test1: 3",
            msg.text.trim()
            )

    }

    @Singleton
    @Replaces(WordCountService::class)
    fun wordCountService(): WordCountService{
        return mock();
    }

    private fun getStatsCommandJson() = StatsCommandHandlerTest::class.java.getResource("/mockStatsCommand.json")!!.readText()
}