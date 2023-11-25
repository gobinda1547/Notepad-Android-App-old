package com.gobinda.notepad.main.domain.use_case

import com.gobinda.notepad.R
import com.gobinda.notepad.main.common.toNoteModel
import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(AddNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.trim().isEmpty()) {
            throw AddNoteException(R.string.text_empty_title)
        }
        if (note.content.trim().isEmpty()) {
            throw AddNoteException(R.string.text_empty_content)
        }
        if (repository.addOrUpdateNote(note.toNoteModel()) <= 0) {
            throw AddNoteException(R.string.text_unknown_exception)
        }
    }
}

class AddNoteException(val reason: Int) : Exception()
