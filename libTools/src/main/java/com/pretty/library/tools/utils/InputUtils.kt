package com.pretty.library.tools.utils

import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.regex.Pattern

object InputUtils {

    /**
     * 隐藏键盘
     * @return Arvin.xun
     */
    fun closeSoftInput(view: View) {
        val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 打开键盘
     * @return Arvin.xun
     */
    fun showSoftInput(context: Activity) {
        val view = context.window.peekDecorView()
        if (view != null) {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, 0)
        }
    }

    /**
     * 禁止输入表情
     * @return Arvin.xun
     */
    fun filterEmoji(callback: (() -> Unit)? = null): InputFilter {
        val emoji = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE shr Pattern.CASE_INSENSITIVE
        )

        return InputFilter { source, _, _, _, _, _ ->
            val matcher = emoji.matcher(source)
            if (matcher.find()) {
                callback?.invoke()
                ""
            } else
                source
        }
    }

    /**
     * 禁止输入匹配正则的字符
     * @author Arvin.xun
     */
    fun filterRegular(regular: String, callback: (() -> Unit)? = null): InputFilter {
        val regularRule = Pattern.compile(regular, Pattern.UNICODE_CASE shr Pattern.CASE_INSENSITIVE)
        return InputFilter { source, _, _, _, _, _ ->
            val matcher = regularRule.matcher(source)
            if (matcher.find()) {
                callback?.invoke()
                ""
            } else
                source
        }
    }

    /**
     * 禁止输入空格
     * @author Arvin.xun
     */
    fun filterSpace(callback: (() -> Unit)? = null): InputFilter {
        return InputFilter { source, _, _, _, _, _ ->
            if (source == " ") {
                callback?.invoke()
                ""
            } else
                source
        }
    }

}