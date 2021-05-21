package com.pretty.library.tools.extend

import android.view.View
import android.widget.TextView

/**
 * 获取view的文本
 * @author Arvin.xun
 */
fun View.toText(): String {
    return if (this is TextView)
        this.text?.toString()?.trim() ?: ""
    else
        ""
}

/**
 * 获取view的Tag字符串
 * @author Arvin.xun
 */
fun View.toTag(): String {
    return this.tag?.toString()?.trim() ?: ""
}

/**
 * 获取view的宽
 * @author Arvin.xun
 */
fun View.width(): Int {
    return this.measuredWidth
}

/**
 * 获取view的高
 * @author Arvin.xun
 */
fun View.height(): Int {
    return this.measuredHeight
}

/**
 * 防止快速双击
 * @author Arvin.xun
 */
fun View.clickDelay(delayTime: Long = 1000, block: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() == ViewObject.lastClickViewHashCode) {
            ViewObject.lastClickTime = System.currentTimeMillis()
            block.invoke()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - ViewObject.lastClickTime > delayTime) {
                ViewObject.lastClickTime = System.currentTimeMillis()
                block.invoke()
            }
        }
    }
}


object ViewObject {
    var lastClickViewHashCode = 0
    var lastClickTime: Long = 0
}