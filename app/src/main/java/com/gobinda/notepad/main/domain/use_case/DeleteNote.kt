package com.gobinda.notepad.main.domain.use_case

import com.gobinda.notepad.main.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {

    suspend operator fun invoke(noteId: Long) {
        repository.deleteNote(noteId)
    }
}