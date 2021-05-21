package com.pretty.library.tools.manager

import android.content.Context
import com.pretty.library.tools.ContextProvider
import java.io.*
import java.net.URLDecoder
import java.net.URLEncoder
import kotlin.jvm.Throws

@Suppress("UNCHECKED_CAST")
object SPManager {

    var fileName: String = ContextProvider.appContext.packageName

    private val prefs by lazy {
        ContextProvider.appContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun <T : Any> save(key: String, value: T) {
        prefs.edit().apply {
            when (value) {
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                else -> putString(key, serialization(value))
            }
        }.apply()
    }

    fun <T : Any> obtain(key: String, defValue: T): T {
        val res: Any? = when (defValue) {
            is Int -> prefs.getInt(key, defValue)
            is String -> prefs.getString(key, defValue)
            is Long -> prefs.getLong(key, defValue)
            is Float -> prefs.getFloat(key, defValue)
            is Boolean -> prefs.getBoolean(key, defValue)
            else -> {
                val objData = prefs.getString(key, serialization(defValue))
                if (objData.isNullOrBlank())
                    defValue
                else
                    deserialization(objData)
            }
        }
        return if (res == null) defValue else res as T
    }

    /**
     * 序列化
     */
    @Throws(IOException::class)
    private fun <T : Any> serialization(obj: T): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 反序列化对象
     */
    private fun <T : Any> deserialization(str: String): T? = try {
        val redStr = URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val obj = objectInputStream.readObject() as T
        objectInputStream.close()
        byteArrayInputStream.close()
        obj
    } catch (e: Exception) {
        null
    }
}