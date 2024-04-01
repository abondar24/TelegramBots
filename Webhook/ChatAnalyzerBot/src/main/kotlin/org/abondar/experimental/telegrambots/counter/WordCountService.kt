package org.abondar.experimental.telegrambots.counter

import io.lettuce.core.RedisClient
import jakarta.inject.Singleton

@Singleton
class WordCountService(private val client: RedisClient) {

    fun saveAndCount(words: List<String>) {


    }

    fun getWordStat(limit: Int): Map<String,Int> {

        return mapOf();
    }

}

