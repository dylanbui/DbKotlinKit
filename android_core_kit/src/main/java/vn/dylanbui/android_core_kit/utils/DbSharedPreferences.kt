package vn.dylanbui.android_core_kit.utils

import android.content.Context
import android.content.SharedPreferences

object DbSharedPreferences {

    private lateinit var prefs: SharedPreferences

    fun initWithAppContext(context: Context) {
        prefs = context.getSharedPreferences("DbSharedPreferences", Context.MODE_PRIVATE)
    }

    fun setBoolean(title: String, boole: Boolean) {
        prefs.edit().putBoolean(title, boole).commit()
    }

    fun getBoolean(title: String): Boolean {
        var result = false
        try {
            result = prefs.getBoolean(title, false)
        } catch (e: Exception) {

        }
        return result
    }

    fun setString(title: String, str: String) {
        prefs.edit().putString(title, str).commit()
    }

    fun getString(title: String): String? {
        return prefs.getString(title, null)
    }

    fun setInt(title: String, value: Int) {
        prefs.edit().putInt(title, value).commit()
    }

    fun getInt(title: String): Int? {
        val num = prefs.getInt(title, -9999)
        return if (num == -9999) null else num
    }

    fun clearKey(key: String) {
        prefs.edit().remove(key).commit();
    }

    fun setObject(name: String, obj: Any) {
        val prefsEditor = prefs.edit()
        val json = DbJson.instance.toJson(obj)  //Gson().toJson(obj)
        prefsEditor.putString(name, json)
        prefsEditor.commit()
    }

    fun <T : Any> getObject(name: String, type: Class<T>): T? {
        try {
            if (prefs != null) {
                val json = prefs.getString(name, "")
                // dLog(json)
                return DbJson.instance.fromJson(json, type)
            }
        } catch (e: Exception) {
            dLog(e.toString())
        }
        return null
    }

}