package com.pretty.library.tools.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

@Suppress("DEPRECATION")
object AppUtil {

    /**
     * 获取application 节点  meta-data 信息
     */
    fun getMetaDataFromApplication(
        mContext: Context,
        key: String?
    ): String? {
        return try {
            val packageManager = mContext.packageManager
            val appInfo = packageManager.getApplicationInfo(
                mContext.packageName,
                PackageManager.GET_META_DATA
            )
            appInfo.metaData.getString(key)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 安装Apk
     * @author Arvin.xun
     */
    fun installApk(
        context: Context,
        apkUri: Uri
    ) {
        val mIntent = Intent(Intent.ACTION_VIEW)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            mIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        mIntent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        context.startActivity(mIntent)
    }

    /**
     * 是否已安装
     * @author Arvin.xun
     */
    fun installedApk(
        context: Context,
        packageName: String
    ): Boolean {
        val pInfo = context.packageManager.getInstalledPackages(0)
        for (i in pInfo.indices) {
            val pn = pInfo[i].packageName
            if (pn == packageName)
                return true
        }
        return false
    }

    /**
     * 卸载apk
     * @author Arvin.xun
     */
    fun uninstallApk(
        context: Context,
        packageName: String
    ) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$packageName")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 判断Service 是否运行
     * @author Arvin.xun
     */
    fun isServiceAlive(
        context: Context,
        serviceName: String
    ): Boolean {
        var isRunning = false
        if (!TextUtils.isEmpty(serviceName)) {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val serviceList = activityManager.getRunningServices(Int.MAX_VALUE)
            for (serviceInfo in serviceList) {
                if (serviceName == serviceInfo.service.className) {
                    isRunning = true
                }
            }
        }
        return isRunning
    }

    /**
     * 停止服务
     * @author Arvin.xun
     */
    fun stopService(
        context: Context,
        serviceName: Class<*>
    ) = context.stopService(Intent(context, serviceName))

    /**
     * 获取已安装包信息
     * @author Arvin.xun
     */
    fun getPackageInfo(context: Context): PackageInfo? {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 获取app名称
     * @author Arvin.xun
     */
    fun getAppName(context: Context): String {
        return try {
            val packInfo = getPackageInfo(context)
            packInfo!!.applicationInfo.loadLabel(context.packageManager).toString()
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 获取版本名称
     * @author Arvin.xun
     */
    fun getVersionName(context: Context): String {
        val packInfo = getPackageInfo(context)
        return packInfo?.versionName ?: ""
    }

    /**
     * 获取版本号
     * @author Arvin.xun
     */
    fun getVersionCode(context: Context): Long {
        return try {
            val packInfo = getPackageInfo(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
                packInfo?.longVersionCode as Long
            else
                packInfo?.versionCode?.toLong() as Long
        } catch (e: Exception) {
            0
        }
    }

    /**
     * 获取进程名称
     * @author Arvin.xun
     */
    fun getProcessName(context: Context, pId: Int): String {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.runningAppProcesses?.let {
            for (processInfo in it) {
                if (processInfo.pid == pId)
                    return@let processInfo.processName
            }
        }
        return ""
    }

    /**
     * GPS 是否开启
     * @author Arvin.xun
     */
    fun isGpsEnabled(mContext: Context): Boolean {
        val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * 切换Fragment
     * @author Arvin.xun
     */
    fun switchFragment(
        fm: FragmentManager,
        contentId: Int,
        target: Fragment?
    ) {
        if (target == null)
            return
        val targetTag = target.javaClass.simpleName
        switchFragment(fm, contentId, target, targetTag)
    }

    /**
     * 切换Fragment
     * @author Arvin.xun
     */
    fun switchFragment(
        fm: FragmentManager,
        contentId: Int,
        target: Fragment?,
        targetTag: String
    ) {
        if (target == null)
            return
        val fragments = fm.fragments
        var currentShow: Fragment? = null
        for (fragment in fragments) {
            if (!fragment.isHidden) {
                currentShow = fragment
                if (currentShow !== target) fm.beginTransaction().hide(fragment)
                    .setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                    .commitAllowingStateLoss()
            }
        }

        if (currentShow === target)
            return
        if (null == fm.findFragmentByTag(targetTag) && !target.isAdded) {
            fm.beginTransaction().add(contentId, target, targetTag)
                .setMaxLifecycle(target, Lifecycle.State.RESUMED)
                .commitAllowingStateLoss()
        } else {
            fm.beginTransaction().show(target)
                .setMaxLifecycle(target, Lifecycle.State.RESUMED)
                .commitAllowingStateLoss()
        }
    }

    /**
     * 打开系统浏览器
     * @author Arvin.xun
     * @param context: Context
     * @param url: String
     * @param title: String
     * @param result: ((code: Int) -> Unit)?   code = 404 -> Indicates that no browser is available
     */
    fun openSystemBrowser(
        context: Context,
        url: String, title: String = "",
        result: ((code: Int) -> Unit)? = null
    ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        try {
            context.startActivity(Intent.createChooser(intent, title))
            result?.invoke(200)
        } catch (e: Exception) {
            result?.invoke(404)
        }
    }
}