package vn.dylanbui.android_core_kit.mvvm_structure

/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/1/20
 * To change this template use File | Settings | File and Code Templates.
 */

import vn.dylanbui.android_core_kit.mvp_structure.DbBaseMvpView
import vn.dylanbui.android_core_kit.mvp_structure.DbNavigation

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.conductor.MvpController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.dylanbui.android_core_kit.DbError
import vn.dylanbui.android_core_kit.DbMessageEvent
import vn.dylanbui.android_core_kit.utils_adapter.DbEndlessRecyclerViewScrollListener

interface ActionBarProvider {
    fun supportActionBar(): ActionBar?
}

@Suppress("OverridingDeprecatedMember", "DEPRECATION")
@SuppressWarnings("deprecation", "unused")
abstract class DbViewModelController<T: DbViewModel>(private var modelClass: Class<T>, arg: Bundle? = null): ViewModelController(arg)
{
    open var nav: DbNavigation? = null

    // open var viewModel: VM? = null
    val viewModel by lazy {
        // ViewModelProviders.of(this, getFactory()).get(modelClass)
        val vm: T = viewModelProvider().get(modelClass)
        vm.nav = nav
        vm
    }

    // Inject dependencies once per life of Controller
    private val injectRunOnce by lazy { onPreAttach() }

    protected open fun setTitle(): String? = null

    init {
        // Van giu controller in Memory, khi push new controller
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }

    // Note: This is just a quick demo of how an ActionBar *can* be accessed, not necessarily how it *should*
    // be accessed. In a production app, this would use Dagger instead.
    protected fun getActionBar(): ActionBar?
    {
        var actionBarProvider: ActionBarProvider? = activity as? ActionBarProvider
        return if (actionBarProvider != null) actionBarProvider?.supportActionBar() else null
    }

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    var recyclerView: RecyclerView? = null
    var layoutRefresh: SwipeRefreshLayout? = null // Full to reload
    var scrollListener: DbEndlessRecyclerViewScrollListener? = null // Load more

    open fun recyclerViewId(): Int? = null
    open fun layoutRefreshId(): Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View
    {
        // -- inflateView for this Controller --
        val view: View = inflateView(inflater, container)

        // progressView = view.findViewById(R.id.progressView)
//        activity?.let {
//            this.progressDialog = DbUtils.makeProgressDialog(it, getStringResource(R.string.loading_title))
//        }
        // toolbar = view.findViewById(R.id.toolbar)
//        toolbar?.title = setTitle()
//        this.enableBackButton()

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

        // -- Register Event Bus , At OnCreateView
        EventBus.getDefault().register(this)
        // -- Blind View for this Controller --
        onViewBound(view)
        return view
    }

    open fun loadNextPage(page: Int) { }
    open fun pullToRefresh() { }

    protected abstract fun onViewBound(view: View)

    protected abstract fun onPreAttach()

    override fun onAttach(view: View) {
        // Call inject variable
        injectRunOnce

        super.onAttach(view)

//        this.mainActivity = activity as? MainActivity
//        this.mainActivity?.let {
//            it.setToolBarTitle(setTitle())
//            it.enableUpArrow(router.backstackSize > 1)
//        }

    }

    override fun onDestroyView(view: View) {
        // -- Unregister Event Bus
        EventBus.getDefault().unregister(this)
        super.onDestroyView(view)
    }

    // -- Make animation change controller
    override fun onChangeStarted(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeStarted(changeHandler, changeType)
        // toolbar?.isEnabled = false
        // toolbar?.visibility = View.INVISIBLE
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        // toolbar?.isEnabled = true
        // toolbar?.visibility = View.VISIBLE
        // val animation = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right)
        // toolbar?.startAnimation(animation)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(mes: DbMessageEvent) {
    }

}