package vn.dylanbui.android_core_kit.mvvm_structure


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 5/29/20
 * To change this template use File | Settings | File and Code Templates.
 */


import android.os.Bundle
import androidx.annotation.NonNull
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.bluelinelabs.conductor.Controller

abstract class ViewModelController : Controller, LifecycleOwner {
    private val viewModelStore = ViewModelStore()
    private val lifecycleOwner: ControllerLifecycleOwner = ControllerLifecycleOwner(this)

    constructor() : super() {}
    constructor(bundle: Bundle?) : super(bundle) {}

    @JvmOverloads
    fun viewModelProvider(factory: ViewModelProvider.Factory? = AndroidViewModelFactory(activity!!.application)): ViewModelProvider {
        return ViewModelProvider(viewModelStore, factory!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }

    @NonNull
    override fun getLifecycle(): Lifecycle {
        return lifecycleOwner.lifecycle
    }
}