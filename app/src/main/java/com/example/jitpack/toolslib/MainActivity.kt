package com.example.jitpack.toolslib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pretty.library.tools.extend.isUrl

class MainActivity : AppCompatActivity() {

    private val data = "http://192.168.2.135:8080/XPARK_Alpha_1.1.0.a2205.apk"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("debug", "isUrl = ${data.isUrl()}")
    }
}