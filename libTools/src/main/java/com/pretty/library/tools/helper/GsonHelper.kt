package com.pretty.library.tools.helper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pretty.library.tools.utils.LogUtils

object GsonHelper {

    val gson by lazy { Gson() }

    /**
     * 将json数据转化为实体数据
     * @param json  json格式数据
     * @author Arvin.xun
     */
    inline fun <reified T> parseEntity(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            LogUtils.e(e.message ?: "")
            T::class.java.newInstance()
        }
    }

    /**
     * 将json数据转化为实体数据
     * @param json  json格式数据
     * @param clazz 实体类
     * @author Arvin.xun
     */
    fun <T> parseEntity(json: String, clazz: Class<T>): T {
        return try {
            gson.fromJson(json, clazz)
        } catch (e: Exception) {
            LogUtils.e(e.message ?: "")
            clazz.newInstance()
        }
    }

    /**
     * 将对象转为 Json String
     * @author Arvin.xun
     */
    fun <T> parseJson(entity: T): String {
        return gson.toJson(entity)
    }

}