package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.two


import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModel
import java.util.*
import vn.dylanbui.dbkotlinapp.commons.*

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

class StepTwoViewModel : DbViewModel() {

    // Day co the coi nhu la 1 ham, nhan tham so lay du lieu
    private val liveData: DbLiveData<String> by lazy {
        val liveData = DbMubLiveData<String>()
        liveData.value = successResult("Step TWO ViewModel : " + Date().toString())

        return@lazy liveData
    }

    fun getMyLiveData(): DbLiveData<String> = liveData

}