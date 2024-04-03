package org.abondar.experimental.telegrambots.stats

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import redis.clients.jedis.exceptions.JedisException

@Produces
@Singleton
@Requires(classes = [JedisException::class, ExceptionHandler::class])
class JedisExceptionHandler : ExceptionHandler<JedisException, HttpResponse<Any>> {
    override fun handle(request: HttpRequest<*>?, exception: JedisException?): HttpResponse<Any> {
        return HttpResponse.status<Any?>(HttpStatus.BAD_GATEWAY).body("Redis is not available")
    }
}