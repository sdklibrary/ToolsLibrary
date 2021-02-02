package com.pretty.library.tools.lifecycle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * @Author: Arvin.xum
 * @Class: LifecycleBroadcastReceiver
 */
class LifecycleBroadcastReceiver(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val receiver: (intent: Intent?) -> Unit
) : BroadcastReceiver(), LifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        receiver.invoke(intent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        context.unregisterReceiver(this)
        lifecycleOwner.lifecycle.removeObserver(this)
    }
    
}