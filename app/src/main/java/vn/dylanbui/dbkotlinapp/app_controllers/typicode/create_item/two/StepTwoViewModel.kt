package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.two

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModel
import java.util.*


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

class StepTwoViewModel : DbViewModel() {

    private val liveData = MutableLiveData<String>()

    init {
        liveData.value = Date().toString()
    }

    fun getLiveData(): LiveData<String> {
        return liveData
    }

}