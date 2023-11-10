package com.gobinda.notepad.main.domain.use_case

import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        repository.addOrUpdateNote(note)
    }
}