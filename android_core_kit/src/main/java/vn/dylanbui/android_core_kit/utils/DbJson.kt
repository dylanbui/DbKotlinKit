package vn.dylanbui.android_core_kit.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.convertFromJson(jsonElement: JsonElement): T? {
    // this.fromJson<T>(json, object: TypeToken<T>() {}.type)
    var resultT: T? = null
    try {
        resultT = Gson().fromJson(jsonElement, object : TypeToken<T>() {}.type)
    } catch (e: JsonParseException) {
        dLog("JsonParseException : $e")
    }
    return resultT
}

object DbJson {

    private var gson: Gson? = null

    @JvmStatic
    val instance: Gson
        @Synchronized get() {
            if (gson == null) {
                val gsonTemp = GsonBuilder()
                gson = gsonTemp.create()
            }
            return gson!!
        }

//    https://mkyong.com/java/how-to-parse-json-with-gson/
//    https://wajahatkarim.com/2018/11/converting-jsonobject-to-hashmap/
//    https://gist.github.com/codebutler/2339666

//    fun <T> convertFromJson(jsonElement: JsonElement): T? {
//        var resultT: T? = null
//        try {
//            resultT = Gson().fromJson(jsonElement, object : TypeToken<T>() {}.type)
//        } catch (e: JsonParseException) {
//            dLog("JsonParseException : $e")
//        }
//        return resultT
//    }

//    https://mkyong.com/java/how-to-parse-json-with-gson/
//    https://wajahatkarim.com/2018/11/converting-jsonobject-to-hashmap/
//    https://gist.github.com/codebutler/2339666


//    @Throws(JsonSyntaxException::class)
//    fun <T> fromJson(json: String?, classOfT: Class<T>): T? {
//        return instance.fromJson(json, classOfT)
//    }
//
//    @Throws(JsonSyntaxException::class)
//    fun <T> fromJsonElement(json: JsonElement?, classOfT: Class<T>): T? {
//        return instance.fromJson(json, classOfT)
//    }
//
//    fun toJson(src: Any?): String? {
//        return instance.toJson(src)
//    }



}