package com.gobinda.notepad.main.view.add_note

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.R
import com.gobinda.notepad.main.domain.model.Note
import com.gobinda.notepad.main.domain.use_case.AddNote
import com.gobinda.notepad.main.domain.use_case.AddNoteException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNote: AddNote
) : ViewModel() {

    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> get() = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow("")
    val noteContent: StateFlow<String> get() = _noteContent.asStateFlow()

    private val _toastMessage = MutableSharedFlow<Int>()
    val toastMessage: SharedFlow<Int> = _toastMessage.asSharedFlow()

    private val _shouldCloseActivity = MutableStateFlow(false)
    val shouldCloseActivity: StateFlow<Boolean> = _shouldCloseActivity.asStateFlow()

    fun onTitleChanged(titleText: String) {
        Log.d("[GP]", "onTitleChanged: invoked [$titleText]")
        _noteTitle.tryEmit(titleText)
    }

    fun onContentChanged(contentText: String) {
        Log.d("[GP]", "onContentChanged: invoked [$contentText]")
        _noteContent.tryEmit(contentText)
    }

    fun onSaveButtonClicked() = viewModelScope.launch(Dispatchers.IO) {
        val newNote = Note(
            title = noteTitle.value,
            content = noteContent.value,
            lastEditTime = System.currentTimeMillis()
        )
        try {
            addNote(newNote)
        } catch (e: AddNoteException) {
            _toastMessage.emit(e.reason)
            return@launch
        }
        _toastMessage.emit(R.string.text_saved_successfully)
        _shouldCloseActivity.emit(true)
    }
}