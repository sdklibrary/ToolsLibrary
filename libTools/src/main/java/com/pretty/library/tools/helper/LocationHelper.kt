package com.pretty.library.tools.helper

import android.Manifest.permission
import android.content.Context
import android.location.*
import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import java.util.*

/**
 * 原生定位
 * LiveData 实现 返回
 * @author Arvin.xun
 */
class LocationHelper private constructor(
    private val context: Context,
    private val timeOut: Long,
    private val criteria: Criteria?
) : LiveData<Pair<Int, Address?>>() {

    private val handler by lazy {
        Handler(Looper.myLooper()!!)
    }

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @Suppress(names = ["MissingPermission"])
    private val timeTask = Runnable {
        locationManager.removeUpdates(listener)
        value = Pair(100, null)
    }

    private val listener by lazy {
        object : LocationListener {
            @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
            override fun onLocationChanged(location: Location) {
                locationManager.removeUpdates(this)
                decodeLocation(location)
            }
        }
    }

    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun onActive() {
        val provider = if (criteria == null) {
            val criteria = Criteria()
            criteria.isCostAllowed = false
            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.powerRequirement = Criteria.POWER_LOW
            criteria
        } else {
            criteria
        }.let {
            locationManager.getBestProvider(it, true) ?: LocationManager.NETWORK_PROVIDER
        }
        val location = locationManager.getLastKnownLocation(provider)
        if (location != null)
            decodeLocation(location)
        else {
            locationManager.requestLocationUpdates(provider, 0, 0f, listener)
            handler.postDelayed(timeTask, timeOut)
        }
    }

    @Suppress(names = ["MissingPermission"])
    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun onInactive() {
        handler.removeCallbacks(timeTask)
        locationManager.removeUpdates(listener)
    }

    /**
     * 解码地址
     */
    private fun decodeLocation(location: Location) {
        val gc = Geocoder(context, Locale.getDefault())
        val addressList = gc.getFromLocation(location.latitude, location.longitude, 1)
        val address = if (addressList.size > 0) addressList[0] else null
        val status = if (address == null) 400 else 200
        value = Pair(status, address)
    }

    companion object {

        @Volatile
        private lateinit var sInstance: LocationHelper

        /**
         * 获取LocationHelper实例
         * @author Arvin.xun
         * @param context Context 上下文实例
         * @param timeOut Long 超时时间 默认2000毫秒
         * @param criteria Criteria 策略
         */
        @MainThread
        operator fun get(
            context: Context,
            timeOut: Long = 2000,
            criteria: Criteria? = null
        ): LocationHelper {
            if (!::sInstance.isInitialized)
                sInstance = LocationHelper(context.applicationContext, timeOut, criteria)
            return sInstance
        }
    }

}