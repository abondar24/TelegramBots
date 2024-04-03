package org.abondar.experimental.telegrambots

import io.micronaut.context.annotation.Replaces
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import io.restassured.http.Header
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeEach

@MicronautTest
class StatsControllerTest {


    @Inject
    lateinit var wordCountService: WordCountService

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @BeforeEach
    fun setup() {
        RestAssured.port = embeddedServer.port
    }
    
    @Test
    fun statsControllerTest(){
        val limit = 10

        Mockito.`when`(wordCountService.getWordStat(limit)).thenReturn(
            mapOf(
                "test" to 2,
                "test1" to 3
            )
        )

        RestAssured.given()
            .log()
            .all()
            .pathParam("limit",limit)
            .header("accept","application/json")
            .`when`().get("/stats/{limit}")
            .then()
            .statusCode(200)
            .assertThat()
            .body("size()",equalTo(2))

    }


    @Test
    fun statsControllerSmallLimitTest(){
        RestAssured.given()
            .log()
            .all()
            .pathParam("limit",5)
            .header("accept","application/json")
            .`when`().get("/stats/{limit}")
            .then()
            .statusCode(400)
    }

    @Test
    fun statsControllerBigLimitTest(){
        RestAssured.given()
            .log()
            .all()
            .pathParam("limit",55)
            .header("accept","application/json")
            .`when`().get("/stats/{limit}")
            .then()
            .statusCode(400)
    }

    @Singleton
    @Replaces(WordCountService::class)
    fun wordCountService(): WordCountService{
        return mock();
    }
}