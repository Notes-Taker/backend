package com.notes.schema

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.notes.models.Note
import com.notes.models.NoteInput
import com.notes.models.User
import com.notes.services.NoteService

fun SchemaBuilder.noteSchema(noteService: NoteService) {

    inputType<NoteInput> {
        description = "The input of the note without the identifier"
    }

    type<Note> {
        description = "Note object with attributes text"
    }

    query("note") {
        resolver { id: String, ctx: Context ->
            try {
                noteService.getNote(id)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("notes") {
        description = "Retrieve all notes"
        resolver { userId: String, ctx: Context ->
            try {
                noteService.getNotesByUserId(userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createNote") {
        description = "Create a new note"
        resolver { noteInput: NoteInput, ctx: Context ->
            try {
                noteService.createNote(noteInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateNote") {
        description = "Updates a note"
        resolver { noteId: String, noteInput: NoteInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                noteService.updateNote(userId, noteId, noteInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteNote") {
        description = "Deletes a note"
        resolver { noteId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                noteService.deleteNote(userId, noteId)
            } catch (e: Exception) {
                false
            }
        }
    }
}