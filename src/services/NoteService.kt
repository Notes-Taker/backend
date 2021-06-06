package com.notes.services

import com.mongodb.client.MongoClient
import com.notes.models.Note
import com.notes.models.NoteInput
import com.notes.repository.NotesRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.UUID

class NoteService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: NotesRepository = NotesRepository(client)

    fun getNotes(): List<Note> = repo.getAll()

    fun getNote(id: String): Note = repo.getById(id)

    fun createNote(noteInput: NoteInput): Note {
        val uid = UUID.randomUUID().toString()
        val note = Note(
            id = uid,
            text = noteInput.text,
        )
        return repo.add(note)
    }

    fun updateNote(id: String, noteInput: NoteInput): Note {
        val updates = Note(
            id = id,
            text = noteInput.text
        )
        return repo.update(updates)
    }

    fun deleteNote(id: String): Boolean = repo.delete(id)
}