package com.notes.models

data class Profile(val user: User, val notes: List<Note> = emptyList())