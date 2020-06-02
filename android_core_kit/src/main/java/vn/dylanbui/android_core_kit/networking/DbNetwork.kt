package vn.dylanbui.android_core_kit.networking

import android.util.Log
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import vn.dylanbui.android_core_kit.DictionaryType
import vn.dylanbui.android_core_kit.utils.DbJson
import vn.dylanbui.android_core_kit.utils.dLog

import java.util.concurrent.TimeUnit

internal class SimpleResponse {

}

internal interface INetworkService {

    // @Headers("Content-Type: application/json")
    @GET
    suspend fun makeGetRequest(@Url url: String?): Response<SimpleResponse>

    @POST
    suspend fun makePostRequest(@Url url: String?, @Body requestBody: RequestBody?): Response<SimpleResponse>

    @PUT
    suspend fun makePutRequest(@Url url: String?, @Body requestBody: RequestBody?): Response<SimpleResponse>

}

// typealias DbPairResponse = Pair<DbResponse?, DbNetworkError?>

// open class DbNetwork<M: DbResponse>(private var modelClass: Class<M>, private var baseUrl: String = "") {
open class DbNetwork<M: DbResponse>(var baseUrl: String = "") {

    // private var makeLink: (String) -> String = { url -> url}
    // var makeLink: ((String) -> String)? = null
    var cacheManager: ICacheManager? = null
    var showDebug: Boolean = false

    init {

    }

    // private var BASE_URL = "" //"http://45.117.162.60:8080/diy/api/"

    private val makeApiService: INetworkService by lazy {
        // -- Log for Retrofit --
        val httpLoggingInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    // Platform.get().log(Platform.INFO, message, null)
                    Log.e("Network", message)
                }
            }
        )
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        if (showDebug) {
            // client.addInterceptor(httpLoggingInterceptor)
            client.addNetworkInterceptor(httpLoggingInterceptor)
        }

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()

        // Create Retrofit client
        return@lazy retrofit.create(INetworkService::class.java)
    }

    // --- Network Suspend Functions

    suspend inline fun <reified T> doBasicExecute(request: DbNetworkRequest): Pair<T?, DbNetworkError?> {

        var resultT: T? = null
        var returnError: DbNetworkError? = null
        // -- Call Synchronously Task --
        val (responseBody, error) = doExecute(request)

        if (error != null) {
            dLog("${error.errorMessage}")
            return Pair(null, error)
        }

        if (responseBody != null) {
            try {
                resultT = DbJson.instance.fromJson(responseBody.data, object : TypeToken<T>() {}.type)
            } catch (e: JsonParseException) {
                returnError = DbNetworkError(404, "JsonParseException: " + e.message)
            }
        } else {
            dLog("${error?.errorMessage}")
            // return Pair(null, error)
            returnError = error
        }

        return Pair(resultT, returnError)
    }


    // Ket qua tra ve luc nay, response da co gia tri, khong can kiem tra loi trong response
    suspend fun doExecute(request: DbNetworkRequest): Pair<M?, DbNetworkError?> {

        // Xu ly ket qua tra ve
        var result: M? = null
        var error: DbNetworkError? = null

        // Xu ly ket qua tra ve
        val resultJson: String? = readResponseCache(request.cache)
        if (resultJson != null) {
            // val entity: M = modelClass.newInstance()
            val entity: M = DbJson.instance.fromJson(resultJson, object : TypeToken<M>() {}.type)
            entity.parseJsonResult(resultJson)
            // Success get from cache
            return Pair(entity, null)
        }

        // Default set Method GET
        val response: Response<SimpleResponse> = makeCall(request.path, request.method, request.params)

        try {
            // if (response?.code() in 400..511)
            val responseJsonBody: String? = response.body() as? String
            if (response.isSuccessful && responseJsonBody != null) {
                // Kiem tra loi trong body response
                // Make response instance and parse json
                val entity: M = DbJson.instance.fromJson(responseJsonBody, object : TypeToken<M>() {}.type)
                // val entity: M = modelClass.newInstance()
                entity.parseJsonResult(responseJsonBody)
                // Ket qua tra ve dung server, dung ket qua
                if (entity.result == true) {
                    error = null
                    result = entity
                    // Write to cache if need
                    writeResponseCache(request.cache, responseJsonBody)
                } else {
                    error = DbNetworkError(entity.code ?: 404, "${entity.message}")
                }
            } else { // Loi dac biet tu response
                error = DbNetworkError(response.code(), "${response.message()}")
            }
        } catch (e: HttpException) {
            error = DbNetworkError(response.code(), "${response.message()}")
        } catch (e: Throwable) {
            error = DbNetworkError(404, "Throwable: ${e.message}")
        }

        return Pair(result, error)
    }

    // Ham thuc hien cuoc goi va tra ve Response
    private suspend fun makeCall(path: String,
                                 method: DbNetworkMethod,
                                 params: DictionaryType? = null): Response<SimpleResponse> {
        // Default set Method GET
        lateinit var call: Response<SimpleResponse> //= makeApiService.makeGetRequest(path)

        // Luc nay da thuc hien cuoc goi
        when (method) {
            DbNetworkMethod.GET -> {
                call = makeApiService.makeGetRequest(path)
            }
            DbNetworkMethod.POST -> {
                var requestBody: RequestBody? = null
                params?.let {
                    val jsonObject = JSONObject(it)
                    requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                }

                call = makeApiService.makePostRequest(path, requestBody)
            }
            DbNetworkMethod.PUT -> {
                var requestBody: RequestBody? = null
                params?.let {
                    val jsonObject = JSONObject(it)
                    requestBody = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                }
                call = makeApiService.makePutRequest(path, requestBody)
            }
        }
        return call
    }

    // TODO: phai kiem tra lai toan bo cho nay, Toan bo qua trinh goi deu la Synchronously

    fun cacheExisted(request: DbNetworkRequest): Boolean {
        if (request.cache != null) {
            return cacheManager?.existed(request.cache!!.key) ?: false
        }
        return false
    }

    // Read string json response from cache
    private fun readResponseCache(cache: DbNetworkCache?): String? {
        if (cache != null && !cache.refresh) {
            cacheManager?.let {
                return it.get(cache.key) as? String
//                if (str != null) {
//                    return DbJson.instance.fromJson(str, object : TypeToken<M>() {}.type)
//                }
            }
        }
        return null
    }
    // Write string json response to cache database
    private fun writeResponseCache(cache: DbNetworkCache?, value: String): Boolean {
        if (cache != null) {
            cacheManager?.let {
                return it.put(cache.key, value, cache.lifeTime, cache.tag)
            }
        }
        return false
    }


}


