package vn.dylanbui.dbkotlinapp.app_controllers.typicode.post

import android.app.Activity
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import kotlinx.android.synthetic.main.controller_splash.view.*
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.android_core_kit.utils.dLog
import vn.dylanbui.android_core_kit.utils_adapter.DbEndlessRecyclerViewScrollListener
import vn.dylanbui.dbkotlinapp.MainActivity
import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.app_models.TyPostUnit
import vn.dylanbui.dbkotlinapp.commons.DbResult
import java.text.FieldPosition

class PostListViewController: DbViewModelController<PostViewModel>(PostViewModel::class.java) {

    lateinit var recyclerView: RecyclerView
    lateinit var layoutRefresh: SwipeRefreshLayout

    private var scrollListener: DbEndlessRecyclerViewScrollListener? = null
    private lateinit var postAdapter: PostListAdapter

    override fun setTitle(): String? = "Title Post"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_post, container, false)
    }

    override fun onViewBound(view: View) {
        recyclerView = view.findViewById(R.id.cycView)
        layoutRefresh = view.findViewById(R.id.refreshLayout)

        // -- At here presenter == null --
        postAdapter = PostListAdapter()

        var layoutManager = LinearLayoutManager(view.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = postAdapter

        scrollListener = object : DbEndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // presenter.getUserList(page, false)
                // presenter.getPostList(page)
            }
        }
        recyclerView.addOnScrollListener(scrollListener!!)

        layoutRefresh.setOnRefreshListener {
            this.viewModel.posts(hashMapOf("1" to "x", "2" to "y", "-1" to "zz"))
        }


        this.viewModel.posts(hashMapOf("1" to "x")).observe(this, Observer { result ->
            this.view?.run {
                // text.text = "ViewModel created at: $it"
//                dLog("ViewModel created at: $it")
//                tvAppVersion.text = "ViewModel created at: $it"
                // Reload data
                // if (page == 0) postAdapter.clearData()
                when(result) {
                    is DbResult.Loading -> showLoading()
                    is DbResult.Success -> showItems(result.data)
                    is DbResult.Error -> showError(result.message)
                }

                //postAdapter.updateData(ArrayList(it.))
                layoutRefresh.isRefreshing = false
                // progressView?.visibility = View.GONE
            }
        })


        this.viewModel.posts(hashMapOf("1" to "x")).observe (this, Observer(::handlerObserverResult))
    }

    private fun handlerObserverResult(result: DbResult<List<TyPostUnit>>) {
        when(result) {
            is DbResult.Loading -> showLoading()
            is DbResult.Success -> showItems(result.data)
            is DbResult.Error -> showError(result.message)
        }
    }

//    private fun handlerObserverResult(result: SResult<List<GenreUI>>) {
//        when(result) {
//            is SResult.Loading -> showLoading()
//            is SResult.Success -> showItems(result.data)
//            is SResult.Error -> showError(result.message)
//        }
//    }

    override fun onPreAttach() {
        // -- At here presenter is existed --
        // -- Get action in row => presenter --
        // postAdapter.presenter = presenter
        // presenter.getPostList(0)
    }

    override fun onAttach(view: View)
    {
        super.onAttach(view)

        var mainActivity = activity as? MainActivity

        mainActivity?.let {
            it.setToolBarTitle(setTitle())
            it.enableUpArrow(router.backstackSize > 1)
        }

        Log.d("TAG", "onAttach")
    }

    override fun onActivityResumed(activity: Activity) {
        // -- Call when Activity Resumed --
        super.onActivityResumed(activity)
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        Log.d("TAG", "onDetach")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy onDestroy")
    }

    // -- interface PostListActionView --

    /*

    override fun onPostRowClick(position: Int, post: TyPost) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        // activity?.toast("No click vao tao: ${position.toString()}")

        // nav?.navigate(ApplicationRoute.GotoPostDetail(post), null, post)
        nav?.navigate(ApplicationRoute.GotoPostDetail(post))
        //nav?.navigate(ApplicationRoute.GotoAnyWhere())

//        var vcl = PostDetailViewController()
//        vcl.tyPost = post
//
//        router.pushController(
//            RouterTransaction.with(vcl)
//                .pushChangeHandler(HorizontalChangeHandler())
//                .popChangeHandler(HorizontalChangeHandler()))
    }

    override fun updatePostList(page: Int, list: ArrayList<TyPost>) {
        // Reload data
        if (page == 0) postAdapter.clearData()
        postAdapter.updateData(list)
        layoutRefresh.isRefreshing = false
        progressView?.visibility = View.GONE
    }

    override fun showPostError(error : AppNetworkServiceError) {
        activity?.toast(error.errorMessage)
    }


     */
}

