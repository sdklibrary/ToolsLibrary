package com.pretty.library.tools.extend

import android.view.ViewGroup
import android.webkit.WebView


fun WebView.release() {
    val parent = this.parent
    if (parent != null && parent is ViewGroup)
        parent.removeView(this)
    this.stopLoading()
    this.settings.javaScriptEnabled = false
    this.clearHistory()
    this.removeAllViews()
    this.destroy()
}

