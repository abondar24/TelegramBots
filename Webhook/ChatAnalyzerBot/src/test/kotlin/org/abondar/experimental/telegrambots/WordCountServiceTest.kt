package org.abondar.experimental.telegrambots

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import redis.clients.jedis.Jedis


@MicronautTest
@Testcontainers
internal class WordCountServiceTest {

    companion object {
        @Container
        val redisContainer: GenericContainer<*> = GenericContainer<Nothing>("redis:latest")
            .withExposedPorts(6379)
    }

    private lateinit var jedis: Jedis

    @BeforeEach
    fun setup(){
        val redisHost = redisContainer.getHost()
        val redisPort = redisContainer.getMappedPort(6379)
        jedis = Jedis(redisHost,redisPort)
        jedis.connect()
        jedis.flushDB()
    }

    @Test
    fun saveWordAndCountTest(){
        val wordCountService = WordCountService(jedis)
        val words = listOf("test","test1","test2","test")

        wordCountService.saveAndCount(words)
        assertEquals(2,jedis.get("test")?.toInt())
        assertEquals(1,jedis.get("test1")?.toInt())
        assertEquals(1,jedis.get("test2")?.toInt())
    }



    @Test
    fun getWordStatTest(){
        val wordCountService = WordCountService(jedis)
        val words = listOf("test","test1","test2","test")

        wordCountService.saveAndCount(words)
        val wordStat = wordCountService.getWordStat(2)
        assertEquals(2,wordStat.size)
        assertEquals(2,wordStat["test"])
        assertEquals(1,wordStat["test2"])
    }

}