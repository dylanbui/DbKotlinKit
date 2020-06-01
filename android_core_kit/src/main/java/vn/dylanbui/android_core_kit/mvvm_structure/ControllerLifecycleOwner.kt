package vn.dylanbui.android_core_kit.mvvm_structure


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 5/29/20
 * To change this template use File | Settings | File and Code Templates.
 */

import android.view.View
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.bluelinelabs.conductor.Controller


class ControllerLifecycleOwner(controller: Controller) : Controller.LifecycleListener(), LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    @NonNull
    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    init {
        controller.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun preCreateView(@NonNull controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            }

            override fun postAttach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }

            override fun preAttach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }

            override fun postDestroyView(@NonNull controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            }
        })
    }
}