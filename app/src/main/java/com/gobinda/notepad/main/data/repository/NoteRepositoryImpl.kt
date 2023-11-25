package com.gobinda.notepad.main.data.repository

import com.gobinda.notepad.main.data.model.NoteModel
import com.gobinda.notepad.main.data.source.NoteDao
import com.gobinda.notepad.main.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteModel>> {
        return noteDao.getAllNotes()
    }

    override fun getSingleNote(noteId: Long): Flow<NoteModel?> {
        return noteDao.getSingleNote(noteId)
    }

    override suspend fun addOrUpdateNote(note: NoteModel): Long {
        return noteDao.addOrUpdateNote(note)
    }

    override suspend fun deleteNote(noteId: Long): Int {
        return noteDao.deleteNote(noteId)
    }
}