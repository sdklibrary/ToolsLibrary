package com.pretty.library.tools.utils

import android.content.Context
import android.net.*
import android.os.Build
import androidx.annotation.RequiresPermission


@Suppress("DEPRECATION")
object NetworkUtils {
    /**
     * 判断网络是否可用
     * @author Arvin.xun
     * @return Int 0->无网络; 1-> 手机网; 2-> Wifi
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun obtainNetworkType(context: Context): Int {
        val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        return if (Build.VERSION.SDK_INT < 23) {
            connectivity.activeNetworkInfo?.let {
                when (it.type) {
                    ConnectivityManager.TYPE_WIFI -> 2
                    ConnectivityManager.TYPE_MOBILE -> 1
                    else -> 0
                }
            } ?: 0
        } else {
            connectivity.activeNetwork?.let { connectivity.getNetworkCapabilities(it) }?.let {
                when {
                    it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> 2
                    it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> 1
                    else -> 0
                }
            } ?: 0
        }
    }

    /**
     * 手机网是否可用
     * @author Arvin.xun
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isMobileNetworkEnable(context: Context) = obtainNetworkType(context) == 1

    /**
     * wifi是否可用
     * @author Arvin.xun
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isWifiEnable(context: Context) = obtainNetworkType(context) == 2

    /**
     * 网络是否可用
     * @author Arvin.xun
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkEnable(context: Context) = obtainNetworkType(context) > 0

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun network(context: Context) {
        val connectivity = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val builder = NetworkRequest.Builder()
        val request = builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI) //表示此网络使用Wi-Fi传输
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR) //表示此网络使用蜂窝传输
            .build()

        connectivity.registerNetworkCallback(request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    //网络可用
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    //当网络即将断开时调用。通常与新替换网络的呼叫配对，
                    //以实现优雅的切换。如果我们有严重损失*（损失而没有警告），则可能无法调用此方法。
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    ////当框架的网络严重中断或正常故障结束时调用
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    //如果在指定的超时时间内未找到网络，则调用
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    //当网络连接到此请求的框架*更改功能但仍满足规定的需求时调用。
                }

                override fun onLinkPropertiesChanged(
                    network: Network,
                    linkProperties: LinkProperties
                ) {
                    super.onLinkPropertiesChanged(network, linkProperties)
                    //当与该请求连接的框架网络更改时调用。
                }

                override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
                    super.onBlockedStatusChanged(network, blocked)
                    //当对指定网络的访问被阻止或取消阻止时调用
                }
            })
    }

}