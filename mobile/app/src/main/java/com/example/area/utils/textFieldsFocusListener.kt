package com.example.area.utils

import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun textFieldsFocusListener(view: View, idEditText: Int, idTextLayout: Int, function: (field: String)->Unit) {
    val editText = view.findViewById<TextInputEditText>(idEditText)
    val textLayout = view.findViewById<TextInputLayout>(idTextLayout)
    editText.setOnFocusChangeListener { _, focused ->
        if (!focused) {
            try {
                function(editText.text.toString())
            } catch (e: IllegalArgumentException) {
                textLayout.error = e.message
                return@setOnFocusChangeListener
            }
        }
        textLayout.error = null
    }
}