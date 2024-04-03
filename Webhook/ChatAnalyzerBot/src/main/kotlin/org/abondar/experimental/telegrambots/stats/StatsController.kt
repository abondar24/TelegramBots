package org.abondar.experimental.telegrambots.stats

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import io.micronaut.json.JsonMapper
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

import org.abondar.experimental.telegrambots.counter.WordCountService
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Controller("/stats")
@Validated
class StatsController (
    private val wordCountService: WordCountService,
    private val jsonMapper: JsonMapper
){

    private val logger: Logger = LoggerFactory.getLogger(StatsController::class.java)

    @Get("/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch statistics", description = "Fetch top of words used in chat")
    @ApiResponse(responseCode = "200", description = "List of top commonly used words")
    @ApiResponse(responseCode = "400", description = "Words top value is too small or too big")
    fun statistics(
        @Parameter(
            description = "The number of words to return. Minimum 5, maximum 50",
            example = "20",
            required = true,
            schema = Schema(implementation = Int::class),
            allowEmptyValue = false,
        )
        @PathVariable
        @Min(5)
        @Max(50)
        limit: Int): String {
        logger.info("Fetching statistics")

        val wordStat = wordCountService.getWordStat(limit)

        return jsonMapper.writeValueAsString(wordStat)
    }
}