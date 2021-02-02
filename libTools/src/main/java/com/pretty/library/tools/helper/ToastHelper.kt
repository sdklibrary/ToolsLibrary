package com.pretty.library.tools.helper

import android.content.Context
import android.widget.Toast

object ToastHelper {

    fun showToast(
        context: Context,
        message: String = "",
        block: (Toast.() -> Unit)? = null
    ) {
        if (message == "")
            return
        val toast = Toast.makeText(context.applicationContext, message, Toast.LENGTH_SHORT)
        block?.invoke(toast)
        toast.show()
    }

    fun showToast(
        context: Context,
        message: String = "",
        status: Int = 0,
        block: (Toast.(message: String, status: Int) -> Unit)? = null
    ) {
        if (message == "")
            return
        val toast = Toast(context.applicationContext)
        block?.invoke(toast, message, status)
        toast.show()
    }
}