package com.pretty.library.tools.extend

import android.graphics.Paint
import android.graphics.Typeface
import android.widget.TextView

/**
 * 是否对TextView字体加粗
 * @author Arvin.xun
 */
fun TextView.isBold(isBold: Boolean) {
    this.typeface = if (isBold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
}

/**
 * 设置中划线
 * @author Arvin.xun
 */
fun TextView.centerLine() {
    val paint = this.paint
    paint.isAntiAlias = true //抗锯齿
    paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG // 设置中划线并加清晰
}

/**
 * 设置下划线
 * @author Arvin.xun
 */
fun TextView.underLine() {
    val paint = this.paint
    paint.isAntiAlias = true //抗锯齿
    paint.flags = Paint.UNDERLINE_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG //下划线
}

/**
 * 取消下划线 中划线
 * @author Arvin.xun
 */
fun TextView.cancelLine() {
    this.paint.flags = 0
}