package com.gobinda.notepad.domain.model

data class NoteAsListItem(
    val title: String,
    val content: String,
    val id: Long = 0
)