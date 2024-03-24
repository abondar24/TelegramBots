package org.abondar.experimental.telegrambots

import io.micronaut.chatbots.core.Dispatcher
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.Send
import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.context.BeanContext
import io.micronaut.json.JsonMapper
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.abondar.experimental.telegrambots.stats.StatsCommandHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest(startApplication = false)
class StatsCommandHandlerTest {

    @Inject
    lateinit var ctx: BeanContext

    @Inject
    lateinit var dispatcher: Dispatcher<TelegramBotConfiguration, Update, Send>

    @Inject
    lateinit var jsonMapper: JsonMapper

    @Test
    fun statsHandlerBeanExists() {
        assertTrue(ctx.containsBean(StatsCommandHandler::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun statsHandlerExistsTest() {
        val send = dispatcher.dispatch(null, jsonMapper.readValue(getStatsCommandJson(), Update::class.java)).get()

        assertTrue(send is SendMessage)
        assertEquals(
            "Get statistics of most frequently used words in chat",
            (send as SendMessage).getText().trim()
        )
    }

    private fun getStatsCommandJson() = StatsCommandHandlerTest::class.java.getResource("/mockStatsCommand.json")!!.readText()
}