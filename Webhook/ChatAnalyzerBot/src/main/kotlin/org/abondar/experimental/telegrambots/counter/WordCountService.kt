package org.abondar.experimental.telegrambots.counter


import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import redis.clients.jedis.Jedis
import java.lang.Exception

@Singleton
open class WordCountService(private val client: Jedis) {

    private val logger: Logger = LoggerFactory.getLogger(WordCountService::class.java)

    fun saveAndCount(words: List<String>) {
        try {
            for (word in words){
                if (!client.exists(word)) {
                    client.set(word, "1")
                } else {
                    client.incr(word)
                }
            }
        } catch (e: Exception){
            logger.error(e.message)
        }
    }

    fun getWordStat(limit: Int): Map<String,Int> {
        val keys = client.keys("*")
        val result = mutableMapOf<String,Int>()

        for (key in keys){
            val  value = client.get(key)?.toIntOrNull() ?: continue
            result[key] = value
        }

        return  result
            .toList()
            .sortedByDescending { it.second }
            .take(limit)
            .toMap()
    }

}

