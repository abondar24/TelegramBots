package org.abondar.experimental.telegrambots

import io.micronaut.context.annotation.Replaces
import io.micronaut.json.JsonMapper
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import redis.clients.jedis.exceptions.JedisException

@MicronautTest
class StatsControllerTest {


    @Inject
    lateinit var wordCountService: WordCountService

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Inject
    lateinit var jsonMapper: JsonMapper

    @BeforeEach
    fun setup() {
        RestAssured.port = embeddedServer.port
    }

    @Test
    fun statsControllerTest() {
        val limit = 10

        val res = mapOf(
            "test" to 2,
            "test1" to 3
        )

        Mockito.`when`(wordCountService.getWordStat(limit)).thenReturn(res)

        val resBody= jsonMapper.writeValueAsString(res)

        RestAssured.given()
            .log()
            .all()
            .queryParam("limit", limit)
            .header("accept", "application/json")
            .`when`().get("/stats")
            .then()
            .statusCode(200)
            .assertThat()
            .body("size()", equalTo(2))
            .body(`is`(resBody))

    }


    //TODO fix weird 403 when limit is < 10
    @Test
    fun statsControllerSmallLimitTest() {
        RestAssured.given()
            .log()
            .all()
            .queryParam("limit", 3)
            .header("accept", "application/json")
            .`when`().get("/stats")
            .then()
            .statusCode(400)
    }

    @Test
    fun statsControllerBigLimitTest() {
        RestAssured.given()
            .log()
            .all()
            .queryParam("limit", 55)
            .header("accept", "application/json")
            .`when`().get("/stats")
            .then()
            .statusCode(400)
    }

    @Test
    fun statsControllerRedisUnavailableTest() {

        val limit = 15

        Mockito.`when`(wordCountService.getWordStat(limit)).thenThrow(JedisException::class.java)

        RestAssured.given()
            .log()
            .all()
            .queryParam("limit", limit)
            .header("accept", "application/json")
            .`when`().get("/stats")
            .then()
            .statusCode(502)
            .assertThat()
            .body(`is`("Redis is not available"))
    }

    @Singleton
    @Replaces(WordCountService::class)
    fun wordCountService(): WordCountService {
        return mock()
    }
}