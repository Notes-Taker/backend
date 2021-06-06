package com.notes.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.notes.models.Note
import org.litote.kmongo.getCollection

class NotesRepository(client: MongoClient): RepositoryInterface<Note> {
    override lateinit var col: MongoCollection<Note>

    init {
        val database = client.getDatabase("myFirstDatabase")
        col = database.getCollection<Note>("Note")
    }
}