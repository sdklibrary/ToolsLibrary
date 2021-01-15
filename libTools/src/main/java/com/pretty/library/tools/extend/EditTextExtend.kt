package com.pretty.library.tools.extend

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.textWatcher(textWatcher: MyTextWatcher.() -> Unit) {
    textWatcher.invoke(MyTextWatcher(this))
}

class MyTextWatcher(view: EditText) {

    private var afterText: (String.() -> Unit)? = null

    private var beforeText: ((s: String, start: Int, count: Int, after: Int) -> Unit)? = null

    private var onText: ((s: String, start: Int, before: Int, count: Int) -> Unit)? = null

    fun afterTextChanged(afterText: String.() -> Unit) {
        this.afterText = afterText
    }

    fun beforeTextChanged(beforeText: (s: String, start: Int, count: Int, after: Int) -> Unit) {
        this.beforeText = beforeText
    }

    fun onTextChanged(onText: (s: String, start: Int, before: Int, count: Int) -> Unit) {
        this.onText = onText
    }

    init {
        view.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                afterText?.invoke(s?.toString() ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforeText?.invoke(s?.toString() ?: "", start, count, after)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onText?.invoke(s?.toString() ?: "", start, before, count)
            }

        })
    }
}