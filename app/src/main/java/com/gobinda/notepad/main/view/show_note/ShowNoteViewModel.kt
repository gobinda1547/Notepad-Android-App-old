package com.gobinda.notepad.main.view.show_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gobinda.notepad.main.domain.use_case.GetSingleNote
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ShowNoteViewModel @AssistedInject constructor(
    getSingleNote: GetSingleNote,
    @Assisted private val noteId: Long
) : ViewModel() {

    val singleNote = getSingleNote(noteId = noteId)

    @AssistedFactory
    interface ShowNoteViewModelFactory {
        fun create(noteId: Long): ShowNoteViewModel
    }

    companion object {
        fun providesFactory(
            assistedFactory: ShowNoteViewModelFactory,
            noteId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(noteId) as T
            }
        }
    }

}