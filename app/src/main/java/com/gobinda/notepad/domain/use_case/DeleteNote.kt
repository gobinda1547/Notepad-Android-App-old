package com.gobinda.notepad.domain.use_case

import com.gobinda.notepad.common.NoteRepository

class DeleteNote(private val repository: NoteRepository) {

    @Throws(DeleteNoteException::class)
    suspend operator fun invoke(noteId: Long) {
        if (repository.deleteNote(noteId) <= 0) {
            throw DeleteNoteException("Exception from database")
        }
    }
}

class DeleteNoteException(reason: String) : Exception(reason)