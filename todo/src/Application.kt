package com.abhi

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(Routing) {
        trace { application.log.trace(it.buildText()) }
        todoApi()
    }

    install(StatusPages)
    {
        this.exception<Throwable> { e ->
            call.respondText { e.localizedMessage }
        }
    }

    install(ContentNegotiation) {
        jackson()
        {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

