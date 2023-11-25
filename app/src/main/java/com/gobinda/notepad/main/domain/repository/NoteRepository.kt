package com.gobinda.notepad.main.domain.repository

import com.gobinda.notepad.main.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<NoteModel>>

    fun getSingleNote(noteId: Long): Flow<NoteModel?>

    suspend fun addOrUpdateNote(note: NoteModel): Long

    suspend fun deleteNote(noteId: Long): Int
}