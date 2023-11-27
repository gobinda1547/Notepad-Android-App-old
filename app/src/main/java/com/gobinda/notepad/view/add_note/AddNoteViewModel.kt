package com.gobinda.notepad.view.add_note

import android.util.Log
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.R
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.use_case.AddNote
import com.gobinda.notepad.domain.use_case.AddNoteException
import com.gobinda.notepad.view.common.util.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val addNote: AddNote
) : ViewModel() {

    companion object {
        private const val TAG = "MyTestApp"
    }

    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> get() = _noteTitle.asStateFlow()

    private val _titleErrorMsgId = MutableStateFlow<Int?>(null)
    val titleErrorMsgId: StateFlow<Int?> get() = _titleErrorMsgId

    private val _noteContent = MutableStateFlow("")
    val noteContent: StateFlow<String> get() = _noteContent.asStateFlow()

    private val _contentErrorMsgId = MutableStateFlow<Int?>(null)
    val contentErrorMsgId: StateFlow<Int?> get() = _contentErrorMsgId

    private val _toastMessage = MutableSharedFlow<Int>()
    val toastMessage: SharedFlow<Int> = _toastMessage.asSharedFlow()

    private val _shouldCloseActivity = MutableStateFlow(false)
    val shouldCloseActivity: StateFlow<Boolean> = _shouldCloseActivity.asStateFlow()

    val shouldEnableSaveMenu = combine(titleErrorMsgId, contentErrorMsgId) { value1, value2 ->
        value1 == null && value2 == null
    }.stateIn(viewModelScope, SharingStarted.Lazily, true)

    val titleChangeListener = TextViewBindingAdapter.OnTextChanged { s, _, _, _ ->
        s?.toString()?.let { onTitleChanged(it) }
    }

    val contentChangeListener = TextViewBindingAdapter.OnTextChanged { s, _, _, _ ->
        s?.toString()?.let { onContentChanged(it) }
    }

    init {
        onTitleChanged("")
        onContentChanged("")
    }

    private fun onTitleChanged(titleText: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d(TAG, "onTitleChanged: invoked with [$titleText]")
        _noteTitle.tryEmit(titleText)
        _titleErrorMsgId.tryEmit(InputValidator.validateNoteTitle(titleText))
    }

    private fun onContentChanged(contentText: String) {
        Log.d(TAG, "onContentChanged: invoked [$contentText]")
        _noteContent.tryEmit(contentText)
        _contentErrorMsgId.tryEmit(InputValidator.validateNoteContent(contentText))
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