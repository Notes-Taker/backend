package com.notes.models

data class Note(override val id: String, val userId: String, var text: String) : Model

data class NoteInput(val userId: String, val text: String)