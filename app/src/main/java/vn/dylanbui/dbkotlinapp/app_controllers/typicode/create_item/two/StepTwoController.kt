package vn.dylanbui.dbkotlinapp.app_controllers.typicode.create_item.two

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import vn.dylanbui.android_core_kit.mvvm_structure.DbViewModelController
import vn.dylanbui.dbkotlinapp.R


/**
 * Created by IntelliJ IDEA.
 * User: Dylan Bui
 * Email: duc@propzy.com
 * Date: 6/17/20
 * To change this template use File | Settings | File and Code Templates.
 */


class StepTwoController: DbViewModelController<StepTwoViewModel>(
    StepTwoViewModel::class.java)
{


    override fun setTitle(): String = "Step 2"



    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_step_two, container, false)
    }

    override fun onPreAttach() {

    }

    override fun onViewBound(view: View)
    {

    }
}