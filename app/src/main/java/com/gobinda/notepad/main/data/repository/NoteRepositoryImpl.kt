package com.gobinda.notepad.main.data.repository

import com.gobinda.notepad.main.data.source.NoteDao
import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getSingleNote(noteId: Long): Note? {
        return noteDao.getSingleNote(noteId)
    }

    override suspend fun addOrUpdateNote(note: Note) {
        return noteDao.addOrUpdateNote(note)
    }

    override suspend fun deleteNote(noteId: Long) {
        return noteDao.deleteNote(noteId)
    }
}