package com.gobinda.notepad.main.view.common.util

import com.gobinda.notepad.R
import com.gobinda.notepad.main.common.TITLE_MAX_LENGTH
import com.gobinda.notepad.main.common.TITLE_MIN_LENGTH

object InputValidator {

    fun validateNoteTitle(input: String): Int? {
        return when {
            input.length < TITLE_MIN_LENGTH -> R.string.text_minimum_length_is
            input.length > TITLE_MAX_LENGTH -> R.string.text_maximum_length_is
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