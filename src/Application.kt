package com.notes

import com.apurebase.kgraphql.GraphQL
import com.notes.di.mainModule
import com.notes.schema.noteSchema
import com.notes.services.NoteService
import io.ktor.application.Application
import io.ktor.application.install
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        val noteService = NoteService()
        playground = true
        schema {
            noteSchema(noteService)
        }
    }
}