package vn.dylanbui.dbkotlinapp.app_coordinator

import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import vn.dylanbui.android_core_kit.mvp_structure.*
import vn.dylanbui.dbkotlinapp.app_controllers.splash_intro.SplashViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.one.StepOneController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.three.StepThreeController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.two.StepTwoController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post.PostListViewController
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailControllerListener
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailViewController


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

class CreateItemCoordinator(router: Router): BaseDbCoordinator(router), DbNavigation {

    var finishedCoordinator: (()->Unit)? = null

    override var rootController: Controller = StepOneController().apply {
        nav = this@CreateItemCoordinator
    }

    override fun start() {

        router.backstack

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
                router.defaultPushController(vcl)
            }

            is CreateItemRoute.StepTwoComplete -> {
                var vcl = StepThreeController()
                router.defaultPushController(vcl)
            }

            is CreateItemRoute.FinishedCreateItem -> {
                this.finishedCoordinator?.let { it() }
            }

        }

    }
}