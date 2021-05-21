package com.pretty.library.tools.helper

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*

@Suppress("DEPRECATION")
object LanguageHelper {

    /**
     * 切换app语言不会修改系统语言
     * @author Arvin.xun
     */
    fun switch(context: Context, language: LanguageHelper.() -> Locale) {
        val languageLocale = language.invoke(this)
        val resources = context.resources
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(languageLocale)
            val localeList = LocaleList(languageLocale)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
            context.createConfigurationContext(configuration)
        } else
            configuration.setLocale(languageLocale)
        resources.updateConfiguration(configuration, metrics)
    }

    /**
     * 当前系统使用的语言
     * @author Arvin.xun
     */
    fun getCurrent(context: Context): Locale {
        //获取应用语言
        val resources = context.resources
        val configuration = resources.configuration
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            configuration.locales[0]
        else
            configuration.locale
    }

    /**
     * 是否需要更新语言
     * @author Arvin.xun
     */
    fun needUpdate(
        context: Context,
        language: LanguageHelper.() -> Locale
    ): Boolean {
        return getCurrent(context) != language.invoke(this)
    }
}