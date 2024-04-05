package org.abondar.experimental.telegrambots.counter

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Value
import io.micronaut.core.annotation.Introspected
import jakarta.inject.Singleton
import redis.clients.jedis.Jedis

@Factory
@Introspected
class RedisConfiguration {

    @Singleton
    fun jedisClient(@Value("\${redis.host}") host: String,
                    @Value("\${redis.port}") port: Int,
                    @Value("\${redis.username}") username: String,
                    @Value("\${redis.password}")  password: String): Jedis {
        val jedis = Jedis(builJedisUrl(host,port,username,password))

        return jedis
    }

    private fun builJedisUrl(host: String, port: Int, username: String, password: String): String{
        val builder = StringBuilder()

        builder.append("redis://")
        if (username.isNotBlank() || password.isNotBlank()) {
            builder.append(username)
            builder.append(":")
            builder.append(password)
            builder.append("@")
        }
        builder.append(host)
        builder.append(":")
        builder.append(port)

        return builder.toString()
    }
}