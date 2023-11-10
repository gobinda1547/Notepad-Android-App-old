package com.gobinda.notepad.main.domain.repository

import com.gobinda.notepad.main.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun getSingleNote(noteId: Long): Note?

    suspend fun addOrUpdateNote(note: Note)

    suspend fun deleteNote(noteId: Long)
}