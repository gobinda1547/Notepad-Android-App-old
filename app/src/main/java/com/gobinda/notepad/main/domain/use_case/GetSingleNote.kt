package com.gobinda.notepad.main.domain.use_case

import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetSingleNote(private val repository: NoteRepository) {

    operator fun invoke(noteId: Long): Flow<Note?> {
        return repository.getSingleNote(noteId)
    }
}