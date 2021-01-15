package com.pretty.library.tools.manager

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class SPProperty<T : Any>(
    private val defValue: T,
    private val prefsKey: String = "",
    fileName: String = "",
) : ReadWriteProperty<Any?, T> {

    init {
        if (fileName == "")
            SPManager.fileName = fileName
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val key = findProperName(property)
        return SPManager.obtain(key, defValue)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        val key = findProperName(property)
        SPManager.save(key, value)
    }

    private fun findProperName(property: KProperty<*>): String {
        return if (prefsKey.isEmpty())
            property.name
        else
            prefsKey
    }

}