package com.gobinda.notepad.main.view.common.binding

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("setErrorResourceId")
fun TextInputLayout.setErrorResourceId(@StringRes resId: Int?) {
    error = resId?.let { context.getString(it) }
}