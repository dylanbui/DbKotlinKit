package vn.dylanbui.dbkotlinapp.app_controllers.typicode.post

import vn.dylanbui.android_core_kit.DictionaryType
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModel
import vn.dylanbui.dbkotlinapp.app_models.TyPostUnit
import vn.dylanbui.dbkotlinapp.app_network.TyPostApi
import vn.dylanbui.dbkotlinapp.commons.*


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/3/20
 * To change this template use File | Settings | File and Code Templates.
 */


class PostViewModel : DbViewModel() {

    // Day co the coi nhu la 1 ham, nhan tham so lay du lieu
    private val postsLiveData: Map<DictionaryType, DbLiveData<List<TyPostUnit>>> =
        lazyMap { parameters ->
            // Xu ly load du lieu bo vao LiveData, ket noi API hay tu database
            val liveData = DbMubLiveData<List<TyPostUnit>>()
            liveData.value = loading()
            TyPostApi.getPosts { list, appNetworkServiceError ->
                appNetworkServiceError?.let {
                    liveData.value = errorResult(it)
                    return@getPosts
                }
                // Reload data
                liveData.value = successResult(list)
            }
            return@lazyMap liveData
        }

    fun posts(parameters: DictionaryType): DbLiveData<List<TyPostUnit>> =
        postsLiveData.getValue(parameters)


    open var ldPost = DbMubLiveData<List<TyPostUnit>>()

    init {
        ldPost.value = loading() //arrayListOf<TyPostUnit>()
    }

    fun getLiveDataPost() {
        ldPost.value = loading()
        TyPostApi.getPosts { list, appNetworkServiceError ->
            appNetworkServiceError?.let {
                ldPost.value = errorResult(it)
                return@getPosts
            }
            // Reload data
            ldPost.value = successResult(list)
        }
    }
//
//    fun getPostList(page: Int = 0) {
//        TyPostApi.getPost { list, appNetworkServiceError ->
//            appNetworkServiceError?.let {
//                // activity?.toast(it.errorMessage)
//                // have error
//                return@getPost
//            }
//            // Reload data
//            ldPost.value = list
//        }
//    }

    fun onPostRowClick(position: Int, post: TyPostUnit) {
        // -- Code xu ly khi click vao row --
        // ifViewAttached { view -> view.onPostRowClick(position, post) }
    }


    /* Cach su dung co ban,
    private val dataListLiveData = MutableLiveData<DbResult<List<TyPostUnit>>>()

    fun getDataList(): LiveData<DbResult<List<TyPostUnit>>> {

        dataListLiveData.value = loading()

        TyPostApi.getPost { list, appNetworkServiceError ->
            appNetworkServiceError?.let {
                // activity?.toast(it.errorMessage)
                // have error
                return@getPost
                dataListLiveData.value = errorResult(it.errorCode, it.errorMessage)
            }
            // Reload data
            dataListLiveData.value = successResult(list)
        }

        return dataListLiveData
    }
     */

}