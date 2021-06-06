package com.notes.services

import com.mongodb.client.MongoClient
import com.notes.models.Profile
import com.notes.repository.NoteRepository
import com.notes.repository.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val noteRepo: NoteRepository = NoteRepository(client)

    fun getProfile(userId: String): Profile {
        val user = repo.getById(userId)
        val notes = noteRepo.getNotesByUserId(userId)
        return Profile(user, notes)
    }
}