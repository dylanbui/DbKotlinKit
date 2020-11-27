package vn.dylanbui.dbkotlinapp.app_controllers.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.propzy.android_core_kit.mvvm_structure.DbBaseViewModel
import java.util.*


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */

class TextViewModel : DbBaseViewModel() {

    private val liveData = MutableLiveData<String>()

    init {
        liveData.value = Date().toString()
    }

    fun getLiveData(): LiveData<String> {
        return liveData
    }

}