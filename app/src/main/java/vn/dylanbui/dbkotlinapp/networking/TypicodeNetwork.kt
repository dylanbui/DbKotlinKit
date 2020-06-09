package vn.dylanbui.dbkotlinapp.networking

import android.text.TextUtils
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import vn.dylanbui.android_core_kit.networking.DbNetwork
import vn.dylanbui.android_core_kit.networking.DbResponse
import vn.dylanbui.android_core_kit.networking.ICacheManager
import vn.dylanbui.android_core_kit.utils.DbJson
import vn.dylanbui.android_core_kit.utils.dLog
import vn.dylanbui.dbkotlinapp.App


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/3/20
 * To change this template use File | Settings | File and Code Templates.
 */


class TyResponse: DbResponse() {

    override fun parseJsonResult(jsonString: String) {
        this.result = false
        try {
            // resultT = Gson().fromJson(jsonElement, object : TypeToken<T>() {}.type)
            this.data = DbJson.instance.fromJson(jsonString, JsonElement::class.java)
            this.result = true
        } catch (e: JsonParseException) {
            dLog("JsonParseException : $e")
            this.result = false
            this.code = 404
            this.message = e.message
        }
    }

}


object TypicodeNetwork : DbNetwork<TyResponse>(TyResponse::class.java) {

    init {
        println("Singleton class invoked.")
    }


    override fun initWithBaseUrl(baseUrl: String, cacheManager: ICacheManager?, debugMode: Boolean) {
        this.baseUrl = baseUrl
        this.cacheManager = cacheManager
        this.showDebug = debugMode
    }

    fun makeLink(link: String): String {
        if (App.currentUser != null && !TextUtils.isEmpty(App.currentUser?.token)) {
            return link + "?access_token=${App.currentUser?.token}"
        }
        return link
    }

}