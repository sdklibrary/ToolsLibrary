package com.pretty.library.tools.utils

import com.pretty.library.tools.ContextProvider

/**
 * 屏幕工具类
 */
object ScreenUtil {

    /**
     * 获取屏幕宽
     * @author Arvin.xun
     */
    fun screenWidth(): Int {
        val resources = ContextProvider.appContext.resources
        val displayMetrics = resources.displayMetrics
        return displayMetrics.widthPixels
    }

    /**
     * 获得屏幕高度
     * @author Arvin.xun
     */
    fun screenHeight(): Int {
        val resources = ContextProvider.appContext.resources
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels
    }

    /**
     * 获取导航栏高度
     * @author Arvin.xun
     */
    fun navigationBarHeight(): Int {
        val resources = ContextProvider.appContext.resources
        val showBarId = resources.getIdentifier("config_showNavigationBar", "bool", "android")
        return if (showBarId != 0) {
            val barId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            resources.getDimensionPixelSize(barId)
        } else 0
    }

    /**
     * 获得状态栏的高度
     * @author Arvin.xun
     */
    fun statusBarHeight(): Int {
        val resources = ContextProvider.appContext.resources
        val resourcesId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourcesId)
    }
}