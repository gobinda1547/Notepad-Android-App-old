package com.gobinda.notepad.view.edit_note

import android.util.Log
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gobinda.notepad.R
import com.gobinda.notepad.domain.model.Note
import com.gobinda.notepad.domain.use_case.AddNote
import com.gobinda.notepad.domain.use_case.AddNoteException
import com.gobinda.notepad.domain.use_case.GetSingleNote
import com.gobinda.notepad.view.common.util.InputValidator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditNoteViewModel @AssistedInject constructor(
    private val addNote: AddNote,
    private val getSingleNote: GetSingleNote,
    @Assisted private val noteId: Long
) : ViewModel() {

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
        viewModelScope.launch {
            val initialNote = getSingleNote.invoke(noteId).first()
            onTitleChanged(initialNote?.title ?: "")
            onContentChanged(initialNote?.content ?: "")
        }
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
            lastEditTime = System.currentTimeMillis(),
            id = noteId
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

    @AssistedFactory
    interface Factory {
        fun create(noteId: Long): EditNoteViewModel
    }

    companion object {
        private const val TAG = "MyTestApp"

        fun getFactory(
            assistedFactory: Factory,
            noteId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(noteId) as T
            }
        }
    }
}