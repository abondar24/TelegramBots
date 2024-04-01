package org.abondar.experimental.telegrambots.counter

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton
import redis.clients.jedis.Jedis

@Factory
class RedisConfiguration {

    @Singleton
    fun jedisClient(@Value("\${redis.host}") host: String,
                    @Value("\${redis.port}") port: Int): Jedis {
        return Jedis(host,port)
    }
}