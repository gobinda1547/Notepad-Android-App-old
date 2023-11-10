package com.gobinda.notepad.main.view.common.util

import android.text.Editable
import android.text.TextWatcher

abstract class AfterTextChangedListener : TextWatcher {

    abstract fun onChange(text: String)

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        p0?.let { onChange(it.toString()) }
    }
}