package org.abondar.experimental.telegrambots.stats

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.json.JsonMapper
import io.micronaut.serde.ObjectMapper
import org.abondar.experimental.telegrambots.counter.WordCountService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/stats")
class StatsController (
    private val wordCountService: WordCountService,
    private val jsonMapper: JsonMapper
){

    private val logger: Logger = LoggerFactory.getLogger(StatsController::class.java)

    @Get("/{limit}")
    fun statistics(@PathVariable limit: Int): String {
        logger.info("Fetching statistics")

        val wordStat = wordCountService.getWordStat(limit)

        return jsonMapper.writeValueAsString(wordStat)
    }
}