package com.gobinda.notepad.view.note_list

import androidx.lifecycle.ViewModel
import com.gobinda.notepad.domain.use_case.GetNoteList
import com.gobinda.notepad.domain.util.NoteSortingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(getNoteList: GetNoteList) : ViewModel() {
    val noteList = getNoteList(sortingOrder = NoteSortingOrder.Descending)
}