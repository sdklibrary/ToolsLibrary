package com.pretty.library.tools.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

@Suppress("DEPRECATION")
object StatusBarUtil {

    /**
     * 全屏模式
     * @author Arvin.xun
     * @param activity 全屏的 Activity
     * @param isDark 状态栏深色
     */
    fun fullScreen(activity: Activity, isDark: Boolean) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            var option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            if (isDark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    option =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                else
                    window.statusBarColor = 0x60000000
            }
            decorView.systemUiVisibility = option
        } else {
            val attributes = window.attributes
            val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            attributes.flags = attributes.flags or flagTranslucentStatus
            window.attributes = attributes
        }
    }

    /**
     * 从新设置沉侵View的参数
     * @author Arvin.xun
     */
    fun immersionView(view: View): Int {
        val statusBarHeight = ScreenUtil.statusBarHeight()
        val params = view.layoutParams
        params.height += statusBarHeight
        view.setPadding(
            view.paddingLeft, view.paddingTop + statusBarHeight,
            view.paddingRight, view.paddingBottom
        )
        return params.height
    }

}