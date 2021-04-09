package vn.dylanbui.dbkotlinapp.app_coordinator

import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler

import vn.dylanbui.dbkotlinapp.app_controllers.splash_intro.SplashViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.one.StepOneController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.three.StepThreeController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.two.StepTwoController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post.PostListViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailControllerListener
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailViewController
import vn.propzy.android_core_kit.mvp_structure.*
import vn.propzy.android_core_kit.utils.dLog


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */

// Define enum nhu la 1 data class
sealed class CreateItemRoute: DbEnumRoute {
    class StepOneComplete : CreateItemRoute()
    class StepTwoComplete : CreateItemRoute()
    class FinishedCreateItem : CreateItemRoute()
}

class CreateItemCoordinator(router: Router): DbBaseCoordinator(router), DbNavigation {

    private var backStack: List<RouterTransaction>? = null

    var finishedCoordinator: (()->Unit)? = null

    override var rootController: Controller = StepOneController().apply {
        nav = this@CreateItemCoordinator
    }

    override fun start(isModal: Boolean, option: Any?) {
        // Save back stack
        this.backStack = router.backstack

        router.defaultPushModalController(this.rootController)
    }

    override fun navigate(toRoute: DbEnumRoute, context: Context?, parameters: Any?) {

//        var route = (toRoute as? ControllerRoute).guard {
//            Log.d("ERROR", "Eo cast duoc")
//            return
//        }

        when (toRoute) {

            is CreateItemRoute.StepOneComplete -> {
                var vcl = StepTwoController()
                vcl.nav = this
                router.defaultPushController(vcl)
            }

            is CreateItemRoute.StepTwoComplete -> {
                var vcl = StepThreeController()
                vcl.nav = this
                router.defaultPushController(vcl)
            }

            is CreateItemRoute.FinishedCreateItem -> {

                dLog("11 router.backstackSize = ${router.backstackSize}")

                this.backStack?.let {
                    // router.setBackstack(it, FadeChangeHandler())
                    router.setBackstack(it, VerticalChangeHandler())

                    dLog("22 router.backstackSize = ${router.backstackSize}")
                }

                this.finishedCoordinator?.let { it() }
            }

        }

    }
}