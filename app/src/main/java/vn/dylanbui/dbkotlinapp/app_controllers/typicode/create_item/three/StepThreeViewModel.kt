package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.three


import java.util.*
import vn.dylanbui.dbkotlinapp.commons.*
import vn.propzy.android_core_kit.mvvm_structure.DbBaseViewModel

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

class StepThreeViewModel : DbBaseViewModel() {

    // Day co the coi nhu la 1 ham, nhan tham so lay du lieu
    private val liveData: DbLiveData<String> by lazy {
        val liveData = DbMubLiveData<String>()
        liveData.value = successResult("Step TWO ViewModel : " + Date().toString())

        return@lazy liveData
    }

    fun getMyLiveData(): DbLiveData<String> = liveData

}