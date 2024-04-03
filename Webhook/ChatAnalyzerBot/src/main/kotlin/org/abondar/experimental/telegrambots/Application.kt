package org.abondar.experimental.telegrambots

import io.micronaut.runtime.Micronaut.run
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License


@OpenAPIDefinition(
    info = Info(
        title = "Chat Analyzer Bot",
        description = "Telegram bot to analyze which words are used in chat",
        version = "1.0",
        license = License(name = "MIT", url = "https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt"),
        contact = Contact(url = "https://github.com/abondar24", name = "Alex Bondar", email = "abondar1992@gmail.com")
    )
)
object Api

fun main(args: Array<String>) {
    run(*args)
}

