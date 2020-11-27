package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.one

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.controller_step_one.view.*
import vn.dylanbui.android_core_kit.utils.onClick
import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.app_coordinator.CreateItemRoute
import vn.dylanbui.dbkotlinapp.commons.AppViewModelController
import vn.dylanbui.dbkotlinapp.commons.DbResult


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */


class StepOneController: AppViewModelController<StepOneViewModel>(StepOneViewModel::class.java) {

    override fun setTitle(): String = "Step 1"

    override fun isShowBackButton(): Boolean = false

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_step_one, container, false)
    }

    override fun onViewBound(view: View) {

        view.btnNextControl.onClick {
            this.nav?.navigate(CreateItemRoute.StepOneComplete())
        }

        this.viewModel.getMyLiveData().observe(this, Observer { result ->
            this.view?.run {
                // Reload data
                // if (page == 0) postAdapter.clearData()
                when(result) {
                    // is DbResult.Loading -> showLoading() // khong su dung do co su dung ProgressView() roi
                    is DbResult.Success -> {
                        this.tvHello.text = result.data
                    }
                    is DbResult.Error -> showError(result.error)
                }
                //postAdapter.updateData(ArrayList(it.))
                layoutRefresh?.isRefreshing = false
                // progressView?.visibility = View.GONE
                hideProgressView()
            }
        })
    }
}