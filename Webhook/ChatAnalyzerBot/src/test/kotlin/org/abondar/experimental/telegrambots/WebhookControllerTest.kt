package org.abondar.experimental.telegrambots

import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class WebhookControllerTest {

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @BeforeEach
    fun setup() {
        RestAssured.port = embeddedServer.port
    }


    @Test
    fun tokenIsRequiredStats() {
        RestAssured.given()
            .contentType("application/json")
            .body(getStatCommandJson())
            .`when`().post("/chat_analyzer")
            .then()
            .statusCode(400)

    }

    @Test
    fun tokenIsRequiredMessage() {
        RestAssured.given()
            .contentType("application/json")
            .body(getMessageJson())
            .`when`().post("/chat_analyzer")
            .then()
            .statusCode(400)
    }

    private fun getStatCommandJson() =
        WebhookControllerTest::class.java.getResource("/mockStatsCommand.json")!!.readText()

    private fun getMessageJson() = WebhookControllerTest::class.java.getResource("/mockMessage.json")!!.readText()


}