package vn.dylanbui.dbkotlinapp

import android.app.Application
import android.content.Context

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.reactivex.disposables.CompositeDisposable
import vn.dylanbui.android_core_kit.utils.DbDeviceUtils

class User {
    @SerializedName("userId")
    @Expose
    var userId: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    override fun toString(): String {
        return "Post{" +
                ", userId = " + userId +
                ", name = " + name +
                ", email = '" + email + '\'' +
                ", phone = '" + phone + '\'' +
                '}'
    }

    fun isLogin(): Boolean {
        if (token == null) {
            return false
        }
        return true
    }

}

class App : Application() {

    companion object {
        lateinit var context: Context
        var deviceToken: String? = null
        var deviceName: String? = null
        var versionApp: String? = null
        var versionName: String? = null
        var currentUser: User = User()
//        var profile: Profile? = null
        var imageFolder = ""
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        // AppNetwork.setBaseUrl("http://45.117.162.50:8080/file/api/")
//        AppNetwork.setBaseUrl("http://app.propzy.vn:9090/sam/api/")
//        CoroutinesNetwork.setBaseUrl("http://app.propzy.vn:9090/sam/api/")

//        var okHttpClient = OkHttpClient().newBuilder()
//            .addInterceptor(ChuckInterceptor(this))
//            .build()
//        AndroidNetworking.initialize(applicationContext, okHttpClient)
////        if (BuildConfig.BUILD_TYPE.equals("debug"))
//        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
//
//        PreUtil.init(this)
//
//        Logger.addLogAdapter(AndroidLogAdapter())
//
//        user = PreUtil.getObject(PreUtil.KEY_USER, User::class.java)
//        profile = PreUtil.getObject(PreUtil.KEY_PROFILE, Profile::class.java)

        versionApp = DbDeviceUtils.getVersionApp(this)
        deviceName = DbDeviceUtils.getDeviceName()
        versionName = DbDeviceUtils.getVersionName()
//        deviceToken = Utils.getDeviceToken() // Pai co Firebase
//        Realm.init(this)
//        val config = RealmConfiguration.Builder().name("surveyapp.realm").build()
//        Realm.setDefaultConfiguration(config)
    }

}