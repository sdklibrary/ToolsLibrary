package com.pretty.library.tools.utils

import android.util.Log

object LogUtils {

    private var LOG_TAG = "log"
    private var isDebug = false

    fun init(debug: Boolean) {
        isDebug = debug
    }

    fun init(debug: Boolean, logTag: String) {
        LOG_TAG = logTag
        isDebug = debug
    }

    fun i(msg: String) {
        if (isDebug)
            Log.i(LOG_TAG, msg)
    }

    fun d(msg: String) {
        if (isDebug) Log.d(LOG_TAG, msg)
    }

    fun e(msg: String) {
        if (isDebug) Log.e(LOG_TAG, msg)
    }

    fun v(msg: String) {
        if (isDebug) Log.v(LOG_TAG, msg)
    }

    fun w(msg: String) {
        if (isDebug) Log.w(LOG_TAG, msg)
    }
}