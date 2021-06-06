package com.notes.schema

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.notes.models.Note
import com.notes.models.NoteInput
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
        resolver { ctx: Context ->
            try {
                noteService.getNotes()
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
        resolver { id: String, noteInput: NoteInput, ctx: Context ->
            try {
                noteService.updateNote(id, noteInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteNote") {
        description = "Deletes a note"
        resolver { id: String, ctx: Context ->
            try {
                noteService.deleteNote(id)
            } catch (e: Exception) {
                false
            }
        }
    }
}