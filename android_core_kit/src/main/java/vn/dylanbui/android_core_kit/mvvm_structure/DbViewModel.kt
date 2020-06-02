package vn.dylanbui.android_core_kit.mvvm_structure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */


abstract class DbViewModel: ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

//    private val liveData = MutableLiveData<String>()
//
//    init {
//        liveData.value = Date().toString()
//    }
//
//    fun getLiveData(): LiveData<String> {
//        return liveData
//    }
}