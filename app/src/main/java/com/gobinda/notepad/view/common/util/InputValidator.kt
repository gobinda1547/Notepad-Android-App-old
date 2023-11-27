package com.gobinda.notepad.view.common.util

import com.gobinda.notepad.R

object InputValidator {

    fun validateNoteTitle(input: String): Int? {
        return when {
            input.length < com.gobinda.notepad.common.TITLE_MIN_LENGTH -> R.string.text_minimum_length_is
            input.length > com.gobinda.notepad.common.TITLE_MAX_LENGTH -> R.string.text_maximum_length_is
            else -> null
        }
    }

    fun validateNoteContent(input: String): Int? {
        return when (input.isEmpty()) {
            true -> R.string.text_should_not_be_empty
            else -> null
        }
    }
}