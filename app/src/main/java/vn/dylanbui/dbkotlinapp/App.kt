package vn.dylanbui.dbkotlinapp

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmConfiguration
import vn.dylanbui.dbkotlinapp.networking.DbRealmCacheManager
import vn.dylanbui.dbkotlinapp.networking.PzNetwork
import vn.dylanbui.dbkotlinapp.networking.TypicodeNetwork
import vn.propzy.android_core_kit.permission_manager.DbPermissionManager
import vn.propzy.android_core_kit.permission_manager.DbPermissionManagerImpl
import vn.propzy.android_core_kit.utils.DbDeviceUtils
import vn.propzy.android_core_kit.utils.DbLog
import vn.propzy.android_core_kit.utils.DbSharedPreferences

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

class App : Application(), LifecycleObserver {

    companion object {
        lateinit var context: Context
        lateinit var activity: AppCompatActivity

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

        // to observe Application lifecycle events
        // https://handyopinion.com/easiest-way-to-observe-application-lifecycle-events-in-kotlin-android/
        // https://developer.android.com/topic/libraries/architecture/lifecycle
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        // PzNetwork.initWithBaseUrl("http://app.propzy.vn:9090/sam/api/", DbRealmCacheManager())
        TypicodeNetwork.initWithBaseUrl("https://jsonplaceholder.typicode.com/", DbRealmCacheManager(), BuildConfig.DEBUG)

        // AppNetwork.setBaseUrl("http://45.117.162.50:8080/file/api/")
//        AppNetwork.setBaseUrl("http://app.propzy.vn:9090/sam/api/")
//        CoroutinesNetwork.setBaseUrl("http://app.propzy.vn:9090/sam/api/")
//
//        Logger.addLogAdapter(AndroidLogAdapter())
//
//        user = PreUtil.getObject(PreUtil.KEY_USER, User::class.java)
//        profile = PreUtil.getObject(PreUtil.KEY_PROFILE, Profile::class.java)

        versionApp = DbDeviceUtils.getVersionApp(this)
        deviceName = DbDeviceUtils.getDeviceName()
        // versionName = DbDeviceUtils.getVersionName()
//        deviceToken = Utils.getDeviceToken() // Pai co Firebase
//        Realm.init(this)
//        val config = RealmConfiguration.Builder().name("surveyapp.realm").build()
//        Realm.setDefaultConfiguration(config)

        // PrefUtils.init(this)
        DbSharedPreferences.initWithAppContext(this)
//
//        Logger.addLogAdapter(AndroidLogAdapter())
//
        // -- Get data saved from device --
//        var sessionUser = AppSessionUser.getFromDevice()
//        sessionUser?.let {
//            currentUser = sessionUser
//        }

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .name("dbkotlinapp.realm").build()
        // Realm.deleteRealm(config)
        Realm.setDefaultConfiguration(config)
        // Log.d("TAG", "Realm path: " + config.path)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppStart() {
        Log.e("lifecycle event", "ON_START")
        logConfig()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAppResume() {
        Log.e("lifecycle event", "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onAppPause() {
        Log.e("lifecycle event", "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppStop() {
        Log.e("lifecycle event", "ON_STOP")
        DbLog.startWriteLogFile()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppDestroy() {
        Log.e("lifecycle event", "ON_DESTROY")
    }

    private fun logConfig() {
        // Xu dung ham yeu cau permission o day van duoc, su dung voi App.activity
        val writeFile = false
        DbLog.initLog(tagName = "DB_KOTLIN_APP", isDebug = true, writeLogFile = writeFile)

        if (writeFile) {
            // Yeu cau quyen doc ghi file
            val permissionManager: DbPermissionManager = DbPermissionManagerImpl
            permissionManager.checkPermissions(
                activity = App.activity,
                permissions = arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                onPermissionResult = { permissionResult ->
                    // handle permission result
                    if (permissionResult.areAllGranted()) {
                    }

                }, requestCode = 112
            )
        }


    }

}