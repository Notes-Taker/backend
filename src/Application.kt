package com.notes

import com.apurebase.kgraphql.GraphQL
import com.notes.di.mainModule
import com.notes.schema.authSchema
import com.notes.schema.noteSchema
import com.notes.schema.profileSchema
import com.notes.services.AuthService
import com.notes.services.NoteService
import com.notes.services.ProfileService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import org.koin.core.context.startKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        val authService = AuthService()
        val profileService = ProfileService()
        val noteService = NoteService()

        playground = true

        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            authSchema(authService)
            profileSchema(profileService)
            noteSchema(noteService)
        }
    }
}