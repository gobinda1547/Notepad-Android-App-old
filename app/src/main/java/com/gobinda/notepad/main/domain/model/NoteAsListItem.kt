package com.gobinda.notepad.main.domain.model

data class NoteAsListItem(
    val title: String,
    val content: String,
    val id: Long = 0
)