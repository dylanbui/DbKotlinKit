package vn.dylanbui.dbkotlinapp.networking

import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import vn.propzy.android_core_kit.DbError
import vn.propzy.android_core_kit.DictionaryType

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/2/20
 * To change this template use File | Settings | File and Code Templates.
 */

// Use for cache response
interface ICacheManager {

    fun existed(key: String): Boolean

    fun get(key: String): Any?
    // lifeTime is second
    fun put(key: String, value: Any, lifeTime: Int, tag: String? = null): Boolean

    fun remove(key: String)

    fun removeTag(tag: String) {}

    fun removeAll() {}

    fun removeExpiryKey() {}

}

enum class DbNetworkMethod(val method: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
}

class DbNetworkError(override var errorCode: Int, override var errorMessage: String) : DbError

// typealias DbPairResponse = Pair<DbResponse?, DbNetworkError?>

// data class TripleResult<T>(val list: T?, val totalItems: Int, val totalPages: Int)

class DbNetworkRequest(var path: String, var method: DbNetworkMethod = DbNetworkMethod.GET) {
    var params: DictionaryType? = null
    var cache: DbNetworkCache? = null
}

// For cache

data class DbNetworkCache(var key: String, var lifeTime: Int, var tag: String? = null, var refresh: Boolean = false)

class DbNetworkRequestCache(var path: String, var method: DbNetworkMethod = DbNetworkMethod.GET) {
    var params: DictionaryType? = null
    var cache: DbNetworkCache? = null
}

// Default response from server
abstract class DbResponse {

    @SerializedName("result")
    @Expose
    var result: Boolean? = false

    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: JsonElement? = null // Phai la JsonElement (co the chua JsonObject, JsonArray, JsonNULL) khong de Any (Kotlin)

    var rawJsonData: String? = null

    abstract fun parseJsonResult(jsonString: String)

//    abstract fun parseJsonResult(jsonString: String) {
//        this.rawJsonData = jsonString
//    }
}
