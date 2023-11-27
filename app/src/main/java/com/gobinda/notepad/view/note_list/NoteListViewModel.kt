package com.gobinda.notepad.view.note_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.domain.use_case.DeleteNote
import com.gobinda.notepad.domain.use_case.GetNoteList
import com.gobinda.notepad.domain.util.NoteSortingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    getNoteList: GetNoteList,
    private val deleteNote: DeleteNote
) : ViewModel() {
    val noteList = getNoteList(sortingOrder = NoteSortingOrder.Descending)

    fun deleteNoteFromList(noteId: Long) {
        viewModelScope.launch {
            deleteNote.invoke(noteId)
        }
    }
}