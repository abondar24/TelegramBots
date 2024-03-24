package org.abondar.experimental.telegrambots

import io.micronaut.chatbots.core.Dispatcher
import io.micronaut.chatbots.telegram.api.Update
import io.micronaut.chatbots.telegram.api.send.Send
import io.micronaut.chatbots.telegram.core.TelegramBotConfiguration
import io.micronaut.context.BeanContext
import io.micronaut.json.JsonMapper
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.abondar.experimental.telegrambots.message.MessageHandler
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest(startApplication = false)
class MessageHandlerTest {

    @Inject
    lateinit var ctx: BeanContext

    @Inject
    lateinit var dispatcher: Dispatcher<TelegramBotConfiguration, Update, Send>

    @Inject
    lateinit var jsonMapper: JsonMapper

    @Test
    fun messageHandlerBeanExists() {
        assertTrue(ctx.containsBean(MessageHandler::class.java))
    }

    @Test
    @Throws(Exception::class)
    fun messageHandlerExistsTest() {
        val send = dispatcher.dispatch(null, jsonMapper.readValue(getMessageFromJson(), Update::class.java))

        assertTrue(send.isEmpty);
    }

    private fun getMessageFromJson() = MessageHandlerTest::class.java.getResource("/mockMessage.json")!!.readText()
}