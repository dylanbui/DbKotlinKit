package vn.dylanbui.dbkotlinapp.app_network

import android.util.Log
import vn.dylanbui.dbkotlinapp.app_models.TyCommentUnit
import vn.dylanbui.dbkotlinapp.app_models.TyPhotoUnit
import vn.dylanbui.dbkotlinapp.app_models.TyPostUnit
import vn.dylanbui.dbkotlinapp.networking.DbNetworkError
import vn.dylanbui.dbkotlinapp.networking.DbNetworkMethod
import vn.dylanbui.dbkotlinapp.networking.TypicodeNetwork

object TyUserApi {

//    fun getUser(callback: (List<TyUserUnit>, DbNetworkError?) -> Unit) {
//        val strUrl = "users"
//
//        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
//                result: ArrayList<TyUserUnit>?, error: DbNetworkError? ->
//            callback(result ?: arrayListOf(), error)
//        }
//    }

}


object TyPostApi {

    fun getPosts(callback: (ArrayList<TyPostUnit>, DbNetworkError?) -> Unit) {
        val strUrl = "posts"

        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
            result: ArrayList<TyPostUnit>?, error: DbNetworkError? ->
            callback(result ?: arrayListOf(), error)
        }
    }

    fun getPostDetail(postId: Int = 0, callback: (TyPostUnit, DbNetworkError?) -> Unit) {

        var strUrl = "posts/${postId}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
                result: TyPostUnit?, error: DbNetworkError? ->
            callback(result ?: TyPostUnit(), error)
        }
    }

}

object TyCommentApi {

    fun getCommentByPostId(postId: Int, callback: (List<TyCommentUnit>, DbNetworkError?) -> Unit) {
        var strUrl = "posts/${postId}/comments"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
                result: List<TyCommentUnit>?, error: DbNetworkError? ->
            callback(result ?: arrayListOf(), error)
        }

    }

}

object TyPhotoApi {

    fun getPhoto(page: Int = 0, callback: (List<TyPhotoUnit>, DbNetworkError?) -> Unit) {
        val offset = 20
        var start = page * offset
        var strUrl = "photos?_start=${start}&_limit=${offset}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
                result: List<TyPhotoUnit>?, error: DbNetworkError? ->
            callback(result ?: arrayListOf(), error)
        }
    }

    fun getDetailPhoto(photoId: Int = 0, callback: (TyPhotoUnit, DbNetworkError?) -> Unit) {
        var strUrl = "photos/${photoId}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeNetwork.doBasicRequest(TypicodeNetwork.makeLink(strUrl), DbNetworkMethod.GET) {
                result: TyPhotoUnit?, error: DbNetworkError? ->
            callback(result ?: TyPhotoUnit(), error)
        }
    }

}

