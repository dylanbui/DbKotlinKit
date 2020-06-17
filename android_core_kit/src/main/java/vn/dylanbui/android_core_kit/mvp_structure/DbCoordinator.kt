package vn.dylanbui.android_core_kit.mvp_structure

import android.content.Context
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler
import vn.dylanbui.android_core_kit.utils.dLog

fun Router.popToController(controller: Controller,
                           animatorChangeHandler: AnimatorChangeHandler? = FadeChangeHandler()): Boolean {

//    val backStackList = backstack
//
//    /* Return if current controller is on top of back stack */
//    if (backstack.isEmpty()) {
//        return false
//    }
//
//    // Phai co it nhat 2 phan tu moi xu ly
//    var activeIndex = backStackList.size - 1
//    while (activeIndex > 0) {
//        // So sanh vung nho
//        if (backStackList[activeIndex].controller == controller) {
//            break
//        }
//        activeIndex--
//    }
//
//    if (activeIndex <= 0) {
//        return false
//    }
//
//    val subList = backStackList.subList(0, activeIndex)
//    setBackstack(subList, animatorChangeHandler)
//    return true

    return popToTag(System.identityHashCode(controller).toString(), animatorChangeHandler)
}

fun Router.defaultPushController(controller: Controller, tag: String? = System.identityHashCode(controller).toString()) {
    // val tagName = tag ?: System.identityHashCode(controller).toString()
    // dLog("Address: $tag")
    pushController(
        RouterTransaction.with(controller)
            .pushChangeHandler(HorizontalChangeHandler())
            .popChangeHandler(HorizontalChangeHandler())
            .tag(tag)
    )
}

fun Router.defaultPushModalController(controller: Controller, tag: String? = System.identityHashCode(controller).toString()) {
    pushController(
        RouterTransaction.with(controller)
            .pushChangeHandler(VerticalChangeHandler())
            .popChangeHandler(VerticalChangeHandler())
            .tag(tag)
    )
}

fun Router.defaultReplaceTopController(controller: Controller, tag: String? = System.identityHashCode(controller).toString()) {
    replaceTopController(
        RouterTransaction.with(controller)
            .pushChangeHandler(HorizontalChangeHandler())
            .popChangeHandler(HorizontalChangeHandler())
            .tag(tag)
    )
}

fun Router.defaultSetRootController(controller: Controller,
                                    animatorChangeHandler: AnimatorChangeHandler? = HorizontalChangeHandler(),
                                    tag: String? = System.identityHashCode(controller).toString()) {
    // val animator = animatorChangeHandler ?: HorizontalChangeHandler()
    setRoot(
        RouterTransaction.with(controller)
            .pushChangeHandler(animatorChangeHandler)
            .popChangeHandler(animatorChangeHandler)
            .tag(tag)
    )
}

fun Router.defaultSetDialogController(controller: Controller) {
    pushController(
        RouterTransaction.with(controller)
            .pushChangeHandler(FadeChangeHandler(false))
            .popChangeHandler(FadeChangeHandler())
    )
}

interface DbEnumRoute {}

interface DbNavigation {
    fun navigate(toRoute: DbEnumRoute, context: Context? = null, parameters: Any? = null)

    fun popCurrentController(router: Router) {
        if (router.backstackSize > 1) router.popCurrentController()
    }
}

abstract class DbCoordinator {

    abstract var router: Router

    abstract var rootController: Controller

    constructor(router: Router)

    abstract fun start()
    abstract fun start(option: Any?)
}


abstract class BaseDbCoordinator(router: Router) : DbCoordinator(router) {

    override var router = router

    override fun start() {}

    override fun start(option: Any?) {}
}

