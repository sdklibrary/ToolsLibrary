package com.pretty.library.tools.extend

import android.webkit.URLUtil
import java.util.regex.Pattern

/**
 * 地址是否是http开始
 * @author Arvin.xun
 */
fun String.isUrl(): Boolean {
    return URLUtil.isNetworkUrl(this)
}

/**
 * 地址是否是gif
 * @author Arvin.xun
 */
fun String.isGif() = this.endsWith("gif")

/**
 * 是否是邮箱
 * @author Arvin.xun
 */
fun String.isEmail(): Boolean {
    val regExp =
        "^([a-zA-Z0-9]+[-_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-_|.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}$"
    return Pattern.compile(regExp).matcher(this).matches()
}

/**
 * 是否是中国身份证号 15 或者 18位
 * @author Arvin.xun
 */
fun String.isIdCard(): Boolean {
    val regExp = "^\\d{15}|\\d{17}([0-9]|X)$"
    return Pattern.compile(regExp).matcher(this).matches()
}

/**
 * 是否匹配规则
 * @author Arvin.xun
 */
fun String.matcher(regExp: String): Boolean {
    return Pattern.compile(regExp).matcher(this).matches()
}

/**
 * 去除Html标签
 * @author Arvin.xun
 */
fun String.clearHtmlTag(): String {
    val pattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.replaceAll("")
}