/*

object TyCommentApi {

    fun getCommentByPostId(postId: Int, callback: (List<TyCommentUnit>, DbNetworkError?) -> Unit) {

        var strUrl = "posts/${postId}/comments"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeApi.get(strUrl, object : TypicodeApiCallback {
            override fun success(responseData: JsonElement) {
                callback(fromJson(responseData), null)
            }

            override fun failure(error: DbNetworkError) {
                // Empty list
                callback(listOf<TyCommentUnit>(), error)
            }
        })
    }

}

object TyPhotoApi {

    fun getPhotoSyn(page: Int = 0, callback: (List<TyPhotoUnit>, DbNetworkError?) -> Unit) {

        val offset = 20
        var start = page * offset
        var strUrl = "photos?_start=${start}&_limit=${offset}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeApi.getSynch(strUrl, object : TypicodeApiCallback {
            override fun success(responseData: JsonElement) {
                callback(fromJson(responseData), null)
            }

            override fun failure(error: AppNetworkServiceError) {
                // Empty list
                callback(listOf<TyPhoto>(), error)
            }
        })
    }

    fun getPhoto(page: Int = 0, callback: (List<TyPhoto>, AppNetworkServiceError?) -> Unit) {

        val offset = 20
        var start = page * offset
        var strUrl = "photos?_start=${start}&_limit=${offset}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeApi.get(strUrl, object : TypicodeApiCallback {
            override fun success(responseData: JsonElement) {
                callback(fromJson(responseData), null)
            }

            override fun failure(error: AppNetworkServiceError) {
                // Empty list
                callback(listOf<TyPhoto>(), error)
            }
        })
    }

    fun getDetailPhoto(photoId: Int = 0, callback: (TyPhoto, AppNetworkServiceError?) -> Unit) {

        var strUrl = "photos/${photoId}"
        Log.d("TAG", "String Url : $strUrl")

        TypicodeApi.get(strUrl, object : TypicodeApiCallback {
            override fun success(responseData: JsonElement) {
                callback(fromJson(responseData), null)
            }

            override fun failure(error: AppNetworkServiceError) {
                // Empty list
                callback(TyPhoto(), error)
            }
        })
    }

}


object TyUserApi {

    fun getUser(callback: (List<TyUser>, AppNetworkServiceError?) -> Unit) {
        TypicodeApi.get("users", object : TypicodeApiCallback {
            override fun success(responseData: JsonElement) {
//                var list: List<TyUser> = fromJson(responseData)
//                callback(list, null)
                callback(fromJson(responseData), null)
            }

            override fun failure(error: AppNetworkServiceError) {
                // Empty list
                callback(listOf<TyUser>(), error)
            }
        })
    }

}

interface TypicodeApiCallback {
    fun success(responseData: JsonElement)
    fun failure(error: AppNetworkServiceError)
}

private interface TypicodeApiService {

    // @Headers("Content-Type: application/json")
    @GET
    fun makeGetRequest(@Url url: String?): Call<JsonElement>

    @POST
    fun makePostRequest(@Url url: String?,
                        @Body requestBody: RequestBody?): Call<JsonElement>

    @Multipart // don use with json
    @POST()
    fun makeUploadFileRequest(@Url url: String?,
                              @PartMap() partMap: HashMap<String, JsonElement>?,
                              @Part file: MultipartBody.Part): Call<JsonElement>

}


object TypicodeApi {

    enum class Method(val method: String) {
        GET("GET"),
        POST("POST"),
    }

    private var BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val makeApiService: TypicodeApiService by lazy {
        Log.d("WebAccess", "Creating retrofit client")

        // -- Log for Retrofit --
        var httpLoggingInterceptor = HttpLoggingInterceptor()
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Create Retrofit client
        return@lazy retrofit.create(TypicodeApiService::class.java)
    }

    // Set at Application()
    fun setBaseUrl(str: String) {
        BASE_URL = str
    }

//    private fun convertToJsonObject(dict: DictionaryType): JsonObject {
//        var jsonObject = JsonObject()
//
//        dict.forEach {
//            // New ways
//            when (it.value) {
//                null -> jsonObject.add(it.key, JsonNull.INSTANCE)
//                is Boolean -> jsonObject.addProperty(it.key, it.value as Boolean)
//                is Number -> jsonObject.addProperty(it.key, it.value as Number)
//                else -> jsonObject.addProperty(it.key, it.value.toString())
//            }
//        }
//        return jsonObject
//    }

//    private fun convertToUrlEncode(map: DictionaryType): String {
//        var sb = StringBuilder()
//        map.entries.forEach {
//            if (it.value == null) {
//                sb.append(it.key).append('=').append('&')
//            } else {
//                sb.append(it.key)
//                    .append('=')
//                    .append(URLEncoder.encode(it.value.toString(), "UTF_8"))
//                    .append('&')
//            }
//        }
//        sb.delete(sb.length - 1, sb.length)
//        return sb.toString()
//    }

    fun getWithSuspend(path: String) {
        // Default set Method GET
        // var call: Call<JsonElement> = makeApiService.makeGetRequest(path)
    }



    fun get(path: String, callback: TypicodeApiCallback) {
        // Default set Method GET
        var call: Call<JsonElement> = makeApiService.makeGetRequest(path)

        call.enqueue(
            object: Callback<JsonElement> {
                override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                    if (response.code() == 200) {
                        response.body()?.let {
                            // Xu ly cac loi cua CloudResponse tra ve tu server, goi ham neu can
                            // onFailed(MyNetworkingServiceError("123", "khong loi"))
                            // onResponse(it)

                            //Thread.sleep(2000)

                            callback.success(it)
                            return
                        }
                    }
                    // Co loi, tra ve loi o day
                    callback.failure(AppNetworkServiceError(response.code().toString(), response.message()))
                }
                override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                    // Day la loi, xu ly loi o day
                    // onFailed?.let { it(AppNetworkServiceError("404", t.message ?: "Unknown")) }
                    callback.failure(AppNetworkServiceError("404", t.message ?: "Unknown"))
                }
            })

    }

    fun getSynch(path: String, callback: TypicodeApiCallback) {

        var call: Call<JsonElement> = makeApiService.makeGetRequest(path)
        // Run in the same thread
        try {
            // call and handle response
            var response: Response<JsonElement> = call.execute()
            if (response.code() == 200) {
                var (body) = guardLet(response.body()) {
                    // Co loi, tra ve loi o day
                    callback.failure(AppNetworkServiceError(response.code().toString(), response.message()))
                    return
                }
                // -- Successfully
                callback.success(body)

            } else {
                callback.failure(AppNetworkServiceError(response.code().toString(), response.message()))


            }
        } catch (t: IOException) {
            callback.failure(AppNetworkServiceError("404", t.message ?: "Catch IOException"))
        }

    }
}

*/
