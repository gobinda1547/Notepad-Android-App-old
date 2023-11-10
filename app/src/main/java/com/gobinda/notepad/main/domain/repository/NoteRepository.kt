package com.gobinda.notepad.main.domain.repository

import com.gobinda.notepad.main.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getSingleNote(noteId: Long): Flow<Note?>

    suspend fun addOrUpdateNote(note: Note): Long

    suspend fun deleteNote(noteId: Long): Int
}