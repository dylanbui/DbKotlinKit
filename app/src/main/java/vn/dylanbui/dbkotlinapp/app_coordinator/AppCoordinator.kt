package vn.dylanbui.dbkotlinapp.app_coordinator

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import vn.dylanbui.android_core_kit.mvp_structure.*
import vn.dylanbui.dbkotlinapp.App
import vn.dylanbui.dbkotlinapp.app_controllers.splash_intro.SplashViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post.PostListViewController

//enum class ApplicationRoute : DbEnumRoute {
//    //DefaultError inherited plus
//    GotoPostDetail,
//    GotoPhUserDetail,
//    GotoAnyWhere
//}

// Define enum nhu la 1 data class
sealed class ApplicationRoute: DbEnumRoute {
    class SplashPageComplete() : ApplicationRoute()
//    class GotoPostDetail(val post: TyPost) : ApplicationRoute()
    class GotoPhUserDetail(val url: String, val caption: String) : ApplicationRoute()
    class GotoAnyWhere() : ApplicationRoute()
}

class AppCoordinator(router: Router): BaseDbCoordinator(router), DbNavigation {

    // private var router = router

    override var rootController: Controller = SplashViewController()

//    private var notificationCoordinator: NotificationCoordinator? = null

    override fun start() {
        // var vcl = PostListViewController()

        var vcl = SplashViewController()


        //var vcl = LoginViewController()
        //var vcl = UploadProgressController()
        // var vcl = FormatEditTextController()

        vcl.nav = this
        router.setRoot(RouterTransaction.with(vcl))

//        router.setRoot(RouterTransaction.with(FirstViewController())
//            .pushChangeHandler(FadeChangeHandler())
//            .popChangeHandler(FadeChangeHandler()))
    }

    private fun splashPageComplete() {

        router.defaultSetRootController(PostListViewController())

//        var user = App.currentUser
//        if (user.isLogin()) {
//            // Da login roi, chuyen qua man hinh ds
//        } else {
//            // Chua login, chuyen qua man hinh login
//            // router.defaultSetRootController(LoginViewController())
//
//            router.defaultSetRootController(PostListViewController())
//        }
    }

    override fun navigate(toRoute: DbEnumRoute, context: Context?, parameters: Any?) {

//        var route = (toRoute as? ControllerRoute).guard {
//            Log.d("ERROR", "Eo cast duoc")
//            return
//        }

        when (toRoute) {

            is ApplicationRoute.SplashPageComplete -> {
                this.splashPageComplete()
            }

//            is ApplicationRoute.GotoPostDetail -> {
//                var vcl = PostDetailViewController()
//                vcl.tyPost = toRoute.post //(parameters as TyPost)
//
//                router.defaultPushController(vcl)
//
////                router.pushController(
////                    RouterTransaction.with(vcl)
////                        .pushChangeHandler(HorizontalChangeHandler())
////                        .popChangeHandler(HorizontalChangeHandler()))
//            }

            is ApplicationRoute.GotoPhUserDetail -> {


            }

            is ApplicationRoute.GotoAnyWhere -> {

            }

            else -> {

            }
        }

    }
}