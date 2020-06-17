package vn.dylanbui.dbkotlinapp.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import vn.dylanbui.android_core_kit.DbError
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModel
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.android_core_kit.networking.DbNetworkError
import vn.dylanbui.android_core_kit.utils.DbUtils
import vn.dylanbui.android_core_kit.utils_adapter.DbEndlessRecyclerViewScrollListener
import vn.dylanbui.dbkotlinapp.R

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/4/20
 * To change this template use File | Settings | File and Code Templates.
 */

abstract class AppBaseController<T: DbViewModel>(private var modelClass: Class<T>, arg: Bundle = bundleOf()): DbViewModelController<T>(modelClass, arg) {

    //region Variables
    var toolbar: Toolbar? = null
    var progressView: ViewGroup? = null // Loading for control, fill all screen
    var progressDialog: AlertDialog? = null // Loading for page

    // var dialogUtils: SweetDialogUtils? = null // Custom dialog show message
    // var callPhoneUtils: CallPhoneUtils? = null
    open fun isShowToolbar() : Boolean  = true

    // Combo RecyclerView
//    var recyclerView: RecyclerView? = null
//    var layoutRefresh: SwipeRefreshLayout? = null // Full to reload
//    var scrollListener: DbEndlessRecyclerViewScrollListener? = null // Load more

    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {
        val view: View = super.onCreateView(inflater, container, savedViewState)

        progressView = view.findViewById(R.id.progressView)
        activity?.let {
            this.progressDialog = DbUtils.makeProgressDialog(it, getStringResource(R.string.loading_title))
            // this.dialogUtils = SweetDialogUtils(it)
            // this.callPhoneUtils = CallPhoneUtils(it)
        }
        toolbar = view.findViewById(R.id.toolbar)
        this.enableBackButton(isShowToolbar())

        return view
    }
    //endregion

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    open fun enableBackButton(isShowToolbar: Boolean) {
        // dLog("router.backstackSize = ${router.backstackSize}")
        toolbar?.let {
            it.title = setTitle() // Set title for toolbar
            if (router.backstackSize > 1) {
                it.setNavigationIcon(R.mipmap.ic_arrow_left)
                it.setNavigationOnClickListener { _ ->
                    if (router.backstackSize > 1) {
                        router.popCurrentController()
                    }
                }
            } else {
                it.navigationIcon = null
            }
            // Hide back button
            if (!isShowToolbar) {
                it.navigationIcon = null
            }
        }
    }

    // -- Interface BaseMvpView --

    //region Public Function
    fun getStringResource(resourceId: Int): String {
        return activity?.getString(resourceId) ?: ""
    }

    fun getStringQuantity(resourceId: Int, numberCount: Int, vararg text: String) : String {
        return activity?.resources?.getQuantityString(resourceId, numberCount, *text) ?: ""
    }

    fun showLoading() {
        this.progressDialog?.show()
    }

    fun hideLoading() {
        this.progressDialog?.hide()
    }

    fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    fun showProgressView() {
        progressView?.visibility = View.VISIBLE
    }

    fun hideProgressView() {
        progressView?.visibility = View.GONE
    }

    fun showError(error: DbError) {
        hideProgressView()
        // Use Switch condition for more check case
        val networkError = error as? DbNetworkError
        networkError?.let {
            showToast("errorCode: " + it.errorCode + " -- errorMessage: " + it.errorMessage)
        }
    }
    //endregion

}