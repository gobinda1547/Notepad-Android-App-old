package com.gobinda.notepad.main.domain.model

data class Note(
    val title: String,
    val content: String,
    val lastEditTime: Long,
    val id: Long = 0
)