package vn.dylanbui.android_core_kit

import org.greenrobot.eventbus.EventBus

typealias DictionaryType = HashMap<String, Any?>
/*
val map: HashMap<Int, String> = hashMapOf(1 to "x", 2 to "y", -1 to "zz")
println(map) // {-1=zz, 1=x, 2=y}
* */

interface DbError {} // Use for save error

interface DbMessageEvent {}

// Define enum nhu la 1 data class
sealed class DbDemoMessageEvent: DbMessageEvent {
    class SplashPageComplete() : DbDemoMessageEvent()
    class GotoPostDetail(val post: Int) : DbDemoMessageEvent()
    class GotoPhUserDetail(val url: String, val caption: String) : DbDemoMessageEvent()
    class GotoAnyWhere() : DbDemoMessageEvent()
}

fun doPostEventBusNotification(message: DbMessageEvent) {
    EventBus.getDefault().post(message)
}

//class DbMessageNotify(message: DbMessageEvent) {
//    var message = message
//}

//class Constants {
//
//    companion object {
//        val TAG = "tag"
//        var gson = Gson()
//
//        val PAGE_SIZE = 20
//
//        // request permission
//        val REQUEST_CHECK_SETTINGS = 0
//        val REQUEST_ENABLE_LOCATION = 1
//    }
//
//    enum class TransitionType(var enter: Int, var exit: Int, var popEnter: Int, var popExit: Int) {
//        NONE(R.anim.stand_still, R.anim.stand_still, R.anim.stand_still, R.anim.stand_still),
//        SLIDE_IN_RIGHT_TO_LEFT(
//            R.anim.slide_in_right,
//            R.anim.slide_out_left,
//            R.anim.slide_in_left,
//            R.anim.slide_out_right
//        ),
//        SLIDE_IN_LEFT_TO_RIGHT(
//            R.anim.slide_in_left,
//            R.anim.slide_out_right,
//            R.anim.slide_in_right,
//            R.anim.slide_out_left
//        ),
//        SLIDE_IN_BOTTOM(R.anim.slide_in_bottom, R.anim.stand_still, R.anim.stand_still, R.anim.slide_out_bottom),
//        CROSS_FADE(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
//
//    }
//}