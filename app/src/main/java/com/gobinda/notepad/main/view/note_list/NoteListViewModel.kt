package com.gobinda.notepad.main.view.note_list

import androidx.lifecycle.ViewModel
import com.gobinda.notepad.main.domain.use_case.GetNoteList
import com.gobinda.notepad.main.domain.util.NoteSortingOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(getNoteList: GetNoteList) : ViewModel() {
    val noteList = getNoteList(sortingOrder = NoteSortingOrder.Ascending)
}