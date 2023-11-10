package com.gobinda.notepad.main.domain.use_case

import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(AddNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.trim().isEmpty()) {
            throw AddNoteException("Empty title")
        }
        if (note.content.trim().isEmpty()) {
            throw AddNoteException("Empty content")
        }
        if (repository.addOrUpdateNote(note) <= 0) {
            throw AddNoteException("Exception from database")
        }
    }
}

class AddNoteException(reason: String) : Exception(reason)