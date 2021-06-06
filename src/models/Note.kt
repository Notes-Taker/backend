package com.notes.models

data class Note(override val id: String, var text: String) : Model

data class NoteInput(val text: String)