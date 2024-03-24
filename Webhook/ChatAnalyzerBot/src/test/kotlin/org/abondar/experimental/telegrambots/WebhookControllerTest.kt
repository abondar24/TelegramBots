package org.abondar.experimental.telegrambots

import io.micronaut.chatbots.telegram.api.send.SendMessage
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

@MicronautTest
class WebhookControllerTest {

    @field:Client("/")
    @Inject
    lateinit var client: HttpClient

    //TODO add test for stats controller
    @Test
    fun tokenIsRequiredStats() {
        val post = HttpRequest.POST("/chat_analyzer", getStatCommandJson())
        val httpClientResponseException = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange(post, SendMessage::class.java)
        }
        assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.status)
    }

    @Test
    fun tokenIsRequiredMessage() {
        val post = HttpRequest.POST("/chat_analyzer", getMessageJson())
        val httpClientResponseException = assertThrows(HttpClientResponseException::class.java) {
            client.toBlocking().exchange(post, SendMessage::class.java)
        }
        assertEquals(HttpStatus.BAD_REQUEST, httpClientResponseException.status)
    }

    private fun getStatCommandJson() = WebhookControllerTest::class.java.getResource("/mockStatsCommand.json")!!.readText()

    private fun getMessageJson() = WebhookControllerTest::class.java.getResource("/mockMessage.json")!!.readText()


}