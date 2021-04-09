package vn.dylanbui.dbkotlinapp.app_controllers.typicode.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.bluelinelabs.conductor.Controller
import kotlinx.android.synthetic.main.controller_post_detail.view.*
import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.app_models.TyPostUnit
import vn.dylanbui.dbkotlinapp.commons.AppViewModelController
import vn.dylanbui.dbkotlinapp.commons.DbResult
import vn.propzy.android_core_kit.utils.onClick

interface PostDetailControllerListener {
    fun onTitlePicked(option: String?)
}


class PostDetailViewController<T: PostDetailControllerListener>(args: Bundle):
    AppViewModelController<PostDetailViewModel>(PostDetailViewModel::class.java, args) {

    constructor(postId: Int, callbackController: T?) : this(bundleOf(KEY_MOVIE_ID to postId)) {
        // if (callbackController is Controller && callbackController is PostDetailControllerListener) {
        if (callbackController is Controller) {
            targetController = callbackController
        }
    }

    private var postId: Int = 0
    private var tyPost: TyPostUnit? = null

    // No se chay sau: onViewBound
    override fun setTitle(): String = "Chi Tiết Post (ID:$postId)"

    override fun getControllerLayoutId(): Int = R.layout.controller_post_detail

    override fun onViewBound(view: View) {

        view?.btnFirst?.onClick {
            val listener = targetController as? PostDetailControllerListener
            listener?.onTitlePicked("noi dung gui lai khi nhan nut")
            // handleBack()
            router.popController(this)
        }

        // Day la cach thu 2 the hien cach dang ky
        postId = args.getInt(KEY_MOVIE_ID)
        this.viewModel.postDetail(hashMapOf(KEY_MOVIE_ID to args.getInt(KEY_MOVIE_ID))).observe (this, Observer(::handlerObserverResult))
    }

    private fun handlerObserverResult(result: DbResult<TyPostUnit>) {
        when(result) {
            // is DbResult.Loading -> showLoading()
            is DbResult.Success -> {
                tyPost = result.data
                updatePostDetail(result.data)
            }
            is DbResult.Error -> showError(result.error)
        }
    }

    override fun onPreAttach() {
        // -- Load data --
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    fun updatePostDetail(post: TyPostUnit) {
        view?.txtId?.text = "ID: ${post.id.toString()}"
        view?.txtTitle?.text = "Title: ${post.title.toString()}"
        view?.txtBody?.text = "Body: ${post.body.toString()}"
        progressView?.visibility = View.GONE
    }


}


