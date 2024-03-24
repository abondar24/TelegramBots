package org.abondar.experimental.telegrambots.stats

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/stats")
class StatsController (
  //TODO: add redis service
){

    private val logger: Logger = LoggerFactory.getLogger(StatsController::class.java)

    @Get
    fun statistics() {
        //TODO: add offset and linit
        logger.info("Fetching statistics")


    }
}