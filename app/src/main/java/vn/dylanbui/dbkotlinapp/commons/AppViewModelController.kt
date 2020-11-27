package vn.dylanbui.dbkotlinapp.commons

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import vn.dylanbui.dbkotlinapp.R
import vn.propzy.android_core_kit.DbBaseActivity
import vn.propzy.android_core_kit.DbError
import vn.propzy.android_core_kit.mvvm_structure.DbBaseViewModel
import vn.propzy.android_core_kit.mvvm_structure.DbViewModelController
import vn.propzy.android_core_kit.utils.DbUtils
import vn.propzy.android_core_kit.utils_adapter.DbEndlessRecyclerViewScrollListener

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/4/20
 * To change this template use File | Settings | File and Code Templates.
 */

abstract class AppViewModelController<T: DbBaseViewModel>(modelClass: Class<T>, arg: Bundle = bundleOf())
    : DbViewModelController<T>(modelClass, arg) {

    //region Variables
    var baseActivity: DbBaseActivity? = null
    var toolbar: Toolbar? = null
    var progressView: ViewGroup? = null // Loading for control, fill all screen
    var progressDialog: AlertDialog? = null // Loading for page

    abstract fun setTitle(): String
    open fun isShowBackButton() : Boolean  = true

    // Combo RecyclerView
    var recyclerView: RecyclerView? = null
    var layoutRefresh: SwipeRefreshLayout? = null // Full to reload
    var scrollListener: DbEndlessRecyclerViewScrollListener? = null // Load more

    open fun recyclerViewId(): Int? = null
    open fun layoutRefreshId(): Int? = null

    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedViewState: Bundle?): View {

        val view: View = super.onCreateView(inflater, container, savedViewState)

        baseActivity = activity as? DbBaseActivity
        progressView = view.findViewById(R.id.progressView)
        activity?.let {
            this.progressDialog = DbUtils.makeProgressDialog(it, getStringResource(R.string.loading_title))
            // this.dialogUtils = SweetDialogUtils(it)
            // this.callPhoneUtils = CallPhoneUtils(it)
        }
        toolbar = view.findViewById(R.id.toolbar)
        this.enableBackButton(isShowBackButton())

        val layoutManager = LinearLayoutManager(view.context)
        // Have support Recycler View
        recyclerViewId()?.let {
            recyclerView = view.findViewById(it)
            recyclerView?.setHasFixedSize(true)
            recyclerView?.layoutManager = layoutManager

            scrollListener = object : DbEndlessRecyclerViewScrollListener(layoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    loadNextPage(page)
                }
            }
            recyclerView?.addOnScrollListener(scrollListener!!)

        }
        // Have support Layout Refresh
        layoutRefreshId()?.let {
            layoutRefresh = view.findViewById(it)
            layoutRefresh?.setOnRefreshListener {
                pullToRefresh()
            }
        }

        return view
    }

    open fun loadNextPage(page: Int) { }
    open fun pullToRefresh() { }

    //endregion

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    open fun enableBackButton(isEnableBack: Boolean) {
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
            if (!isEnableBack) {
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
//        val networkError = error as? DbNetworkError
//        networkError?.let {
//            showToast("errorCode: " + it.errorCode + " -- errorMessage: " + it.errorMessage)
//        }
    }

    // Keyboard Manager
    fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = it.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }
    }

    //endregion

}