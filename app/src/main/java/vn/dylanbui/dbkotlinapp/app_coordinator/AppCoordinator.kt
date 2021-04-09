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
import vn.dylanbui.dbkotlinapp.app_controllers.splash_intro.SplashViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post.PostListViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailControllerListener
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailViewController
import vn.propzy.android_core_kit.mvp_structure.*
import vn.propzy.android_core_kit.utils.dLog

//enum class ApplicationRoute : DbEnumRoute {
//    //DefaultError inherited plus
//    GotoPostDetail,
//    GotoPhUserDetail,
//    GotoAnyWhere
//}

// Define enum nhu la 1 data class
sealed class ApplicationRoute: DbEnumRoute {
    class SplashPageComplete : ApplicationRoute()
    class GotoPostDetail<T: PostDetailControllerListener>(val postId: Int, val listener: T?) : ApplicationRoute()
    class GotoPhUserDetail(val url: String, val caption: String) : ApplicationRoute()
    class GotoAnyWhere : ApplicationRoute()

    class MakeItem : ApplicationRoute()
}

class AppCoordinator(router: Router): DbBaseCoordinator(router), DbNavigation {

    // private var router = router

    override var rootController: Controller = SplashViewController()

//    private var notificationCoordinator: NotificationCoordinator? = null

    override fun start(isModal: Boolean, option: Any?) {
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

        var vcl = PostListViewController()
        vcl.nav = this
        router.defaultSetRootController(vcl)

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

            is ApplicationRoute.GotoPostDetail<*> -> {
                var vcl = PostDetailViewController(toRoute.postId, toRoute.listener)
                router.defaultPushController(vcl)
            }

            is ApplicationRoute.GotoPhUserDetail -> {


            }

            is ApplicationRoute.GotoAnyWhere -> {

            }

            is ApplicationRoute.MakeItem -> {
                val create = CreateItemCoordinator(router)
                create.finishedCoordinator = {
                    dLog("finishedCoordinator")
                }
                create.start()
            }

            else -> {

            }
        }

    }
}