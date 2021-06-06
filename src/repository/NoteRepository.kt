package com.notes.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.notes.models.Note
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class NoteRepository(client: MongoClient): RepositoryInterface<Note> {
    override lateinit var col: MongoCollection<Note>

    init {
        val database = client.getDatabase("myFirstDatabase")
        col = database.getCollection<Note>("Note")
    }

    fun getNotesByUserId(userId: String): List<Note> {
        return try {
            col.find(Note::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user desserts")
        }
    }
}