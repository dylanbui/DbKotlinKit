package vn.dylanbui.dbkotlinapp.app_controllers.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.bluelinelabs.conductor.Controller
import kotlinx.android.synthetic.main.controller_text.view.*
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.android_core_kit.utils.dLog
import vn.dylanbui.dbkotlinapp.R


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */

private val KEY_TEXT = "TextController.text"

class TextController(args: Bundle): DbViewModelController<TextViewModel>(TextViewModel::class.java) {

    constructor(text: String) : this(bundleOf(KEY_TEXT to text)) {
    }

    override fun setTitle(): String = "Title first"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_text, container, false)
    }

    override fun onPreAttach() {

    }

    override fun onViewBound(view: View) {

        this.viewModel.getLiveData().observe(this, Observer<String> {
            this.view?.run {
                dLog("ViewModel created at: $it")
            }
        })

        view.tvTextView.text = args.getString(KEY_TEXT)



    }
}