package com.notes.services

import com.mongodb.client.MongoClient
import com.notes.models.Note
import com.notes.models.NoteInput
import com.notes.repository.NoteRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.UUID

class NoteService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: NoteRepository = NoteRepository(client)

    fun getNotesByUserId(userId: String): List<Note> = repo.getNotesByUserId(userId)

    fun getNote(id: String): Note = repo.getById(id)

    fun createNote(noteInput: NoteInput): Note {
        val uid = UUID.randomUUID().toString()
        val note = Note(
            id = uid,
            userId = noteInput.userId,
            text = noteInput.text,
        )
        return repo.add(note)
    }

    fun updateNote(userId: String, noteId: String, noteInput: NoteInput): Note {
        val note = repo.getById(noteId)
        if (note.userId == userId) {
            val updates = Note(
                id = noteId,
                userId = noteInput.userId,
                text = noteInput.text
            )
            return repo.update(updates)
        }
        error("Cannot update note")
    }

    fun deleteNote(userId: String, noteId: String): Boolean {
        val note = repo.getById(noteId)
        if (note.userId == userId) {
            return repo.delete(noteId)
        }
        error("Cannot delete note")
    }
}