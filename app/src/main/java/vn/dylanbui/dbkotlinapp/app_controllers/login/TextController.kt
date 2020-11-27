package vn.dylanbui.dbkotlinapp.app_controllers.login

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.controller_text.view.*
import vn.dylanbui.dbkotlinapp.R
import vn.dylanbui.dbkotlinapp.commons.AppViewModelController
import vn.propzy.android_core_kit.utils.dLog


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */

private val KEY_TEXT = "TextController.text"

class TextController(args: Bundle): AppViewModelController<TextViewModel>(TextViewModel::class.java, args) {

    constructor(text: String) : this(bundleOf(KEY_TEXT to text)) {

    }

    override fun setTitle(): String = "Title first"
    override fun getControllerLayoutId(): Int = R.layout.controller_text


    override fun onViewBound(view: View) {

        this.viewModel.getLiveData().observe(this, Observer<String> {
            this.view?.run {
                dLog("ViewModel created at: $it")
            }
        })

        view.tvTextView.text = args.getString(KEY_TEXT)

    }

    override fun onPreAttach() {

    }
}