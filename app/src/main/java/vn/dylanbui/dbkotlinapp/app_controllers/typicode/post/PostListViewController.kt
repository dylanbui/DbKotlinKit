package vn.dylanbui.dbkotlinapp.app_controllers.typicode.post

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.controller_post.view.*
import vn.dylanbui.android_core_kit.utils.onClick
import vn.dylanbui.android_core_kit.utils.toast
import vn.dylanbui.android_core_kit.utils_adapter.OnDbAdapterListener
import vn.dylanbui.dbkotlinapp.MainActivity
import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail.PostDetailControllerListener
import vn.dylanbui.dbkotlinapp.app_coordinator.ApplicationRoute
import vn.dylanbui.dbkotlinapp.app_models.TyPostUnit
import vn.dylanbui.dbkotlinapp.commons.AppViewModelController
import vn.dylanbui.dbkotlinapp.commons.DbResult

class PostListViewController: AppViewModelController<PostViewModel>(PostViewModel::class.java), OnDbAdapterListener<TyPostUnit>
    , PostDetailControllerListener {

    private lateinit var postAdapter: PostListAdapter

    override fun setTitle(): String? = "Title Post"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_post, container, false)
    }

    override fun recyclerViewId(): Int? = R.id.cycView
    override fun layoutRefreshId(): Int? = R.id.refreshLayout


    override fun onViewBound(view: View) {

        // -- At here presenter == null --
        postAdapter = PostListAdapter(this)
        recyclerView?.adapter = postAdapter

        this.viewModel.ldPost.observe(this, Observer { result ->
            this.view?.run {
                // Reload data
                // if (page == 0) postAdapter.clearData()
                when(result) {
                    // is DbResult.Loading -> showLoading() // khong su dung do co su dung ProgressView() roi
                    is DbResult.Success -> showItems(result.data)
                    is DbResult.Error -> showError(result.error)
                }
                //postAdapter.updateData(ArrayList(it.))
                layoutRefresh?.isRefreshing = false
                // progressView?.visibility = View.GONE
            }
        })
        // this.viewModel.getLiveDataPost()

        this.viewModel.posts(hashMapOf("1" to "x")).observe(this, Observer { result ->
            this.view?.run {
                // Reload data
                // if (page == 0) postAdapter.clearData()
                when(result) {
                    // is DbResult.Loading -> showLoading() // khong su dung do co su dung ProgressView() roi
                    is DbResult.Success -> showItems(result.data)
                    is DbResult.Error -> showError(result.error)
                }
                //postAdapter.updateData(ArrayList(it.))
                layoutRefresh?.isRefreshing = false
                // progressView?.visibility = View.GONE
            }
        })

        // Day la cach thu 2 the hien cach dang ky
        // this.viewModel.posts(hashMapOf("1" to "x")).observe (this, Observer(::handlerObserverResult))

        view.btnNextControl.onClick {
            nav?.navigate(ApplicationRoute.MakeItem())
        }

    }

    private fun handlerObserverResult(result: DbResult<List<TyPostUnit>>) {
        when(result) {
            is DbResult.Loading -> showLoading()
            is DbResult.Success -> showItems(result.data)
            is DbResult.Error -> showError(result.error)
        }
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

    override fun onDetach(view: View) {
        super.onDetach(view)
        Log.d("TAG", "onDetach")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy onDestroy")
    }

    // -- interface PostListActionView --

    override fun loadNextPage(page: Int) {
//            this.viewModel.posts(hashMapOf("1" to "x", "2" to "y", "-1" to "zz"))
    }

    override fun pullToRefresh() {
//            this.viewModel.posts(hashMapOf("1" to "x", "2" to "y", "-1" to "zz"))
        // this.viewModel.getLiveDataPost()
        this.viewModel.posts(hashMapOf("1" to "x")) // chi goi dc co 1 lan thoi
    }

    fun showItems(items: List<TyPostUnit>) {
        hideProgressView()
        postAdapter.updateData(ArrayList(items))
    }

    override fun onSelectedItemListener(model: TyPostUnit, position: Int, view: View?) {
        activity?.toast("No click vao tao: $position")
        nav?.navigate(ApplicationRoute.GotoPostDetail(model.id!!, this))
    }

    override fun onTitlePicked(option: String?) {
        activity?.toast(option ?: "option nullll")
    }

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

