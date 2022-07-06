package com.beautymnl.exam.core.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getText() : String? {
    return editText?.text?.toString()
}

fun TextInputLayout.setText(text: CharSequence)  {
    editText?.setText(text)
}