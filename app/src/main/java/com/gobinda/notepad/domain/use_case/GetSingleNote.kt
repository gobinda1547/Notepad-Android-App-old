package com.gobinda.notepad.domain.use_case

import com.gobinda.notepad.common.NoteRepository
import com.gobinda.notepad.common.toNote
import com.gobinda.notepad.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSingleNote(private val repository: NoteRepository) {
    operator fun invoke(noteId: Long): Flow<Note?> {
        return repository.getSingleNote(noteId).map { it?.toNote() }
    }
}