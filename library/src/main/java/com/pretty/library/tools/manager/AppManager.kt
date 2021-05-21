package com.pretty.library.tools.manager

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import androidx.annotation.RequiresPermission
import java.util.*
import kotlin.system.exitProcess

object AppManager {

    private val activityStack by lazy {
        Stack<Activity>()
    }

    /**
     * 添加Activity到堆栈
     * @author Arvin.xun
     */
    fun addActivity(activity: Activity?) {
        activityStack.add(activity)
    }

    /**
     * 移除堆栈
     * @author Arvin.xun
     */
    fun removeActivity(activity: Activity?) {
        if (activityStack.isEmpty())
            return
        activityStack.remove(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     * @author Arvin.xun
     */
    fun currentActivity(): Activity? {
        return activityStack.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     * @author Arvin.xun
     */
    fun finishActivity() {
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     * @author Arvin.xun
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
    }

    /**
     * 结束指定类名的Activity
     * @author Arvin.xun
     */
    fun finishActivity(cls: Class<*>) {
        activityStack.forEach { activity ->
            if (activity != null && activity.javaClass == cls)
                activity.finish()
        }
    }

    /**
     * 结束所有Activity
     * @author Arvin.xun
     */
    fun finishAllActivity() {
        activityStack.forEach { activity ->
            activity?.finish()
        }
    }

    /**
     * 结束其他所有activity
     * @author Arvin.xun
     */
    fun finishOtherActivity(cls: Class<*>) {
        for (i in activityStack.indices) {
            val activity = activityStack[i]
            if (activity!!.javaClass != cls)
                finishActivity(activity)
        }
    }

    /**
     * @author Arvin.xun
     */
    fun findTopIsActivity(cls: Class<*>): Boolean {
        val firstActivity = activityStack.firstElement()
        return firstActivity!!.javaClass == cls
    }

    /**
     * 重启其他activity
     * @author Arvin.xun
     */
    fun recreateAllOtherActivity(activity: Activity) {
        activityStack.forEach {
            if (it != activity)
                it.recreate()
        }
    }

    /**
     * 退出应用程序
     * @author Arvin.xun
     */
    @RequiresPermission(Manifest.permission.KILL_BACKGROUND_PROCESSES)
    fun exitApp(context: Context) {
        try {
            val manager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(context.packageName)
        } catch (e: Exception) {
        }
        exitProcess(0)
    }
}