package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.one

import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModel
import vn.dylanbui.dbkotlinapp.commons.DbLiveData
import vn.dylanbui.dbkotlinapp.commons.DbMubLiveData
import vn.dylanbui.dbkotlinapp.commons.successResult
import java.util.*


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

class StepOneViewModel : DbViewModel() {

    // Day co the coi nhu la 1 ham, nhan tham so lay du lieu

    private val liveData: DbLiveData<String> by lazy {
        val liveData2 = DbMubLiveData<String>()
        liveData2.value = successResult("StepOneViewModel : " + Date().toString())

        return@lazy liveData2
    }

    fun getMyLiveData(): DbLiveData<String> = liveData

}