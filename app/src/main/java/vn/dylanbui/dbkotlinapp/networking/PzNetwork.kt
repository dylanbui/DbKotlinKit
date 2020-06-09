package vn.dylanbui.dbkotlinapp.networking

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.dylanbui.android_core_kit.DictionaryType
import vn.dylanbui.android_core_kit.mvp_structure.bgDispatcher
import vn.dylanbui.android_core_kit.mvp_structure.uiDispatcher
import vn.dylanbui.android_core_kit.networking.*
import vn.dylanbui.android_core_kit.utils.dLog


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/2/20
 * To change this template use File | Settings | File and Code Templates.
 */

// Dal la demo class Network support for Propzy

data class TripleResult<T>(val list: T?, val totalItems: Int, val totalPages: Int)

class PzResponse: DbResponse() {

    override fun parseJsonResult(jsonString: String) {

    }

}

object PzNetwork : DbNetwork<PzResponse>(PzResponse::class.java) {

    // Set at Application()
    override fun initWithBaseUrl(baseUrl: String, cacheManager: ICacheManager?, debugMode: Boolean) {
        this.baseUrl = baseUrl
        this.cacheManager = cacheManager
        this.showDebug = debugMode
    }

    // --- Network Utility Functions custom For Propzy

//    inline fun <reified T> doRequest(
//        path: String, method: DbNetworkMethod, params: DictionaryType? = null,
//        threadCallback: CoroutineDispatcher = uiDispatcher,
//        crossinline onResponse: (responseBody: T?, errorData: DbNetworkError?) -> Unit
//    ) = launch(bgDispatcher) {
//
//        val request = DbNetworkRequest(path, method)
//        request.params = params
//
//        // -- Call Synchronously Task --
//        val (responseBody, error) = doBasicExecute<T>(request)
//
//        // Update on thread, default is UI Thread
//        withContext(threadCallback) {
//            onResponse(responseBody, error)
//        }
//    }

    inline fun <reified T> doRequestList(
        path: String, method: DbNetworkMethod, params: DictionaryType? = null,
        threadCallback: CoroutineDispatcher = uiDispatcher,
        crossinline onResponse: (responseBody: TripleResult<T>?, errorData: DbNetworkError?) -> Unit
    ) = launch(bgDispatcher) {

        val request = DbNetworkRequest(path, method)
        request.params = params

        // -- Call Synchronously Task --
        val (responseBody, error) = doExecutePaging<T>(request)

        // Update on thread, default is UI Thread
        withContext(threadCallback) {
            onResponse(responseBody, error)
        }
    }

    suspend inline fun <reified T> doExecutePaging(request: DbNetworkRequest)
            : Pair<TripleResult<T>?, DbNetworkError?> {

        var result: TripleResult<T>? = null
        var returnError: DbNetworkError? = null
        // -- Call Synchronously Task --
        val (responseBody, error) = doExecute(request)

        if (responseBody != null) {
            if (responseBody.data is JsonObject) {
                // Kiem tra dung loai du lieu
                val elementList = responseBody.data?.asJsonObject?.get("list")
                val totalItems = responseBody.data?.asJsonObject?.get("totalItems")
                val totalPages = responseBody.data?.asJsonObject?.get("totalPages")
                if (elementList != null && totalItems != null && totalPages != null) {
                    // val resultTT: T? = Gson().fromJson(elementList, object : TypeToken<T>() {}.type)
                    // val resultT: T? = DbJson.instance.fromJson(elementList, object : TypeToken<T>() {}.type)
                    try {
                        val resultT: T? = Gson().fromJson(elementList, object : TypeToken<T>() {}.type)
                        result = TripleResult(resultT, totalItems?.asInt ?: 0, totalPages?.asInt ?: 0)
                    } catch (e: JsonParseException) {
                        returnError = DbNetworkError(
                            404,
                            "JsonParseException: " + e.message
                        )
                    }
                } else {
                    returnError = DbNetworkError(404, "Json Parse Error")
                }
            } else {
                returnError = DbNetworkError(404, "Json Parse Error")
            }
        } else {
            dLog("${error?.errorMessage}")
            // return Pair(null, error)
            returnError = error
        }

        return Pair(result, returnError)
    }

//    fun doRequestBasic(
//        path: String, method: DbNetworkMethod, params: DictionaryType? = null,
//        threadCallback: CoroutineDispatcher = uiDispatcher,
//        onResponse: (cloudResponse: PzResponse?, errorData: DbNetworkError?) -> Unit
//    ) = launch(bgDispatcher) {
//
//        val request = DbNetworkRequest(path, method)
//        request.params = params
//
//        // -- Call Synchronously Task --
//        val (responseBody, error) = doExecute(request)
//
//        // Update on thread, default is UI Thread
//        withContext(threadCallback) {
//            onResponse(responseBody, error)
//        }
//    }


}

